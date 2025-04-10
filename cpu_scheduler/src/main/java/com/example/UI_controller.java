package com.example;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.schedulers.*;
import com.example.schedulers.Process;
import javafx.collections.FXCollections;
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
import javafx.scene.input.MouseEvent;

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

    ObservableList<Process> currentTableData = FXCollections.observableArrayList();

    @FXML
<<<<<<< HEAD
    public TableView<com.example.schedulers.Process> table;
=======
    private TableView<com.example.schedulers.Process> table;
    //private TableView<com.example.ProcessInfo> table;
>>>>>>> 230accb5d327185e0d8ef92198f6dfdeb71e078d

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
    private TableColumn<com.example.schedulers.Process, String> processName_col;

    @FXML
    private TableColumn<com.example.schedulers.Process, Integer> remaining_col;

    @FXML
    private TableColumn<com.example.schedulers.Process, Integer> arrival_col;

    @FXML
    private TableColumn<com.example.schedulers.Process, Integer> burst_col;

    @FXML
    private TableColumn<com.example.schedulers.Process, Integer> priority_col;

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

    private ObservableList<com.example.schedulers.Process> processList = FXCollections.observableArrayList();


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
<<<<<<< HEAD
        table.setItems(currentTableData);
        processName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        remaining_col.setCellValueFactory(new PropertyValueFactory<>("remainingTime"));
        arrival_col.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        burst_col.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        priority_col.setCellValueFactory(new PropertyValueFactory<>("priority"));
=======
//        processName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
//        remaining_col.setCellValueFactory(new PropertyValueFactory<>("remainingTime"));
//        arrival_col.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
//        burst_col.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
//        priority_col.setCellValueFactory(new PropertyValueFactory<>("priority"));

        processName_col.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        remaining_col.setCellValueFactory(cellData -> cellData.getValue().remainingTimeProperty().asObject());
        arrival_col.setCellValueFactory(cellData -> cellData.getValue().arrivalTimeProperty().asObject());
        burst_col.setCellValueFactory(cellData -> cellData.getValue().burstTimeProperty().asObject());
        priority_col.setCellValueFactory(cellData -> cellData.getValue().priorityProperty().asObject());

        remaining_col.setCellValueFactory(cellData -> cellData.getValue().remainingTimeProperty().asObject());

        table.setItems(processList);

