package com.example.votes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RegPageController {

    @FXML
    private Button createButton;

    @FXML
    private ToggleGroup first;

    @FXML
    private TextField loginText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private RadioButton selectAdmin;

    @FXML
    private RadioButton selectUser;

    @FXML
    void createButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException {
        DBHandler dbHandler = new DBHandler();
        String role = "";
        if (selectAdmin.isSelected()) {
            role = "admin";
        } else {
            if (selectUser.isSelected()) {
                role = "user";
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Login or Password is empty", ButtonType.OK);

        if (loginText.getText().equals("") || passwordText.getText().equals("")) {
            alert.showAndWait();
        } else {
            ResultSet resultSet = dbHandler.getLogin(loginText.getText());
            Alert alert_ = new Alert(Alert.AlertType.CONFIRMATION, "User with this login already exist. Create another login", ButtonType.OK);

            int k = 0; // counter
            while (resultSet.next()) { // if user founded when counter++
                k++;
            }

            if (k == 0) {
                dbHandler.signUpUser(loginText.getText(), passwordText.getText(), role);
                createButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(App.class.getResource("MainPage.fxml"));
                try {
                    loader.load();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Parent root = loader.getRoot();
                MainPageController cntrl = loader.getController();
                cntrl.sendRole(role, loginText.getText());
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } else {
                alert_.showAndWait();
            }
        }
    }

    @FXML
    void initialize() {

    }

}
