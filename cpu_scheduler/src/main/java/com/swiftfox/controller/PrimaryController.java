package com.swiftfox.controller;

import java.io.IOException;

import com.swiftfox.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
/**
 * Controller class for the main screen of the SwiftFox CPU Scheduler Simulator.
 * <p>
 * This class is responsible for handling initial user interactions on the welcome screen.
 * It manages UI components such as the launch button, logo image, and tagline,
 * and transitions the user to the main simulation interface.
 * </p>
 *
 * <p>
 * When the "Launch" button is pressed, this controller loads the primary simulation view
 * defined in {@code updated_UI.fxml}, creates a new scene, and sets it on the current stage.
 * </p>
 *
 * <p>
 * This class is linked to an FXML layout and is part of a JavaFX application.
 * </p>
 * @author SwiftFox Team
 * @version 1.0
 * @see javafx.fxml.FXML
 * @see javafx.stage.Stage
 * @see javafx.scene.Scene
 * @see FXMLLoader
 */
public class PrimaryController {
//
//    @FXML
//    private void switchToSecondary() throws IOException {
//        App.setRoot("secondary");
//    }



    @FXML
    private Button launchBtn;

    @FXML
    private ImageView logoImage;

    @FXML
    private Label tagline;
   
    @FXML
    void press(ActionEvent event)throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("updated_UI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1366,768);
        Stage mainStage = (Stage) logoImage.getScene().getWindow();
        mainStage.setScene(scene);
        mainStage.centerOnScreen();
        mainStage.setFullScreen(false);

    }
    
}