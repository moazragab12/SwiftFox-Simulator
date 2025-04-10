package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class java {
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label ProcessName_label;

    @FXML
    private VBox addProcess_VBox;

    @FXML
    private AnchorPane table_box;

    @FXML
    private Button start_btn;

    @FXML
    private CheckBox liveSimulation_btn;

    @FXML
    private Label summary_label;

    @FXML
    private FlowPane summaryDetails;

    @FXML
    private HBox summaryHBox;

    @FXML
    private ChoiceBox<?> SchedulingMethod_choiceList;

    @FXML
    private TextField BurstTime_textField;

    @FXML
    private Label Average_Turnaround_Time_label;

    @FXML
    private TextField Average_Waiting_Time_label;

    @FXML
    private TextField ArrivalTime_textField;

    @FXML
    private Label processesLabel;

    @FXML
    private Label BurstTime_label;

    @FXML
    private Label ArrivalTime_label;

    @FXML
    private Button addProcess_btn;

    @FXML
    private VBox summary_HBox;

    @FXML
    private TextField ProcessName_textField;

    @FXML
    private TextField Average_Turnaround_Time_textField;

    @FXML
    private TextField Average_Waiting_Time_textField;

    @FXML
    private Label ghantt_label;

    @FXML
    private Label SchedulingMethod_label;

    @FXML
    private HBox ghantt_HBox;

    @FXML
    private TableView<?> table;





}







