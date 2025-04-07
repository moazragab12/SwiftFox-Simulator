package com.example.controllers;
import com.example.App;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class mainController {

    @FXML
    private ChoiceBox<String> algorithmSelector;

    @FXML
    private Button addButton;

    @FXML
    public void initialize() {
        // Populate the ChoiceBox with items
        algorithmSelector.getItems().addAll("First Come First Serve", "Shortest Job First", "Priority Scheduling", "Round Robin");

        // Set a default value
        algorithmSelector.setValue("First Come First Serve");

        // Add a listener to handle selection changes
        algorithmSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected Algorithm: " + newValue);
        });
    }
    @FXML
    private TextField arrivalTime;

    @FXML
    private TextField burstTime;

    @FXML
    private CheckBox liveSimCHK;

    @FXML
    private TextField priority;

    @FXML
    private TextField processName;

    @FXML
    void chkPressed(ActionEvent event) {

    }

    @FXML
    void pressed(ActionEvent event) {

    }
}
