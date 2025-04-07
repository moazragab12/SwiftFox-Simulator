package com.example.controllers;

import com.example.App;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class welcomingController {

    @FXML
    private Button EnterSimulationButton;

    @FXML
    private AnchorPane anchor;

    @FXML
    void switchToMainScene(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
            Scene sceneServices = new Scene(fxmlLoader.load(), 1280, 720);
            Stage mainStage = (Stage) EnterSimulationButton.getScene().getWindow();
            mainStage.setScene(sceneServices);
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, you can show an error dialog to the user here
        }
    }
}


