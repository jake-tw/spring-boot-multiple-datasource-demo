# MySql Replication

- MySql 的 multi-connection 策略，官方叫 server failover, 在 failover 的基礎上演變而來的則是 loadbalance 和 replication, 分別對應 Cluster 架構和 Replication 架構。

- Java connector url setting
    - Failover: 當主要資料庫連不上時自動切換至次要的資料庫，並自動嘗試恢復主要資料庫的連線

        ```txt
        jdbc:mysql://[primary host][:port],[secondary host 1][:port][,[secondary host 2][:port]]...[/[database]][?propertyName1=propertyValue1[&propertyName2=propertyValue2]...]
        ```

    - Loadbalance: 自動切換使用的資料庫

        ```txt
        jdbc:mysql:loadbalance://[host1][:port],[host2][:port][,[host3][:port]]...[/[database]][?propertyName1=propertyValue1[&propertyName2=propertyValue2]...]
        ```

    - Replication: 主要資料庫寫入, 次要資料庫讀取

        ```txt
        # setting type 1
        jdbc:mysql:replication://[source host][:port],[replica host 1][:port][,[replica host 2][:port]]...[/[database]][?propertyName1=propertyValue1[&propertyName2=propertyValue2]...]
        ```

        ```txt
        # setting type 2
        jdbc:mysql:replication://address=(type=source)(host=source1host),address=(type=source)(host=source2host),address=(type=replica)(host=replica1host)/database
        ```

    - Related properties
        - ha.loadBalanceStrategy: default random, 使用附載平衡連線時的連線選擇策略
        - failOverReadOnly: default true, 當發生故障時是否進入唯讀模式
        - allowSourceDownConnections: default false, 是否允許在主資料庫無法連線時運行
        - allowReplicaDownConnections *1: default false, 是否允許在次要資料庫無法連線時運行
        - readFromSourceWhenNoReplicas: default false, 當次要資料庫無法使用時，是否改由主要資料庫讀取資料
        - retriesAllDown: default 120, 所有 host 嘗試連線次數, 每一輪間隔 250 ms

- NonRegisteringDriver: 依照 url 選擇 proxy

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


> *1 注意[官方說明文件](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-source-replica-replication-connection.html)有部分錯誤, 實際設定應以 Connector 的 PropertyKey 或 [Developer Guide](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html) 為準