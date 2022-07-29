module com.example.loginproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.loginproject to javafx.fxml;
    exports com.example.loginproject;
}