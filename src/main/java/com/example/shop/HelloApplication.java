package com.example.shop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;


import static com.example.shop.DataBaseConnector.getDataFromOrders;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException, SQLException, ParseException {



        System.out.println("Тест " + getDataFromOrders(2));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) throws SQLException, IOException {
        launch();


    }


}