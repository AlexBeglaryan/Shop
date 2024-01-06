CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
INSERT INTO user VALUES (1,'Петр','12345','Администратор','Петр'),
(2,'Сергей','12345','Пользователь','Сергей'),
(3,'Мадина','12345','Пользователь','Мадина'),
(4,'Роман','12345','Пользователь','Роман');
