/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interface3;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 *
 * @author andre
 */
public class Interface3 extends Application {
    private double x, y ;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
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
