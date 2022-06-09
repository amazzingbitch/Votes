package com.example.votes;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainPageController {

    @FXML
    private ListView<String> listView;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button newButton;

    @FXML
    private Label rL;

    @FXML
    private Label label;

    @FXML
    private Label s1;

    @FXML
    private Label s2;

    @FXML
    private Label s3;

    @FXML
    private Label loginLabel;

    @FXML
    private ToggleGroup sss;

    @FXML
    private Button selectButton;

    @FXML
    private RadioButton rb1;

    @FXML
    private RadioButton rb2;

    @FXML
    private RadioButton rb3;

    @FXML
    private Button statButton;

    private static Timer timer;
    private boolean count = false;
    private int Id = 0;

    @FXML
    void statButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!Objects.equals(label.getText(), "Title of vote")) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("StatPage.fxml"));
            try {
                loader.load();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            StatPageController controller = loader.getController();
            controller.sendInfo(loginLabel.getText(), getId(), label.getText());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }

    }

    @FXML
    void selectButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!Objects.equals(label.getText(), "Title of vote")) {
            if (!voteOrNot()) {
                int n = 0;
                int ID = getId();
                DBHandler dbHandler = new DBHandler();
                ResultSet resultSet2 = null;
                if (rb1.isSelected()) {
                    try {
                        resultSet2 = dbHandler.getAnswer(label.getText());
                    } catch (SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        assert resultSet2 != null;
                        if (resultSet2.next()) {
                            dbHandler.addStatVote1(ID, resultSet2.getInt("answer1stat") + 1);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    n = 1;
                }

                if (rb2.isSelected()) {
                    try {
                        resultSet2 = dbHandler.getAnswer(label.getText());
                    } catch (SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        assert resultSet2 != null;
                        if (resultSet2.next()) {
                            dbHandler.addStatVote2(ID, resultSet2.getInt("answer2stat") + 1);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    n = 2;
                }

                if (rb3.isSelected()) {
                    try {
                        resultSet2 = dbHandler.getAnswer(label.getText());
                    } catch (SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        assert resultSet2 != null;
                        if (resultSet2.next()) {
                            dbHandler.addStatVote3(ID, resultSet2.getInt("answer3stat") + 1);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    n = 3;
                }
                dbHandler.addUserVote(ID, loginLabel.getText(), n);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You already voted", ButtonType.OK);
                alert.showAndWait();
            }
        }

    }

    @FXML
    void newButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("NewPage.fxml"));
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
    void editButtonClick(ActionEvent event) {
        if (!Objects.equals(label.getText(), "Title of vote")) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("EditPage.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            EditPageController cntrl = loader.getController();
            cntrl.sendText(label.getText(), s1.getText(), s2.getText(), s3.getText());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Choose one of the votes in the left list to edit it", ButtonType.OK);
            alert.showAndWait();
        }

    }

    @FXML
    void deleteButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!Objects.equals(label.getText(), "Title of vote")) {
            DBHandler dbHandler = new DBHandler();
            dbHandler.deleteVote(label.getText());
            label.setText("Title of vote");
            s1.setText("Answer 1");
            s2.setText("Answer 2");
            s3.setText("Answer 3");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Choose one of the votes in the left list to delete it", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void initialize() {
        newButton.setVisible(false);
        editButton.setVisible(false);
        deleteButton.setVisible(false);
        timer = new Timer();
        startCycle();
    }

    boolean voteOrNot() throws SQLException, ClassNotFoundException {
        DBHandler dbHandler = new DBHandler();
        ResultSet resultSet = dbHandler.voteOrNot(loginLabel.getText());
        try {
            assert resultSet != null;
            while (resultSet.next()) {
                if (resultSet.getInt("idvotes")==getId()) return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    void setInvisible() throws SQLException, ClassNotFoundException {
        count = true;
        if (Objects.equals(rL.getText(), "admin")) {
            newButton.setVisible(true);
            editButton.setVisible(true);
            deleteButton.setVisible(true);
        }
    }

    private void startCycle() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        update();
                        if (!count) setInvisible();
                    } catch (IOException | SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

            }
        }, 0, 1000);
    }

    private void update() throws IOException, SQLException, ClassNotFoundException {
        DBHandler dbHandler = new DBHandler();
        ResultSet resultSet = dbHandler.getVote();
        listView.getItems().clear();

        while (resultSet.next()) {
            String name = resultSet.getString("titlevotes");
            listView.getItems().add(name);

        }

        listView.setOnMouseClicked(e -> {
            label.setText(listView.getSelectionModel().getSelectedItem());
            /*try {
                System.out.println(voteOrNot());
                if (voteOrNot()) {
                    *//*rb1.setVisible(false);
                    rb2.setVisible(false);
                    rb3.setVisible(false);*//*
                    selectButton.setDisable(true);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }*/
            ResultSet resultSet2 = null;
            try {
                resultSet2 = dbHandler.getAnswer(listView.getSelectionModel().getSelectedItem());
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            String answer1, answer2, answer3;
            try {
                assert resultSet2 != null;
                if (resultSet2.next()) {
                    answer1 = resultSet2.getString("answer1");
                    s1.setText(answer1);
                    answer2 = resultSet2.getString("answer2");
                    s2.setText(answer2);
                    answer3 = resultSet2.getString("answer3");
                    s3.setText(answer3);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });

    }

    public void sendRole(String role, String login) {
        rL.setText(role);
        loginLabel.setText(login);
    }

    int getId() throws SQLException, ClassNotFoundException {
        DBHandler dbH = new DBHandler();
        ResultSet rst = dbH.getAnswer(label.getText());
        try {
            assert rst != null;
            if(rst.next()) {
                return rst.getInt("idvotes");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
