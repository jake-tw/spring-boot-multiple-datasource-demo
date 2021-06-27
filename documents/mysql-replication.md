# MySql Replication

以下內容以 8.0.25 版本的 connector 與 mysql 8.0 版製作，若使用其他版本內容與設定會略有不同。

- MySql 的 multi-connection 策略，官方叫 server failover, 在 failover 的基礎上演變而來的則是 loadbalance 和 replication, 分別對應 Cluster 架構和 Replication 架構。

- Create MySql Replication
    1. create setting file*1
        - mysql-source.cnf

            ```txt
            [mysqld]
            server_id=1
            gtid_mode=ON
            enforce_gtid_consistency=ON
            ```

        - create mysql-replica.cnf
        
            ```txt
            [mysqld]
            server_id=2
            gtid_mode=ON
            enforce_gtid_consistency=ON
            read_only=ON
            ```

    2. start mysql with docker
        - create network

            ```txt
            docker network create mysql-replication
            ```

        - source

            ```txt
            docker run \
            -d \
            -v {pwd}/mysql-source.cnf:/etc/mysql/conf.d/mysql.cnf \
            --network mysql-replication \
            --name mysql-source \
            -p 3306:3306 \
            -e MYSQL_ROOT_PASSWORD=password \
            mysql:8.0
            ```

        - replica
            
            ```txt
            docker run \
            -d \
            -v {pwd}/mysql-replica.cnf:/etc/mysql/conf.d/mysql.cnf \
            --network mysql-replication \
            --name mysql-replica \
            -p 3307:3306 \
            -e MYSQL_ROOT_PASSWORD=password \
            mysql:8.0
            ```

        - 如果設定不生效, 修改 cnf 文件權限 644 後重啟 MySql

            ```txt
            chmod 644 /etc/mysql/conf.d/mysql.cnf
            ```

        - create replication user in source

            - 設置一個新用戶 repl，可以從 example.com 中的任何主機連接以進行複制

            ```
            mysql> CREATE USER 'repl'@'%.example.com' IDENTIFIED BY 'password';

            mysql> GRANT REPLICATION SLAVE ON *.* TO 'repl'@'%.example.com';

            mysql> SHOW MASTER STATUS;
            ```

            - CREATE USER: 建立新帳戶以供 Replica 連接
            - GRANT: 開啟帳戶複製所需的權限

        - replica connect to source

            ```txt
            mysql> CHANGE MASTER TO 
            MASTER_HOST='mysql-source', 
            MASTER_PORT=3306,
            MASTER_USER='repl',
            MASTER_PASSWORD='password',
            GET_MASTER_PUBLIC_KEY=1,
            MASTER_AUTO_POSITION=1;

            mysql> START SLAVE;
            ```

- Java connector url setting
    - Failover: 當主要資料庫連不上時自動切換至次要的資料庫，並自動嘗試恢復主要資料庫的連線

        ```txt
        jdbc:mysql://[primary host][:port],[secondary host 1][:port][,[secondary host 2][:port]]...[/[database]][?propertyName1=propertyValue1[&propertyName2=propertyValue2]...]
        ```

    - Loadbalance: 自動切換使用的資料庫

        ```txt
        jdbc:mysql:loadbalance://[host1][:port],[host2][:port][,[host3][:port]]...[/[database]][?propertyName1=propertyValue1[&propertyName2=propertyValue2]...]
        ```

    - Replication: 主要資料庫寫入, 次要資料庫讀取*2

        ```txt
        # setting type 1
        jdbc:mysql:replication://[source host][:port],[replica host 1][:port][,[replica host 2][:port]]...[/[database]][?propertyName1=propertyValue1[&propertyName2=propertyValue2]...]
        ```

        ```txt
        # setting type 2
        jdbc:mysql:replication://address=(type=source)(host=source1host),address=(type=source)(host=source2host),address=(type=replica)(host=replica1host)/database
        ```

    - Related properties
        - ha.loadBalanceStrategy: default random, 使用負載平衡連線時的連線選擇策略
        - failOverReadOnly: default true, 當發生故障時是否進入唯讀模式
        - allowSourceDownConnections: default false, 是否允許在主資料庫無法連線時運行
        - allowReplicaDownConnections*3: default false, 是否允許在次要資料庫無法連線時運行
        - readFromSourceWhenNoReplicas: default false, 當次要資料庫無法使用時，是否改由主要資料庫讀取資料
        - retriesAllDown: default 120, 所有 host 嘗試連線次數, 每一輪間隔 250 ms

- Driver: NonRegisteringDriver

    ```java
    switch (conStr.getType()) {
    case SINGLE_CONNECTION:
        return com.mysql.cj.jdbc.ConnectionImpl.getInstance(conStr.getMainHost());

    case FAILOVER_CONNECTION:
    case FAILOVER_DNS_SRV_CONNECTION:
        return FailoverConnectionProxy.createProxyInstance(conStr);

    case LOADBALANCE_CONNECTION:
    case LOADBALANCE_DNS_SRV_CONNECTION:
        return LoadBalancedConnectionProxy.createProxyInstance(conStr);

    case REPLICATION_CONNECTION:
    case REPLICATION_DNS_SRV_CONNECTION:
        return ReplicationConnectionProxy.createProxyInstance(conStr);

    default:
        return null;
    }
    ```


> *1 [Replication configuration](https://dev.mysql.com/doc/refman/8.0/en/replication.html) and [CHANGE MASTER TO Statement](https://dev.mysql.com/doc/refman/8.0/en/change-master-to.html)
>
> *2 部分 frameworks 如 Spring, 可直接指定 @Transactional(readOnly = false) 來讀取 master 的資料
>
> *3 注意[官方說明文件](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-source-replica-replication-connection.html)有部分錯誤, 實際設定應以 Connector 的 PropertyKey 或 [Developer Guide](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html) 為準
