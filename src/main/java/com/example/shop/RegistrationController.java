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
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import static com.example.shop.DataBaseConnector.isUniqLogin;
import static com.example.shop.DataBaseConnector.registrationRequest;

public class RegistrationController {

    @FXML
    private Button RegistrationControllerBackBatton;

    @FXML
    private TextField RegistrationControllerLoginField;

    @FXML
    private TextField RegistrationControllerNameField;

    @FXML
    private PasswordField RegistrationControllerPasswordField;

    @FXML
    private Button RegistrationControllerRegisterButton;



    @FXML
    void initialize() {
        RegistrationControllerRegisterButton.setOnAction(x -> {

            try {
                if (isUniqLogin(RegistrationControllerLoginField.getText())) {
                    return;
                }
                registrationRequest(RegistrationControllerLoginField.getText(), RegistrationControllerPasswordField.getText(), "Пользователь", RegistrationControllerNameField.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        });
        RegistrationControllerBackBatton.setOnAction(y -> {
            try {
                changeFormHide("registration.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                changeFormShow("hello-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });




    }

    public void changeFormShow(String str) throws IOException {

       RegistrationControllerBackBatton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();


    }

    public void changeFormHide(String str) throws IOException {

        RegistrationControllerBackBatton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.hide();


    }

}
