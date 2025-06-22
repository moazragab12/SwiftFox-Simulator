module com.swiftfox {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.swiftfox to javafx.fxml;
    exports com.swiftfox;
    exports com.swiftfox.scheduler;
    opens com.swiftfox.scheduler to javafx.base;
    opens com.swiftfox.controller to javafx.fxml;
    exports com.swiftfox.controller;
    exports com.swiftfox.model;
    opens com.swiftfox.model to javafx.base;
    exports com.swiftfox.scheduler.algorithms;
    opens com.swiftfox.scheduler.algorithms to javafx.base;
}
