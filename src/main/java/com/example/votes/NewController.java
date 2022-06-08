package com.example.votes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class NewController {
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

        Votes vote = new Votes(titleText.getText(), answ1.getText(), answ2.getText(), answ3.getText());
        DBHandler dbHandler = new DBHandler();
        dbHandler.createVote(vote);
        addVoteButton.getScene().getWindow().hide();
    }

    @FXML
    public void initialize() {
    }

}
