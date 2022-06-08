module com.example.votes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.votes to javafx.fxml;
    exports com.example.votes;
}