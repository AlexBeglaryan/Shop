package com.example.shop;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import static com.example.shop.DataBaseConnector.*;

public class ProductConsoleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ProductConsoleAddButton;

    @FXML
    private ComboBox<Category> ProductConsoleCategoryComboBox;

    @FXML
    private TableColumn<Product, Category> ProductConsoleControllerColumnCategory;

    @FXML
    private TableColumn<Product, Integer> ProductConsoleControllerColumnCount;

    @FXML
    private TableColumn<Product, Integer> ProductConsoleControllerColumnId;

    @FXML
    private TableColumn<Product, String> ProductConsoleControllerColumnName;

    @FXML
    private TableColumn<Product, Float> ProductConsoleControllerColumnPrice;


    @FXML
    private TextField ProductConsoleCountField;

    @FXML
    private Button ProductConsoleDeleteButton;

    @FXML
    private TextField ProductConsoleNameField;

    @FXML
    private TextField ProductConsolePriceField;

    @FXML
    private TableView<Product> ProductConsoleTableView;



    @FXML
    void initialize() throws SQLException {
        ObservableList<Category> categoryList = FXCollections.observableArrayList(Category.values());
        ProductConsoleCategoryComboBox.setItems(categoryList);
        getTableData();

        ProductConsoleAddButton.setOnAction(event -> {

            try {
                addProduct(ProductConsoleNameField.getText(), Integer.parseInt(ProductConsoleCountField.getText()), String.valueOf(Float.parseFloat(ProductConsolePriceField.getText())), String.valueOf(ProductConsoleCategoryComboBox.getValue()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            try {
                getTableData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        });


    }


    public void getTableData() throws SQLException {

        ObservableList<Product> productList = FXCollections.observableArrayList(getProductFromDb());
        ProductConsoleTableView.setItems(productList);

        ProductConsoleControllerColumnId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("Id"));
        ProductConsoleControllerColumnName.setCellValueFactory(new PropertyValueFactory<Product, String>("Name"));
        ProductConsoleControllerColumnCount.setCellValueFactory(new PropertyValueFactory<Product, Integer>("Count"));
        ProductConsoleControllerColumnPrice.setCellValueFactory(new PropertyValueFactory<Product, Float>("Price"));
        ProductConsoleControllerColumnCategory.setCellValueFactory(new PropertyValueFactory<Product, Category>("Category"));


    }

}
