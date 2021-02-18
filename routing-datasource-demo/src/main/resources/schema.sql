SET NAMES utf8;

CREATE TABLE IF NOT EXISTS `customer`
(
    `id`              int(11) AUTO_INCREMENT,
    `customerId`      char(32),
    `customerName`    varchar(16),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `product`
(
    `id`              int(11) AUTO_INCREMENT,
    `customerId`      char(32),
    `customerName`    varchar(16),
    PRIMARY KEY (`id`)
);