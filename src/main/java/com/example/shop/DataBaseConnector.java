package com.example.shop;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.example.shop.HelloController.clientId;

public class DataBaseConnector {


    public DataBaseConnector() throws SQLException {
    }

    public static Connection connectionToDb() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/shoptest";
        String username = "root";
        String password = "12345-As";

        return DriverManager.getConnection(url, username, password);
    }

    public static void registrationRequest(String login, String password, String role, String name) throws SQLException {

        PreparedStatement prepareStatement = connectionToDb().prepareStatement("insert into user (login,password,role,name) values (?,?,?,?) ;");
        prepareStatement.setString(1, login);
        prepareStatement.setString(2, password);
        prepareStatement.setString(3, role);
        prepareStatement.setString(4, name);

        prepareStatement.executeUpdate();


        System.out.println("Запись добавлена в БД");
    }

    public static boolean isUniqLogin(String login) throws SQLException {


        PreparedStatement prepareStatement = connectionToDb().prepareStatement("Select login from user;");
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {

            if (login.equals(resultSet.getString(1))) {

                return true;

            }

        }

        return false;
    }


    public static String authorisation(String login, String password) throws SQLException {
        PreparedStatement prepareStatement = connectionToDb().prepareStatement("Select login,password,role from user where login = ? and password = ? ;");
        prepareStatement.setString(1, login);
        prepareStatement.setString(2, password);
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {

//            if (login.equals(resultSet.getString(1)) || password.equals(resultSet.getString(2))) {
//                System.out.println("Логин верный");
            return resultSet.getString(3);
        }


        return "Пользователь не найден";
    }


    public static void addProduct(String name, int count, String price, String category) throws SQLException {
        PreparedStatement prepareStatement = connectionToDb().prepareStatement("insert into products (name,count,price,category) values (?,?,?,?) ;");
        prepareStatement.setString(1, name);
        prepareStatement.setInt(2, count);
        prepareStatement.setString(3, price);
        prepareStatement.setString(4, category);

        prepareStatement.executeUpdate();

        System.out.println("Товар добавлен в БД");

    }


    public static ArrayList<Product> getProductFromDb() throws SQLException {

        ArrayList<Product> productsList = new ArrayList<>();

        PreparedStatement prepareStatement = connectionToDb().prepareStatement("Select id, name,count,price,category from shop.products;");

        ResultSet resultSet = prepareStatement.executeQuery();

        while (resultSet.next()) {

            productsList.add(new Product(
                    resultSet.getInt(1)
                    , resultSet.getString(2)
                    , resultSet.getInt(3)
                    , resultSet.getFloat(4)
                    , Category.valueOf(resultSet.getString(5))));
        }

        return productsList;
    }


    public static Integer getCLientId(String login, String password) throws SQLException {
        PreparedStatement prepareStatement = connectionToDb().prepareStatement("Select id,login,password,role from user where login = ? and password = ? ;");
        prepareStatement.setString(1, login);
        prepareStatement.setString(2, password);
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {

//            if (login.equals(resultSet.getString(1)) || password.equals(resultSet.getString(2))) {
//                System.out.println("Логин верный");
            return resultSet.getInt(1);
        }

        return null;
    }

    public static void makePurchase(int productId, int clientId) throws SQLException {
        int productCount = 0;


        if (!isPurchaceExists(clientId, productId)) {


            PreparedStatement prepareStatement = connectionToDb().prepareStatement("insert into purchases (clientid,productid,count) values (?,?,?) ;");
            prepareStatement.setInt(1, clientId);
            prepareStatement.setInt(2, productId);
            prepareStatement.setInt(3, 1);
            prepareStatement.executeUpdate();

            decreaseProductCount(productId);
            System.out.println("Покупка добавлен в БД");
        } else {

            PreparedStatement prepareStatement = connectionToDb().prepareStatement("update purchases as p set p.count = p.count + 1 where p.clientid = ? and p.productid = ?;");
            prepareStatement.setInt(1, clientId);
            prepareStatement.setInt(2, productId);
            prepareStatement.executeUpdate();

            decreaseProductCount(productId);
            System.out.println("Количество товара в покупке увеличено");
        }


    }

    public static Boolean isPurchaceExists(int clientid, int productid) throws SQLException {
        PreparedStatement prepareStatement = connectionToDb().prepareStatement("Select * from purchases where clientid = ? and productid = ? ;");
        prepareStatement.setInt(1, clientid);
        prepareStatement.setInt(2, productid);
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {

            return true;
        }
        return false;
    }

    public static void decreaseProductCount(int productId) throws SQLException {
        PreparedStatement prepareStatement = connectionToDb().prepareStatement("update products as p set p.count = p.count - 1 where id = ?;");
        prepareStatement.setInt(1, productId);
        prepareStatement.executeUpdate();

    }

    public static ArrayList<Product> getDataForBusket(int clientid) throws SQLException {

        ArrayList<Product> productsList = new ArrayList<>();

        PreparedStatement prepareStatement = connectionToDb().prepareStatement("select p.productid, pr.name,p.count,pr.price,pr.category from shop.purchases as p join shop.products as pr on p.productid= pr.id where clientid = ?; ");
        prepareStatement.setInt(1, clientid);
        ResultSet resultSet = prepareStatement.executeQuery();

        while (resultSet.next()) {

            productsList.add(new Product(
                    resultSet.getInt(1)
                    , resultSet.getString(2)
                    , resultSet.getInt(3)
                    , resultSet.getFloat(4)
                    , Category.valueOf(resultSet.getString(5))));
        }
        System.out.println(productsList);
        return productsList;
    }

    public static void addToHistoryPurchase(int clientId, int productId, int count, int orderId) throws SQLException {


        PreparedStatement prepareStatement = connectionToDb().prepareStatement("insert into purchasehistory (clientid,productid,count,orderid) values (?,?,?,?) ;");
        prepareStatement.setInt(1, clientId);
        prepareStatement.setInt(2, productId);
        prepareStatement.setInt(3, count);
        prepareStatement.setInt(4, orderId);
        prepareStatement.executeUpdate();
        System.out.println("Операция добавлена в историю операций");


    }


    public static void clientBusketCleare(int clientId) throws SQLException {
        PreparedStatement prepareStatement = connectionToDb().prepareStatement("Delete from purchases where clientId = ? ;");
        prepareStatement.setInt(1, clientId);
        prepareStatement.executeUpdate();
        System.out.println("Корзина очищена");

    }

    public static void addToOrders() throws SQLException {

        PreparedStatement prepareStatement = connectionToDb().prepareStatement("insert into orders (purchasedate) values (?) ;");
        prepareStatement.setDate(1, new Date(new java.util.Date().getTime()));
        prepareStatement.executeUpdate();
        System.out.println("Операция добавлена в заказы");


    }


    public static int getOrderId() throws SQLException {

        PreparedStatement prepareStatement = connectionToDb().prepareStatement("SELECT orderid FROM orders ORDER BY orderid DESC LIMIT 1;");
        ResultSet resultSet = prepareStatement.executeQuery();
        int orderId = 0;
        while (resultSet.next()) {

            orderId = resultSet.getInt(1);
        }

        return orderId;
    }


    public static ArrayList<Order> getDataFromOrders(int clientId) throws SQLException, ParseException {

        ArrayList<Order> orderList = new ArrayList<>();


        PreparedStatement prepareStatement = connectionToDb().prepareStatement("select ph.orderid, pr.id, pr.name,ph.count,pr.price,pr.category, o.purchasedate from orders as o inner join  purchasehistory as ph on o.orderid = ph.orderid inner join products as pr on ph.productid = pr.id where ph.clientid = ?;");
        prepareStatement.setInt(1, clientId);
        ResultSet resultSet = prepareStatement.executeQuery();

        while (resultSet.next()) {
            int orderId = resultSet.getInt(1);
            String purchaseDate = resultSet.getString(7);
            int productId = resultSet.getInt(2);
            String productName = resultSet.getString(3);
            int productCount = resultSet.getInt(4);
            float productPrice = resultSet.getFloat(5);
            Category productCategory = Category.valueOf(resultSet.getString(6));


//ДОбавить продукты а потом зказаз.

            if (orderList.stream().filter(x -> x.getOrderId() == orderId).count() == 0) {

                Order order = new Order();
                order.setOrderId(orderId);
                order.setPurchaseDate(purchaseDate);

                ArrayList<Product> prList = new ArrayList<>();

                prList.add(new Product(productId, productName, productCount, productPrice, productCategory));
                order.setProductDetails(prList);
                orderList.add(order);
            } else {

                Order oldOrder = orderList.stream().filter(x -> x.getOrderId() == orderId).findFirst().get();
                int index = orderList.indexOf(oldOrder);

                oldOrder.getProductDetails().add(new Product(productId, productName, productCount, productPrice, productCategory));

                orderList.set(index, oldOrder);
            }


        }


        return orderPriceSum(orderList);
    }


    public static ArrayList<Order> orderPriceSum(ArrayList<Order> orderList) throws ParseException {

        for (int i = 0; i < orderList.size(); i++) {


            orderList.get(i).setTotalPrice((float) orderList.get(i).getProductDetails().stream().mapToDouble(x -> x.getPrice()).sum());
            orderList.get(i).setPurchaseDate(dataEditor(orderList.get(i).getPurchaseDate()));

        }

        return orderList;
    }


    public static String dataEditor(String data) throws ParseException {

        String date_s = data;
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = dt.parse(date_s);
        return dt.format(date);
    }


    public static void usersTableDeploy() throws IOException, SQLException {

        Connection conn = DataBaseConnector.connectionToDb();
        Statement stmt = conn.createStatement();

        String tableExists = "DROP TABLE IF EXISTS user;";
        String createTable = "CREATE TABLE `user` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `login` varchar(45) DEFAULT NULL,\n" +
                "  `password` varchar(45) DEFAULT NULL,\n" +
                "  `role` varchar(45) DEFAULT NULL,\n" +
                "  `name` varchar(45) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ")";

        String lockTable = "LOCK TABLES `user` WRITE;";
        String tableUpdate = "INSERT INTO `user` VALUES (1,'Петр','12345','Администратор','Петр'),\n" +
                "(2,'Сергей','12345','Пользователь','Сергей'),\n" +
                "(3,'Мадина','12345','Пользователь','Мадина'),\n" +
                "(4,'Роман','12345','Пользователь','Роман');";
        String unlockTable = "UNLOCK TABLES;";


        stmt.executeUpdate(tableExists);
        stmt.executeUpdate(createTable);
        stmt.executeUpdate(lockTable);
        stmt.executeUpdate(tableUpdate);
        stmt.executeUpdate(unlockTable);


        System.out.println("Развертывание таблицы users завершено");
    }

    public static void productsTableDeploy() throws IOException, SQLException {

        Connection conn = DataBaseConnector.connectionToDb();
        Statement stmt = conn.createStatement();

        String tableExists = "DROP TABLE IF EXISTS products;";
        String createTable = "CREATE TABLE `products` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(45) NOT NULL,\n" +
                "  `count` int NOT NULL,\n" +
                "  `price` decimal(10,2) NOT NULL,\n" +
                "  `category` varchar(45) NOT NULL,\n" +
                "  `image` varchar(45) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ")";

        String lockTable = "LOCK TABLES products WRITE;";
        String tableUpdate = "INSERT INTO `products` VALUES (1,'iPhone 15',46,120.20,'MOBILE_PHONE',NULL),\n" +
                "(2,'Xiaomi MI PAD 5',50,35000.00,'TABLET',NULL),\n" +
                "(3,'Pixel 8 Pro',48,135.50,'MOBILE_PHONE',NULL),\n" +
                "(4,'Pixel 7 Pro',46,75000.20,'MOBILE_PHONE',NULL),\n" +
                "(5,'Nokia 3310',47,15000.30,'MOBILE_PHONE',NULL),\n" +
                "(6,'Vertu Constelation',48,650000.50,'MOBILE_PHONE',NULL),\n" +
                "(7,'Galaxy Tab 6',46,45000.50,'TABLET',NULL),\n" +
                "(8,'HyperPC 1',45,320000.20,'PC',NULL);";
        String unlockTable = "UNLOCK TABLES;";


        stmt.executeUpdate(tableExists);
        stmt.executeUpdate(createTable);
        stmt.executeUpdate(lockTable);
        stmt.executeUpdate(tableUpdate);
        stmt.executeUpdate(unlockTable);


        System.out.println("Развертывание таблицы products завершено");
    }

    public static void ordersTableDeploy() throws IOException, SQLException {

        Connection conn = DataBaseConnector.connectionToDb();
        Statement stmt = conn.createStatement();

        String tableExists = "DROP TABLE IF EXISTS orders;";
        String createTable = "CREATE TABLE `orders` (\n" +
                "  `orderid` int NOT NULL AUTO_INCREMENT,\n" +
                "  `purchasedate` datetime DEFAULT NULL,\n" +
                "  PRIMARY KEY (`orderid`)\n" +
                ")";

        String lockTable = "LOCK TABLES orders WRITE;";
        String tableUpdate = "INSERT INTO `orders` VALUES (19,'2023-11-28 00:00:00'),\n" +
                "(20,'2023-12-01 00:00:00'),\n" +
                "(21,'2023-12-01 00:00:00'),\n" +
                "(22,'2023-12-01 00:00:00'),\n" +
                "(23,'2023-12-10 00:00:00');";
        String unlockTable = "UNLOCK TABLES;";


        stmt.executeUpdate(tableExists);
        stmt.executeUpdate(createTable);
        stmt.executeUpdate(lockTable);
        stmt.executeUpdate(tableUpdate);
        stmt.executeUpdate(unlockTable);


        System.out.println("Развертывание таблицы orders завершено");
    }

    public static void purchasesTableDeploy() throws IOException, SQLException {

        Connection conn = DataBaseConnector.connectionToDb();
        Statement stmt = conn.createStatement();

        String tableExists = "DROP TABLE IF EXISTS purchases;";
        String createTable = "CREATE TABLE `purchases` (\n" +
                "  `clientid` int NOT NULL,\n" +
                "  `productid` int NOT NULL,\n" +
                "  `count` int DEFAULT '0',\n" +
                "  KEY `product_idx` (`productid`),\n" +
                "  KEY `client_idx` (`clientid`),\n" +
                "  CONSTRAINT `client` FOREIGN KEY (`clientid`) REFERENCES `user` (`id`),\n" +
                "  CONSTRAINT `product` FOREIGN KEY (`productid`) REFERENCES `products` (`id`)\n" +
                ")";


        stmt.executeUpdate(tableExists);
        stmt.executeUpdate(createTable);

        System.out.println("Развертывание таблицы purchases завершено");
    }

    public static void purchaseHistoryTableDeploy() throws IOException, SQLException {

        Connection conn = DataBaseConnector.connectionToDb();
        Statement stmt = conn.createStatement();

        String tableExists = "DROP TABLE IF EXISTS purchasehistory;";
        String createTable = "CREATE TABLE `purchasehistory` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `clientid` int DEFAULT NULL,\n" +
                "  `productid` int DEFAULT NULL,\n" +
                "  `count` int DEFAULT NULL,\n" +
                "  `orderid` int DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `orders_idx` (`orderid`),\n" +
                "  KEY `products_idx` (`productid`),\n" +
                "  CONSTRAINT `orders` FOREIGN KEY (`orderid`) REFERENCES `orders` (`orderid`),\n" +
                "  CONSTRAINT `products` FOREIGN KEY (`productid`) REFERENCES `products` (`id`)\n" +
                ")";

        String lockTable = "LOCK TABLES purchasehistory WRITE;";

        String tableUpdate = "INSERT INTO `purchasehistory` VALUES (30,2,8,1,19),\n" +
                "(31,2,1,1,19),\n" +
                "(32,2,7,1,20),\n" +
                "(33,2,4,1,20),\n" +
                "(34,2,5,1,21),\n" +
                "(35,2,5,1,22),\n" +
                "(36,2,6,1,22),\n" +
                "(37,4,8,1,23),\n" +
                "(38,4,7,1,23);";
        String unlockTable = "UNLOCK TABLES;";

        stmt.executeUpdate(tableExists);
        stmt.executeUpdate(createTable);
        stmt.executeUpdate(lockTable);
        stmt.executeUpdate(tableUpdate);
        stmt.executeUpdate(unlockTable);

        System.out.println("Развертывание таблицы purchasehistory завершено");
    }

}




