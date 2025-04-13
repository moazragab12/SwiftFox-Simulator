package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.example.schedulers.FCFS;
import com.example.schedulers.GanttChart;
import com.example.schedulers.GanttEntry;
import com.example.schedulers.Priority;
import com.example.schedulers.Process;
import com.example.schedulers.RR;
import com.example.schedulers.Results;
import com.example.schedulers.SJF;
import com.example.schedulers.Scheduler;
import com.example.schedulers.Simulator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private ScrollPane ghantt_VBox;

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
    private Label priorityQuantum_label;

    @FXML
    private TextField priorityQuantum_textField;

    @FXML
    private TextField quantumTime_textField;

    @FXML
    private Label quantumTime_label;

    @FXML
    private Button goBack_btn;

    private HBox rectanglesBox = new HBox();
    private HBox timelineBox = new HBox();
    Scheduler scheduler;
    GanttChart chart;
    Simulator simulator;
    private VBox Chartcontainer= new VBox();
    private  int idx;
    HashMap<Integer, Color> ProcessColors ;
    int rr;
    boolean isRunning = false;
    private List <Color>lightColors= List.of(Color.LIGHTBLUE, Color.LIGHTGREEN, Color.LIGHTPINK, Color.LIGHTYELLOW, Color.LIGHTCORAL, Color.LIGHTSALMON, Color.LIGHTCYAN, Color.LIGHTGOLDENRODYELLOW);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.setItems(currentTableData);
        timelineBox.setAlignment(Pos.BOTTOM_LEFT);
        ghantt_VBox.setContent(Chartcontainer);
        Chartcontainer.getChildren().addAll(rectanglesBox, timelineBox);
        processName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        remaining_col.setCellValueFactory(new PropertyValueFactory<>("remainingTime"));
        arrival_col.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        burst_col.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        priority_col.setCellValueFactory(new PropertyValueFactory<>("priority"));
        priorityQuantum_textField.setVisible(false);
        priorityQuantum_label.setVisible(false);
        quantumTime_label.setVisible(false);
        quantumTime_textField.setVisible(false);
        priority_col.setVisible(false);
        SchedulingMethod_choiceList.getItems().addAll("FCFS", "SJF (Preemptive)", "SJF (Non-Preemptive)", "Priority (Preemptive)", "Priority (Non-Preemptive)", "Round Robin");
        SchedulingMethod_choiceList.setValue("FCFS");
        SchedulingMethod_choiceList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            handleSchedulingChoice(newVal);
        });
        ProcessColors = new HashMap<>();
    }

    private void handleSchedulingChoice(String newVal) {
        switch (SchedulingMethod_choiceList.getValue()) {
            case "Priority (Preemptive)":
            case "Priority (Non-Preemptive)":
                priorityQuantum_label.setText("Priority");
                priorityQuantum_textField.setVisible(true);
                priorityQuantum_label.setVisible(true);
                priority_col.setVisible(true);
                break;

            case "Round Robin":
                priorityQuantum_label.setText("Quantum Time (ms)");
                priorityQuantum_textField.setVisible(true);
                priorityQuantum_label.setVisible(true);
                priority_col.setVisible(false);
                //quantumTime_textField.setVisible(true);
                //quantumTime_label.setVisible(true);
                break;

            default:
                quantumTime_textField.setVisible(false);
                quantumTime_label.setVisible(false);
                priorityQuantum_textField.setVisible(false);
                priorityQuantum_label.setVisible(false);
                priority_col.setVisible(false);
        }
    }

    @FXML
    public void addProcess(ActionEvent event) {
        int arrivalTime;
        if (isRunning) { 
            if(ArrivalTime_textField.getText().isEmpty()){
                arrivalTime=chart.getEntries().size();
             }
             else{
                arrivalTime=chart.getEntries().size()+Integer.parseInt(ArrivalTime_textField.getText());
             }
        }else{
         arrivalTime = Integer.parseInt(ArrivalTime_textField.getText());}
         ArrivalTime_textField.setText(arrivalTime+"");

        // Check if the text fields are empty before adding a process
        if (ProcessName_textField.getText().isEmpty() || ArrivalTime_textField.getText().isEmpty() || BurstTime_textField.getText().isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }
        if (SchedulingMethod_choiceList.getValue().equals("Priority (Preemptive)") ||
                SchedulingMethod_choiceList.getValue().equals("Priority (Non-Preemptive)")) {
            if (priorityQuantum_textField.getText().isEmpty()) {
                System.out.println("Please fill in all fields.");
                return;
            }
        } else if (SchedulingMethod_choiceList.getValue().equals("Round Robin")) {
            if (priorityQuantum_textField.getText().isEmpty()) {
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
                Integer.parseInt(priorityQuantum_textField.getText());
            } else if (SchedulingMethod_choiceList.getValue().equals("Round Robin")) {
                if(table.getItems().isEmpty()){
                    rr = Integer.parseInt(priorityQuantum_textField.getText());
                    priorityQuantum_textField.setEditable(false);
                }
                else{
                    priorityQuantum_textField.setText(rr+"");
                    
                    
                }
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
            if (Integer.parseInt(priorityQuantum_textField.getText()) < 0) {
                System.out.println("Please enter non-negative values.");
                return;
            }
        } else if (SchedulingMethod_choiceList.getValue().equals("Round Robin")) {
            if (Integer.parseInt(priorityQuantum_textField.getText()) <= 0) {
                System.out.println("Please enter positive values for quantum time.");
                return;
            }
        }


        String processName = ProcessName_textField.getText();
       
        int burstTime = Integer.parseInt(BurstTime_textField.getText());

        int priority = 0;
        if (SchedulingMethod_choiceList.getValue().equals("Priority (Preemptive)") ||
                SchedulingMethod_choiceList.getValue().equals("Priority (Non-Preemptive)")) {

            priority = Integer.parseInt(priorityQuantum_textField.getText());
            Process p = new Process(processName, arrivalTime, burstTime, priority);
            currentTableData.add(p);
            if (isRunning) {
                simulator.addProcess(p);
            }
        } else {
            Process p = new Process(processName, arrivalTime, burstTime);
            currentTableData.add(p);
            if (isRunning) {
                simulator.addProcess(p);
            }
        }
        
        ProcessName_textField.clear();
        ArrivalTime_textField.clear();
        BurstTime_textField.clear();
        quantumTime_textField.clear();
    }

    public void startSimulation(ActionEvent actionEvent) {
        scheduler = getScheduler();
        chart = new GanttChart();
        simulator = new Simulator(currentTableData, scheduler, chart);
        ObservableList<GanttEntry> entries = chart.getEntries();
        isRunning = true;

        entries.addListener(new ListChangeListener<GanttEntry>() {
            @Override
            public void onChanged(Change<? extends GanttEntry> change) {
                // Call ProccessHandler whenever the list changes
                ProccessHandler();
            }
        });

        // Ensure a new task is created each time the button is clicked
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                simulator.runLive();   // Run the simulation in the background
                getResults(chart);     // Update the results (presumably updates the chart)
                if (simulator.allProcessesTerminated()) {
                    // Display an alert window
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Simulation Complete");
                        alert.setHeaderText(null);
                        alert.setContentText("All processes have terminated successfully.");
                        alert.showAndWait();
                    });
                    System.out.println("All processes have terminated.");
                }
                return null;
            }
        };

        if (!liveSimulation_btn.isSelected()){
            simulator.runStatic();
            getResults(chart);
            
        }
        else
            new Thread(task).start();
           if (simulator.allProcessesTerminated()) {
    // Display an alert window
    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Simulation Complete");
        alert.setHeaderText(null);
        alert.setContentText("All processes have terminated successfully.");
        alert.showAndWait();
    });
    System.out.println("All processes have terminated.");
}


    }


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

    private Scheduler getScheduler(){
        switch (SchedulingMethod_choiceList.getValue()){
            case "FCFS":
                scheduler = new FCFS();
                break;
            case "SJF (Preemptive)":
                scheduler = new SJF(true);
                break;
            case "SJF (Non-Preemptive)":
                scheduler = new SJF(false);
                break;
            case "Priority (Preemptive)":
                scheduler = new Priority(true);
                break;
            case "Priority (Non-Preemptive)":
                scheduler = new Priority(false);
                break;
            case "Round Robin":
                scheduler = new RR(rr);
                break;
        }
        return scheduler;
    }

    private void getResults(GanttChart gaunt){
        Results results = new Results(gaunt);
        Average_Waiting_Time_textField.setText(Double.toString(results.getAverageWaitingTime()));
        Average_Turnaround_Time_textField.setText(Double.toString(results.getAverageTurnaroundTime()));
    }

    private void shownewproccess(int i) {
        String taskname = "IDLE ";
        //  System.out.println("Process ID: " + i);
        if (chart.getEntries().get(i - 1).getProcess() != null) {
            taskname = chart.getEntries().get(i - 1).getProcess().getName();
        }

        // Create rectangle with text
        Text centerText = new Text();
        centerText.setText(taskname.length() > 5 ? taskname.substring(0, 5) + "..." : taskname);
        centerText.setFill(Color.BLACK);
        centerText.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        centerText.setWrappingWidth(50 - 10);
        Rectangle rectangle = new Rectangle(50, 70);
        if (!ProcessColors.containsKey(chart.getEntries().get(i - 1).getProcess().getPid()))
        {
            rectangle.setFill(lightColors.get(idx % lightColors.size()));
            ProcessColors.put(chart.getEntries().get(i - 1).getProcess().getPid(), lightColors.get(idx % lightColors.size()));
            idx++;
        }
        else
        {
            rectangle.setFill(ProcessColors.get(chart.getEntries().get(i - 1).getProcess().getPid()));
        }
        rectangle.setStroke(Color.BLACK);
        StackPane stackPane = new StackPane(rectangle, centerText);
        rectanglesBox.getChildren().add(stackPane);
        Tooltip tooltip = new Tooltip(taskname);
        Tooltip.install(stackPane, tooltip);

        // Create number with alignment
        Text numberText = new Text(String.valueOf(i));
        numberText.setStyle("-fx-font-size: 12px;");

        StackPane numberContainer = new StackPane(numberText);
        numberContainer.setMinWidth(50+1);
        numberContainer.setAlignment(Pos.BOTTOM_RIGHT);

        timelineBox.getChildren().add(numberContainer);
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        Stage mainStage = (Stage) goBack_btn.getScene().getWindow();
        mainStage.centerOnScreen();
        mainStage.setScene(scene);
    }

    public void reset(MouseEvent mouseEvent) {
        table.getItems().clear();
        rectanglesBox.getChildren().clear();
        timelineBox.getChildren().clear();
        Average_Waiting_Time_textField.clear();
        Average_Turnaround_Time_textField.clear();
        isRunning = false;
        priorityQuantum_textField.setEditable(true);  
        }
}

