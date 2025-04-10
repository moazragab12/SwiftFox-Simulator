package com.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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

     
   
    void press(ActionEvent event)throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("updated_UI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage mainStage = (Stage) logoImage.getScene().getWindow();
        mainStage.setScene(scene);
        mainStage.setFullScreen(true);
    }
    
}