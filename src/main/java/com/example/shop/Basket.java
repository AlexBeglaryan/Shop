package com.example.shop;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static com.example.shop.DataBaseConnector.*;

public class Basket {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button basketBackButton;

    @FXML
    private Label basketCostSum;

    @FXML
    private Button basketPurchaseButton;

    @FXML
    private TableView<Product> basketTableView;

    @FXML
    private TableColumn<Product, Category> busketCategoryColumn;

    @FXML
    private TableColumn<Product, Integer> busketCountColumn;

    @FXML
    private TableColumn<Product, String> busketNameColumn;

    @FXML
    private TableColumn<Product, Float> busketPriceColumn;

    @FXML
    void initialize() throws SQLException {
        getTableData(HelloController.clientId);

        basketBackButton.setOnAction(x -> {
            try {
                changeFormHide("basket.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                changeFormShow("customerConsole.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });

        basketCostSum.setText(String.valueOf((float) getDataForBusket(HelloController.clientId).stream().mapToDouble(Product::getPrice).sum()));

        basketPurchaseButton.setOnAction(x -> {

            try {
                addToOrders();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            try {
                ArrayList<Product> pList = getDataForBusket(HelloController.clientId);
                int orderId = getOrderId();

                for (int i = 0; i < pList.size(); i++) {

                    DataBaseConnector.addToHistoryPurchase(HelloController.clientId, pList.get(i).getId(), pList.get(i).getCount(),orderId);

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                clientBusketCleare(HelloController.clientId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                getTableData(HelloController.clientId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void changeFormHide(String str) throws IOException {

        basketBackButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.hide();


    }

    public void changeFormShow(String str) throws IOException {

        basketBackButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();


    }


    public void getTableData(int clientId) throws SQLException {

        ObservableList<Product> productList = FXCollections.observableArrayList(getDataForBusket(clientId));
        basketTableView.setItems(productList);


        busketNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("Name"));
        busketCountColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("Count"));
        busketPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Float>("Price"));
        busketCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Category>("Category"));


    }

}
