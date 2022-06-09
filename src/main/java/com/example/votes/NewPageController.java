package com.example.votes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewPageController {
    @FXML
    private Button addVoteButton;

    @FXML
    private TextField answ1;

    @FXML
    private TextField answ2;

    @FXML
    private TextField answ3;

    @FXML
    private TextField titleText;

    @FXML
    public void AddVoteButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!titleText.getText().trim().equals("") && !answ1.getText().trim().equals("") && !answ2.getText().trim().equals("") && !answ3.getText().trim().equals("")){
            DBHandler dbHandler = new DBHandler();
            ResultSet resultSet = dbHandler.getAnswer(titleText.getText());
            boolean flag = false;
            try {
                assert resultSet != null;
                if (resultSet.next()) {
                    flag = true;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            if (!flag && !titleText.getText().equals("Title of vote")) {
                Votes vote = new Votes(titleText.getText(), answ1.getText(), answ2.getText(), answ3.getText());
                dbHandler.createVote(vote);
                addVoteButton.getScene().getWindow().hide();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vote with this title already exists. Create another title", ButtonType.OK);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Title or one of Answers is empty", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void initialize() {
    }

}
