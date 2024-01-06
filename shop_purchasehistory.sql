DROP TABLE IF EXISTS `purchasehistory`;
CREATE TABLE `purchasehistory` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clientid` int DEFAULT NULL,
  `productid` int DEFAULT NULL,
  `count` int DEFAULT NULL,
  `orderid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `orders_idx` (`orderid`),
  KEY `products_idx` (`productid`),
  CONSTRAINT `orders` FOREIGN KEY (`orderid`) REFERENCES `orders` (`orderid`),
  CONSTRAINT `products` FOREIGN KEY (`productid`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `purchasehistory` WRITE;
INSERT INTO `purchasehistory` VALUES (30,2,8,1,19),(31,2,1,1,19),(32,2,7,1,20),(33,2,4,1,20),(34,2,5,1,21),(35,2,5,1,22),(36,2,6,1,22),(37,4,8,1,23),(38,4,7,1,23);
UNLOCK TABLES;
