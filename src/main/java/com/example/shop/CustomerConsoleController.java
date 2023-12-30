package com.example.shop;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import static com.example.shop.DataBaseConnector.getProductFromDb;
import static com.example.shop.DataBaseConnector.makePurchase;

public class CustomerConsoleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Product, Category> PurchaseConsoleControllerCategoryColumn;

    @FXML
    private TableColumn<Product, String> PurchaseConsoleControllerNameColumn;

    @FXML
    private TableColumn<Product, Float> PurchaseConsoleControllerPriceColumn;

    @FXML
    private TableView<Product> PurchaseConsoleControllerTableView;

    @FXML
    private Button PurchaseConsoleControllerBasket;

    @FXML
    private Button PurchaseConsoleControllerHistoryPurchaseButton;

    @FXML
    void initialize() throws SQLException {

        getCustomerTableData();
        addButtonToTable();

        PurchaseConsoleControllerHistoryPurchaseButton.setOnAction(x->{


            try {

                changeFormShow("purchaseHistoryController.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });


        PurchaseConsoleControllerBasket.setOnAction(x->{


            try {
                changeFormShow("basket.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });


    }


    private void addButtonToTable() {
        TableColumn<Product, Void> colBtn = new TableColumn("Button Column");
        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {

                    private final Button btn = new Button("Купить");

                    {

                        btn.setOnAction((ActionEvent event) -> {

                            Product product = getTableView().getItems().get(getIndex());

                            if(product.getCount() > 0) {

                                try {
                                    DataBaseConnector.makePurchase(product.getId(), HelloController.clientId);

                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                try {
                                    getCustomerTableData();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }else System.out.println("Недостаточно доваров на складе");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        PurchaseConsoleControllerTableView.getColumns().add(colBtn);

    }

    public void getCustomerTableData() throws SQLException {
        ObservableList<Product> productList = FXCollections.observableArrayList(getProductFromDb());
        PurchaseConsoleControllerTableView.setItems(productList);
        PurchaseConsoleControllerNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("Name"));
        PurchaseConsoleControllerPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Float>("Price"));
        PurchaseConsoleControllerCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Category>("Category"));

    }

//    public void changeFormHide(String str) throws IOException {
//
//        PurchaseConsoleControllerBasket.getScene().getWindow().hide();
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(getClass().getResource(str));
//        fxmlLoader.load();
//        Parent root = fxmlLoader.getRoot();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.hide();
//
//
//    }

    public void changeFormShow(String str) throws IOException {

        PurchaseConsoleControllerBasket.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();


    }

}
