package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.schedulers.*;
import com.example.schedulers.Process;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

//import static com.example.schedulers.Main.chart;

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
    private VBox ghantt_VBox;

    private ObservableList<Process> currentTableData = FXCollections.observableArrayList();

    @FXML
    public TableView<com.example.schedulers.Process> table;

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

    private HBox rectanglesBox = new HBox();
    private HBox timelineBox = new HBox();
    GanttChart chart;
    Simulator simulator;

    @FXML
    void goBack(ActionEvent event) {
        try {
            // Use App.setRoot() to switch back to the primary window
            App.setRoot("primary"); // The name of your primary window FXML
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.setItems(currentTableData);
        timelineBox.setAlignment(Pos.BOTTOM_LEFT);
        ghantt_VBox.getChildren().addAll(rectanglesBox, timelineBox);
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
        SchedulingMethod_choiceList.getItems().addAll("FCFS", "SJF (Preemptive)", "SJF (Non-Preemptive)", "Priority (Preemptive)", "Priority (Non-Preemptive)", "Round Robin");
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
        }
        catch (NumberFormatException e) {
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

            priority = Integer.parseInt(priority_textField.getText());
            Process p = new Process(processName, arrivalTime, burstTime, priority);
            currentTableData.add(p);

        } else {
            Process p = new Process(processName, arrivalTime, burstTime);
            currentTableData.add(p);
        }

        ProcessName_textField.clear();
        ArrivalTime_textField.clear();
        BurstTime_textField.clear();
        priority_textField.clear();
        quantumTime_textField.clear();
    }

    public void startSimulation(ActionEvent actionEvent) {
        chart = new GanttChart();
        simulator = new Simulator(currentTableData, new Priority(false), chart);
        ObservableList<GanttEntry> entries = chart.getEntries();

        entries.addListener(new ListChangeListener<GanttEntry>() {
            @Override
            public void onChanged(Change<? extends GanttEntry> change) {
                // Call ProccessHandler whenever the list changes
                ProccessHandler();
            }
        });
        if (!liveSimulation_btn.isSelected())
            simulator.runStatic();
        else
            new Thread(task).start();


    }

    Task<Void> task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            simulator.runLive();
            return null;
        }
    };


    private void ProccessHandler() {
        int tasksSize = chart.getEntries().size();
        if (!liveSimulation_btn.isSelected()) {
            shownewproccess(tasksSize);
        } else {
            Platform.runLater(() -> {
                if (chart.getEntries().isEmpty()) {
                    System.out.println("Please add processes to the table before starting the simulation.");
                    return;
                }
                shownewproccess(tasksSize);
            });
        }
    }

    private void shownewproccess(int i) {
        String taskname = "IDLE ";
        //  System.out.println("Process ID: " + i);
        if (chart.getEntries().get(i - 1).getProcess() != null) {
            taskname = chart.getEntries().get(i - 1).getProcess().getName();
        }

        // Create rectangle with text
        Text centerText = new Text();
        centerText.setText(taskname.length() > 6 ? taskname.substring(0, 6) + "..." : taskname);
        centerText.setFill(Color.BLACK);
        centerText.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        centerText.setWrappingWidth(50 - 10);
        Rectangle rectangle = new Rectangle(50, 70);
        rectangle.setFill(Color.DARKCYAN);
        rectangle.setStroke(Color.BLACK);
        StackPane stackPane = new StackPane(rectangle, centerText);
        rectanglesBox.getChildren().add(stackPane);
        Tooltip tooltip = new Tooltip(taskname);
        Tooltip.install(stackPane, tooltip);

        // Create number with alignment
        Text numberText = new Text(String.valueOf(i));
        numberText.setStyle("-fx-font-size: 12px;");

        StackPane numberContainer = new StackPane(numberText);
        numberContainer.setMinWidth(50);
        numberContainer.setAlignment(Pos.BOTTOM_RIGHT);

        timelineBox.getChildren().add(numberContainer);
    }

    @FXML
    void goBack(MouseEvent event) {

    }

}