>>>>>>> 230accb5d327185e0d8ef92198f6dfdeb71e078d
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
      
      
        // Check if the text fields are empty before adding a process
        if (ProcessName_textField.getText().isEmpty() || ArrivalTime_textField.getText().isEmpty() || BurstTime_textField.getText().isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }
        if (SchedulingMethod_choiceList.getValue().equals("Priority (Preemptive)") ||
                SchedulingMethod_choiceList.getValue().equals("Priority (Non-Preemptive)")) {
            if (priority_textField.getText().isEmpty()) {
                System.out.println("Please fill in all fields.");
                return;
            }
        } else if (SchedulingMethod_choiceList.getValue().equals("Round Robin")) {
            if (quantumTime_textField.getText().isEmpty()) {
                System.out.println("Please fill in all fields.");
                return;
            }
        }

        // Check if the input values are valid integers
        try {
            Integer.parseInt(ArrivalTime_textField.getText());
            Integer.parseInt(BurstTime_textField.getText());
            if (SchedulingMethod_choiceList.getValue().equals("Priority (Preemptive)") ||
                    SchedulingMethod_choiceList.getValue().equals("Priority (Non-Preemptive)")) {
                Integer.parseInt(priority_textField.getText());
            } else if (SchedulingMethod_choiceList.getValue().equals("Round Robin")) {
                Integer.parseInt(quantumTime_textField.getText());
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid integer values.");
            return;
        }
        // Check if the input values are non-negative
        if (Integer.parseInt(ArrivalTime_textField.getText()) < 0 || Integer.parseInt(BurstTime_textField.getText()) < 0) {
            System.out.println("Please enter non-negative values.");
            return;
        }
        if (SchedulingMethod_choiceList.getValue().equals("Priority (Preemptive)") ||
                SchedulingMethod_choiceList.getValue().equals("Priority (Non-Preemptive)")) {
            if (Integer.parseInt(priority_textField.getText()) < 0) {
                System.out.println("Please enter non-negative values.");
                return;
            }
        } else if (SchedulingMethod_choiceList.getValue().equals("Round Robin")) {
            if (Integer.parseInt(quantumTime_textField.getText()) <= 0) {
                System.out.println("Please enter positive values for quantum time.");
                return;
            }
        }


        String processName = ProcessName_textField.getText();
        int arrivalTime = Integer.parseInt(ArrivalTime_textField.getText());
        int burstTime = Integer.parseInt(BurstTime_textField.getText());

        int priority = 0;
        if (SchedulingMethod_choiceList.getValue().equals("Priority (Preemptive)") ||
                SchedulingMethod_choiceList.getValue().equals("Priority (Non-Preemptive)")) {
<<<<<<< HEAD

            int priority = Integer.parseInt(priority_textField.getText());
            Process p = new Process(processName, arrivalTime, burstTime, priority);
            currentTableData.add(p);

        } else {
            Process p = new Process(processName, arrivalTime, burstTime);
            currentTableData.add(p);
=======
            priority = Integer.parseInt(priority_textField.getText());
            //table.getItems().add(new com.example.ProcessInfo(processName, arrivalTime, burstTime,burstTime, priority));
            //table.getItems().add(new com.example.schedulers.Process(processName, arrivalTime, burstTime, priority));
            //System.out.println("Adding process: " + processName); //testing
            com.example.schedulers.Process newProcess = new com.example.schedulers.Process(processName, arrivalTime, burstTime, priority);
            processList.add(newProcess);
            table.refresh();

            ProcessName_textField.clear();
            ArrivalTime_textField.clear();
            BurstTime_textField.clear();
            priority_textField.clear();

            System.out.println("Adding process: " + processName);

        } else {
            //table.getItems().add(new com.example.ProcessInfo(processName, arrivalTime,burstTime,burstTime));
            //table.getItems().add(new com.example.schedulers.Process(processName, arrivalTime, burstTime, priority));
            //System.out.println("Adding process: " + processName); //testing

            com.example.schedulers.Process newProcess = new com.example.schedulers.Process(processName, arrivalTime, burstTime);
            processList.add(newProcess);
            table.refresh();

            ProcessName_textField.clear();
            ArrivalTime_textField.clear();
            BurstTime_textField.clear();
            priority_textField.clear();

            System.out.println("Adding process: " + processName);
>>>>>>> 230accb5d327185e0d8ef92198f6dfdeb71e078d
        }

        ProcessName_textField.clear();
        ArrivalTime_textField.clear();
        BurstTime_textField.clear();
        priority_textField.clear();
        quantumTime_textField.clear();
    }


    public void startSimulation(ActionEvent actionEvent) {
<<<<<<< HEAD
        
        
    }
=======
        //ObservableList<ProcessInfo> allProcesses = table.getItems();
        //ObservableList<Process> allProcesses = table.getItems();
        //List<Process> processList = new ArrayList<>();
        switch (SchedulingMethod_choiceList.getValue()) {
            case "FCFS":
//                for (ProcessInfo info : table.getItems()) {
//                    Process p = new Process(info.getName(), info.getArrivalTime(), info.getBurstTime());
//                    processList.add(p);
//                }
                Scheduler scheduler = new FCFS();
                GanttChart ganttChart = new GanttChart();
                Simulator simulator = new Simulator(processList, scheduler, ganttChart, table);
                simulator.runLive();
                System.out.println("FCFS here");
                break;
            case "SJF (Preemptive)":
>>>>>>> 230accb5d327185e0d8ef92198f6dfdeb71e078d

    

    @FXML
    void goBack(MouseEvent event) {

    }

}

