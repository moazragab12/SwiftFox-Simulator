package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class SecondaryController {

    @FXML
     private VBox chartContainer;
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }


}