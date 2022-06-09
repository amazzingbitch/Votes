package com.example.votes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;


public class UserPackage implements Serializable {
    public int highScore;
    public String username;
    public String password;
    public String message;
    public SimpleStringProperty login;
    public SimpleIntegerProperty record;


    public UserPackage(String username, String password, String message) {
        this.username = username;
        this.password = password;
        this.message = message;
    }

    /*public UserPackage(String username, String password, int highScore) {
        this.username = username;
        this.password = password;
        this.highScore = highScore;
    }

    public UserPackage(SimpleStringProperty username, SimpleIntegerProperty record) {
        this.login = username;
        this.record = record;

    }*/


    public String getMessage() {
        return message;
    }

    public int getHighScore() {
        return highScore;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
