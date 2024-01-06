DROP TABLE IF EXISTS `purchases`;
CREATE TABLE `purchases` (
  `clientid` int NOT NULL,
  `productid` int NOT NULL,
  `count` int DEFAULT '0',
  KEY `product_idx` (`productid`),
  KEY `client_idx` (`clientid`),
  CONSTRAINT `client` FOREIGN KEY (`clientid`) REFERENCES `user` (`id`),
  CONSTRAINT `product` FOREIGN KEY (`productid`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `purchases` WRITE;
UNLOCK TABLES;
