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
import java.util.Objects;


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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Username or Password is empty\n", ButtonType.YES);
        String role = null;
        if(selectAdmin.isSelected()){
            role = "admin";
        }
        if(selectUser.isSelected()){
            role = "user";
        }
        if(!loginText.getText().trim().equals("")&&!passwordText.getText().trim().equals("")){
            if(checkUser(loginText.getText().trim())) {
                Client.SignUp(loginText.getText(), passwordText.getText(), role);

                if (Objects.equals(Client.getResponse(), "SUCCESS")) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(App.class.getResource("MainPage.fxml"));
                    try{
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    MainPageController controller = loader.getController();
                    controller.sendRole(role, loginText.getText());
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                }

            }
        }
        else{
            alert.showAndWait();
        }
    }

    private boolean checkUser(String loginText) throws SQLException, ClassNotFoundException {
        Client.checkUser(loginText);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User with this login already exist\n", ButtonType.YES);
        String resp = Client.getResponse();
        System.out.println(resp);
        if(Objects.equals(resp, "0")){
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    void initialize() {

    }

}
