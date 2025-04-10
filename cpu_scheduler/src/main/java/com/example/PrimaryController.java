package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PrimaryController {
//
//    @FXML
//    private void switchToSecondary() throws IOException {
//        App.setRoot("secondary");
//    }



@FXML
private Button launchBtn;

    @FXML
    private ImageView logoImage;

    @FXML
    public void initialize() {
        logoImage.setImage(new Image(getClass().getResource("@images/logo.png").toExternalForm())); // Add logo to resources/images

        launchBtn.setOnAction(e -> {
            try {
                App.setRoot("updated-UI"); // your main FXML
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}