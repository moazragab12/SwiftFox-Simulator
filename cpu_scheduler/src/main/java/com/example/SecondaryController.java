package com.example;

import java.io.IOException;

import com.example.schedulers.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.example.schedulers.Main.chart;

public class SecondaryController {

    private HBox rectanglesBox = new HBox();
    private HBox timelineBox = new HBox();
    private int currentProcess = 1; // Tracks the process being added
    @FXML
    private VBox ContainerOfGanttChart;
    @FXML
    private CheckBox LivecheckBox;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    @FXML
    public void initialize() {

        Main.main(new String[]{}); // Call the main method of Main class to initialize the scheduler
    }

    public void StartGantt() {
        Stage primaryStage = new Stage();
        timelineBox.setAlignment(Pos.BOTTOM_LEFT);

        ContainerOfGanttChart.getChildren().addAll(rectanglesBox, timelineBox);


        // Set the size of the rectanglesBox and timelineBox


        // Start adding processes one by one
        addProcessesWithDelay();
    }

    private void addProcessesWithDelay() {
    int TasksSize = chart.getEntries().size();
        if (!LivecheckBox.isSelected()) {
            for (int i = 1; i <= TasksSize; i++) {
              addProcess(i);
            }
        } else {
            Timeline timeline = new Timeline();
            for (int i = 1; i <= TasksSize; i++) {
                int processNum = i; // Capture the current process number
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(i), e -> addProcess(processNum)));
            }
            timeline.play();
        }


    }

    private void addProcess(int i) {
        String taskname = chart.getEntries().get(i - 1).getProcess().getName() +" "+ i;
        double rectWidth = 10 * taskname.length();

        // Create rectangle with text
        Text centerText = new Text(taskname);
        centerText.setFill(Color.BLACK);
        centerText.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Rectangle rectangle = new Rectangle(rectWidth, 80);
        rectangle.setFill(Color.LIGHTGRAY);
        rectangle.setStroke(Color.BLACK);

        StackPane stackPane = new StackPane(rectangle, centerText);
        rectanglesBox.getChildren().add(stackPane);

        // Create number with alignment
        Text numberText = new Text(String.valueOf(i));
        numberText.setStyle("-fx-font-size: 12px;");

        StackPane numberContainer = new StackPane(numberText);
        numberContainer.setMinWidth(rectWidth + 1);
        numberContainer.setAlignment(Pos.BOTTOM_RIGHT);

        timelineBox.getChildren().add(numberContainer);
    }

    @FXML
    public void SimulationStart() {
        // This method will be called when the "Start Simulation" button is clicked
        // You can add your simulation logic here
        System.out.println("Simulation started!");
        StartGantt();

    }

}