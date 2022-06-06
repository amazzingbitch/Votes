module com.example.votes {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.votes to javafx.fxml;
    exports com.example.votes;
}