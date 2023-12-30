/**
 * Sample Skeleton for 'purchaseHistoryController.fxml' Controller Class
 */

package com.example.shop;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static com.example.shop.DataBaseConnector.getDataFromOrders;
import static com.example.shop.DataBaseConnector.getProductFromDb;

public class PurchaseHistoryController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="PurchaseHistoryControllerBackButton"
    private Button PurchaseHistoryControllerBackButton; // Value injected by FXMLLoader

    @FXML // fx:id="PurchaseHistoryControllerColumnOrderName"
    private TableColumn<Order, Integer> PurchaseHistoryControllerColumnOrderNumber; // Value injected by FXMLLoader

    @FXML // fx:id="PurchaseHistoryControllerColumnPurchaseDate"
    private TableColumn<Order, String> PurchaseHistoryControllerColumnPurchaseDate; // Value injected by FXMLLoader

    @FXML // fx:id="PurchaseHistoryControllerOrderTabel"
    private TableView<Order> PurchaseHistoryControllerOrderTabel; // Value injected by FXMLLoader

    @FXML // fx:id="PurchaseHistoryControllerProductCategory"
    private TableColumn<Product, Category> PurchaseHistoryControllerProductCategory; // Value injected by FXMLLoader

    @FXML // fx:id="PurchaseHistoryControllerProductCount"
    private TableColumn<Product, Integer> PurchaseHistoryControllerProductCount; // Value injected by FXMLLoader

    @FXML // fx:id="PurchaseHistoryControllerProductName"
    private TableColumn<Product, String> PurchaseHistoryControllerProductName; // Value injected by FXMLLoader

    @FXML // fx:id="PurchaseHistoryControllerProductPrice"
    private TableColumn<Product, Float> PurchaseHistoryControllerProductPrice; // Value injected by FXMLLoader

    @FXML
    private TableColumn<Order, Float> PurchaseHistoryControllerColumnTotalPrice;

    @FXML // fx:id="PurchaseHistoryControllerProductTable"
    private TableView<Product> PurchaseHistoryControllerProductTable; // Value injected by FXMLLoader

    @FXML
        // This method is called by the FXMLLoader when initialization is complete


    void initialize() throws SQLException, ParseException {


        getDataToOrderTable();

        PurchaseHistoryControllerOrderTabel.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            Order order = PurchaseHistoryControllerOrderTabel.getSelectionModel().getSelectedItem();
            try {
                getDataToProductTable(order);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });

        PurchaseHistoryControllerBackButton.setOnAction(x -> {


            try {
                changeFormShow("customerConsole.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });
    }


    public void changeFormShow(String str) throws IOException {

        PurchaseHistoryControllerBackButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();


    }

    public void getDataToOrderTable() throws SQLException, ParseException {
        ObservableList<Order> productList = FXCollections.observableArrayList(getDataFromOrders(HelloController.clientId));
        PurchaseHistoryControllerOrderTabel.setItems(productList);
        PurchaseHistoryControllerColumnOrderNumber.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderId"));
        PurchaseHistoryControllerColumnPurchaseDate.setCellValueFactory(new PropertyValueFactory<Order, String>("purchaseDate"));
        PurchaseHistoryControllerColumnTotalPrice.setCellValueFactory(new PropertyValueFactory<Order, Float>("totalPrice"));
    }

    public void getDataToProductTable(Order order) throws SQLException {
        ObservableList<Product> productList = FXCollections.observableArrayList(order.getProductDetails());
        PurchaseHistoryControllerProductTable.setItems(productList);
        PurchaseHistoryControllerProductName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        PurchaseHistoryControllerProductCount.setCellValueFactory(new PropertyValueFactory<Product, Integer>("count"));
        PurchaseHistoryControllerProductPrice.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
        PurchaseHistoryControllerProductCategory.setCellValueFactory(new PropertyValueFactory<Product, Category>("category"));
    }

}



