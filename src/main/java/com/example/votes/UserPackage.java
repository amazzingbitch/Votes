package com.example.votes;


import java.io.Serializable;


public class UserPackage implements Serializable {
    public String username;
    public String password;
    public String message;
    public String role;
    public String title;
    public String starttitle;
    public String body;


    public UserPackage(String username, String password, String message) {
        this.username = username;
        this.password = password;
        this.message = message;
    }
    public UserPackage(String username, String message) {
        this.username = username;
        this.message = message;
    }
    public UserPackage(String username, String password, String role, String message) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.message = message;
    }

    public UserPackage(String title, String body, String message, int i) {
        this.title = title;
        this.body = body;
        this.message = message;
    }

    public UserPackage(String title, String message, int i) {
        this.title = title;
        this.message = message;
    }

    public UserPackage(String starttitle, String title, String body, String message, int i) {
        this.starttitle = starttitle;
        this.title = title;
        this.body = body;
        this.message = message;
    }

    public UserPackage(String title, String message, boolean i) {
        this.title = title;
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

}
