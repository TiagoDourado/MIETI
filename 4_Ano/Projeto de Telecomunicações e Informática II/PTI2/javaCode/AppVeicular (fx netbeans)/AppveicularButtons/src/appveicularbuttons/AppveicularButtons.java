/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appveicularbuttons;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author andre
 */
public class AppveicularButtons extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(root);
        //scene.setFill(Color.TRANSPARENT);
        /*root.setOnMousePressed (new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                x = event.getSceneX();
                y = event.getSceneY();
            }
        });
        root.setOnMouseDragged (new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                stage.setX(event.getScreenX()-x);
                stage.setY(event.getScreenY()-y);
            }
        });*/
        stage.setScene(scene);  
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
