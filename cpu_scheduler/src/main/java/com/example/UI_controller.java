package com.example;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.schedulers.*;
import com.example.schedulers.Process;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UI_controller implements Initializable {

    @FXML
    private Label ProcessName_label;

    @FXML
    private TextField Average_Waiting_Time_textField;

    @FXML
    private TextField Average_Turnaround_Time_textField;

    @FXML
    private TextField ProcessName_textField;

    @FXML
    private Label SchedulingMethod_label;

    @FXML
    private HBox ghantt_HBox;

    @FXML
    public TableView<com.example.ProcessInfo> table;

    @FXML
    private Button start_btn1;

    @FXML
    private VBox addProcess_VBox;

    @FXML
    private CheckBox liveSimulation_btn;

    @FXML
    private Label summary_label;

    @FXML
    private FlowPane summaryDetails;

    @FXML
    private HBox summaryHBox;

    @FXML
    private ChoiceBox<String> SchedulingMethod_choiceList;


    @FXML
    private Label Average_Turnaround_Time_label;

    @FXML
    private TextField BurstTime_textField;

    @FXML
    private Label Average_Waiting_Time_label;

    @FXML
    private TextField ArrivalTime_textField;

    @FXML
    private Label processesLabel;

    @FXML
    private Label BurstTime_label;

    @FXML
    private Label ArrivalTime_label;

    @FXML
    private VBox summary_HBox;

    @FXML
    private Button addProcess_btn;

    @FXML
    private Label ghantt_label;

    @FXML
    private ImageView reset;


    @FXML
    private TableColumn<com.example.ProcessInfo, String> processName_col;

    @FXML
    private TableColumn<com.example.ProcessInfo, Integer> remaining_col;

    @FXML
    private TableColumn<com.example.ProcessInfo, Integer> arrival_col;

    @FXML
    private TableColumn<com.example.ProcessInfo, Integer> burst_col;

    @FXML
    private TableColumn<com.example.ProcessInfo, Integer> priority_col;

    @FXML
    private Label priority_label;

    @FXML
    private TextField priority_textField;

    @FXML
    private TextField quantumTime_textField;

    @FXML
    private Label quantumTime_label;

    @FXML
    private Button goBack_btn;

    @FXML
    void goBack(ActionEvent event) {
        try {
            // Use App.setRoot() to switch back to the primary window
            App.setRoot("primary"); // The name of your primary window FXML
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        processName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        remaining_col.setCellValueFactory(new PropertyValueFactory<>("remainingTime"));
        arrival_col.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        burst_col.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        priority_col.setCellValueFactory(new PropertyValueFactory<>("priority"));
        priority_textField.setVisible(false);
        priority_label.setVisible(false);
        quantumTime_label.setVisible(false);
        quantumTime_textField.setVisible(false);
        priority_col.setVisible(false);
        SchedulingMethod_choiceList.getItems().addAll("FCFS","SJF (Preemptive)","SJF (Non-Preemptive)","Priority (Preemptive)","Priority (Non-Preemptive)","Round Robin");
        SchedulingMethod_choiceList.setValue("FCFS");
        SchedulingMethod_choiceList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            handleSchedulingChoice(newVal);
        });
    }

    private void handleSchedulingChoice(String newVal) {
        switch (SchedulingMethod_choiceList.getValue()) {
            case "Priority (Preemptive)":
            case "Priority (Non-Preemptive)":
                priority_label.setText("Priority");
                priority_textField.setVisible(true);
                priority_label.setVisible(true);
                priority_col.setVisible(true);
                break;

            case "Round Robin":
                priority_label.setText("Quantum Time (ms)");
                priority_textField.setVisible(true);
                priority_label.setVisible(true);
                //quantumTime_textField.setVisible(true);
                //quantumTime_label.setVisible(true);
                break;

            default:
                quantumTime_textField.setVisible(false);
                quantumTime_label.setVisible(false);
                priority_textField.setVisible(false);
                priority_label.setVisible(false);
                priority_col.setVisible(false);
        }
    }

    @FXML
    public void addProcess(ActionEvent event) {
        //ObservableList<Process> currentTableData = table.getItems();

        String processName = ProcessName_textField.getText();
        int arrivalTime = Integer.parseInt(ArrivalTime_textField.getText());
        int burstTime = Integer.parseInt(BurstTime_textField.getText());

        if (SchedulingMethod_choiceList.getValue().equals("Priority (Preemptive)") ||
                SchedulingMethod_choiceList.getValue().equals("Priority (Non-Preemptive)")) {
            int priority = Integer.parseInt(priority_textField.getText());
            table.getItems().add(new com.example.ProcessInfo(processName, arrivalTime, burstTime,burstTime, priority));
            System.out.println("Adding process: " + processName); //testing

        } else {
            table.getItems().add(new com.example.ProcessInfo(processName, arrivalTime,burstTime,burstTime));
            System.out.println("Adding process: " + processName); //testing

        }
    }


    public void startSimulation(ActionEvent actionEvent) {
        ObservableList<ProcessInfo> allProcesses = table.getItems();
        List<Process> processList = new ArrayList<>();
        switch (SchedulingMethod_choiceList.getValue()) {
            case "FCFS":
                for (ProcessInfo info : table.getItems()) {
                    Process p = new Process(info.getName(), info.getArrivalTime(), info.getBurstTime());
                    processList.add(p);
                }
                Scheduler scheduler = new FCFS();
                GanttChart ganttChart = new GanttChart();
                Simulator simulator = new Simulator(processList, scheduler, ganttChart);
                simulator.runStatic();
                System.out.println("FCFS here");
                break;
            case "SJF (Preemptive)":

                break;
            case "SJF (Non-Preemptive)":
                break;
            case "Priority (Preemptive)":
                break;
            case "Priority (Non-Preemptive)":

                break;

            case "Round Robin":

                break;
        }
        for (Process process : processList) {
            System.out.println("Process: " + process.getName() +
                    ", Arrival: " + process.getArrivalTime() +
                    ", Burst: " + process.getBurstTime() +
                    ", Priority: " + process.getPriority());
            System.out.println(process.getRemainingTime());
        }

    }

}

