package com.example.votes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        DBHandler dbHandler = new DBHandler();
        ResultSet resultSet = dbHandler.getUser(login, password);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User with this login or password not founded", ButtonType.OK);

        int k = 0;
        while (resultSet.next()) {
            k++;
        }
        if (k > 0) {
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
        DBHandler dbH = new DBHandler();
        ResultSet resultSet2 = null;
        try {
            resultSet2 = dbH.getLogin(login);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        String role = null;
        try {
            assert resultSet2 != null;
            if(resultSet2.next()) {
                System.out.println(resultSet2);
                role = resultSet2.getString("role");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return role;
    }


    @FXML
    void initialize() {
    }
}
