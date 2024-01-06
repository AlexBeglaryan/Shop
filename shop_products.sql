DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `count` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `category` varchar(45) NOT NULL,
  `image` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `products` WRITE;
INSERT INTO `products` VALUES (1,'iPhone 15',46,120.20,'MOBILE_PHONE',NULL),(2,'Xiaomi MI PAD 5',50,35000.00,'TABLET',NULL),(3,'Pixel 8 Pro',48,135.50,'MOBILE_PHONE',NULL),(4,'Pixel 7 Pro',46,75000.20,'MOBILE_PHONE',NULL),(5,'Nokia 3310',47,15000.30,'MOBILE_PHONE',NULL),(6,'Vertu Constelation',48,650000.50,'MOBILE_PHONE',NULL),(7,'Galaxy Tab 6',46,45000.50,'TABLET',NULL),(8,'HyperPC 1',45,320000.20,'PC',NULL);
