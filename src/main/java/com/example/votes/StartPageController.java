package com.example.votes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class StartPageController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button signupButton;

    @FXML
    void signupButtonClick(ActionEvent event) {
        signupButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("RegPage.fxml"));
        try {
            loader.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    void loginButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException {
        String login = loginText.getText().trim();
        String password = passwordText.getText().trim();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Login or Password is empty", ButtonType.OK);
        if (!login.equals("") && !password.equals("")) {
            loginUser(login, password);
        } else {
            alert.showAndWait();
        }

    }

    private void loginUser(String login, String password) throws SQLException, ClassNotFoundException {
        Client.loginUser(login, password);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User with this login or password not founded", ButtonType.OK);
        String response = Client.getResponse();
        if (Objects.equals(response, "correct")) {
            loginButton.getScene().getWindow().hide();
            openMainW(getRole(login), login);
        } else {
            alert.showAndWait();
        }
    }

    @FXML
    void openMainW(String role, String login) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("MainPage.fxml"));
        try {
            loader.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        MainPageController cntrl = loader.getController();
        cntrl.sendRole(role, login);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }


    String getRole(String login){
        Client.Role(login);
        return Client.getResponse();
    }


    @FXML
    void initialize() {
    }
}
