module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.example to javafx.fxml;
    opens com.example.controllers to javafx.fxml; // Allow access to the controllers package

    exports com.example;
    exports com.example.controllers;
}
