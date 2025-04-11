module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.example to javafx.fxml;
    exports com.example;
    exports com.example.schedulers;
    opens com.example.schedulers to javafx.base;
}
