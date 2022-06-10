package com.example.votes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditPageController {

    @FXML
    private TextField answ1;

    @FXML
    private TextField answ2;

    @FXML
    private TextField answ3;

    @FXML
    private Button saveButton;

    @FXML
    private TextField titleText;

    private String startTitle;

    @FXML
    public void initialize() {
    }

    @FXML
    private void saveButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException {
        DBHandler dbHandler = new DBHandler();

        ResultSet resultSet2 = null;
        try {
            resultSet2 = dbHandler.getAnswer(startTitle);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        int id = 0;
        try {
            assert resultSet2 != null;
            if (resultSet2.next()) {
                id = resultSet2.getInt("idvotes");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            resultSet2 = dbHandler.getAnswer(titleText.getText());
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        boolean flag = false;
        try {
            assert resultSet2 != null;
            if (resultSet2.next()) {
                flag = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (!flag) {
            dbHandler.updateVote(id, titleText.getText(), answ1.getText(), answ2.getText(), answ3.getText());
            saveButton.getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vote with this title already exists. Create another title", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void sendText(String title, String answer1, String answer2, String answer3) {
        titleText.setText(title);
        startTitle = title;
        answ1.setText(answer1);
        answ2.setText(answer2);
        answ3.setText(answer3);
    }
}
