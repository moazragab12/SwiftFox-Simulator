package com.example.controllers;
import com.example.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class SecondaryController {

    @FXML
    private Button secondaryButton;


    @FXML
    private void switchToPrimary() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        Scene sceneServices = new Scene(fxmlLoader.load(), 1280, 720);
        Stage mainStage = (Stage) secondaryButton.getScene().getWindow();
        mainStage.setScene(sceneServices);    }    
    }
