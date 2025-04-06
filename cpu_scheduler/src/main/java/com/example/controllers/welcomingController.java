package com.example.controllers;
import com.example.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class welcomingController {
    @FXML
    private Button primaryButton;

    @FXML
    private void switchToSecondary() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("secondary.fxml"));
        Scene sceneServices = new Scene(fxmlLoader.load(), 1280, 720);
        Stage mainStage = (Stage) primaryButton.getScene().getWindow();
        mainStage.setScene(sceneServices);    }
}
