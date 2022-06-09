package com.example.votes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatPageController {
    @FXML
    private Label a1;

    @FXML
    private Label a2;

    @FXML
    private Label a3;

    @FXML
    private Label allvotes;

    @FXML
    private Label label;

    @FXML
    private Label num1;

    @FXML
    private Label num2;

    @FXML
    private Label num3;

    @FXML
    private Label perc2;

    @FXML
    private Label perc3;

    @FXML
    private Label prec1;

    @FXML
    private Label useransw;

    @FXML
    public void initialize() {

    }

    void sendInfo(String login, int id, String lab) {
        label.setText(lab);


        DBHandler dbHandler = new DBHandler();
        ResultSet resultSet2 = null, resultSet = null;
        int a_1 = 0, a_2 = 0, a_3 = 0, var = 0;
        System.out.println("user " + login);
        try {
            resultSet2 = dbHandler.voteOrNot(login);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        String answer = null;
        try {
            assert resultSet2 != null;
            while (resultSet2.next()) {
                if (resultSet2.getInt("idvotes")== id) {
                    var = resultSet2.getInt("numansw");
                    break;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("var = " + var);
        try {
            resultSet = dbHandler.getAnswer(lab);
            System.out.println(label.getText());
            System.out.println(resultSet);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        String an1 = null, an2 = null, an3 = null;
        try {
            assert resultSet != null;
            if (resultSet.next()) {
                a_1 = resultSet.getInt("answer1stat");
                a_2 = resultSet.getInt("answer2stat");
                a_3 = resultSet.getInt("answer3stat");
                an1 = resultSet.getString("answer1");
                an2 = resultSet.getString("answer2");
                an3 = resultSet.getString("answer3");
                System.out.println("a1 = " + a1);
                System.out.println("a2 = " + a2);
                System.out.println("a3 = " + a3);
                answer = resultSet.getString("answer"+ var);
                System.out.println("answer = " + answer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        a1.setText(an1);
        a2.setText(an2);
        a3.setText(an3);
        num1.setText(String.valueOf(a_1));
        num2.setText(String.valueOf(a_2));
        num3.setText(String.valueOf(a_3));
        allvotes.setText(String.valueOf(a_1+a_2+a_3));
        useransw.setText(answer);
        prec1.setText(String.valueOf((double) a_1/(a_1+a_2+a_3)*100)+"%");
        perc2.setText(String.valueOf((double)a_2/(a_1+a_2+a_3)*100)+"%");
        perc3.setText(String.valueOf((double)a_3/(a_1+a_2+a_3)*100)+"%");
    }

}
