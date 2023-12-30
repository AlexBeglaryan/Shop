package com.example.shop;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.example.shop.DataBaseConnector.*;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AuthorisationPageEnterButton;

    @FXML
    private TextField AuthorisationPageLoginField;

    @FXML
    private PasswordField AuthorisationPagePasswordField;

    @FXML
    private Button AuthorisationPageRegistrationButton;

    public static int clientId;

    @FXML
    void initialize() throws SQLException {


        AuthorisationPageRegistrationButton.setOnAction(x -> {

//            try {
//                changeFormHide("hello-view.fxml");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }


            try {
                changeFormShow("registration.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });

        AuthorisationPageEnterButton.setOnAction(x -> {

            try {
                clientId = getCLientId(AuthorisationPageLoginField.getText(), AuthorisationPagePasswordField.getText());
                if (authorisation(AuthorisationPageLoginField.getText(), AuthorisationPagePasswordField.getText()).equals("Администратор")) {

                    changeFormHide("hello-view.fxml");
                    changeFormShow("productConsole.fxml");


                } else if (authorisation(AuthorisationPageLoginField.getText(), AuthorisationPagePasswordField.getText()).equals("Пользователь")) {

                    changeFormHide("hello-view.fxml");
                    changeFormShow("customerConsole.fxml");

                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });


    }


    public void changeFormHide(String str) throws IOException {

        AuthorisationPageRegistrationButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.hide();


    }

    public void changeFormShow(String str) throws IOException {

        AuthorisationPageRegistrationButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();


    }

}
