# Demo project - Spring Boot Multiple DataSource

### 1. Create mysql using docker

```
docker pull mysql:5.7.29
docker run -d --name mysql-main -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql:5.7.29
docker run -d --name mysql-sub -p 3307:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql:5.7.29
```

### 2. Table schema

```sql
CREATE DATABASE `test`;
USE `test`;
CREATE TABLE IF NOT EXISTS `product`
(
    `id`              int(11) AUTO_INCREMENT,
    `product_id`      char(32),
    `product_name`    varchar(16),
    PRIMARY KEY (`id`)
);
```