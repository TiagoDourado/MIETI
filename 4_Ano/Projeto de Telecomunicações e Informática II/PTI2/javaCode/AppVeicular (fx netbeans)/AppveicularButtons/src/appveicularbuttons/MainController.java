/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appveicularbuttons;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class MainController implements Initializable {

    @FXML
    private TabPane tabpane;
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private CheckBox cb1;
    @FXML
    private CheckBox cb2;
    @FXML
    private CheckBox cb3;
    @FXML
    private Button met;
    @FXML
    private Button aci;
    @FXML
    private Button amb;
    @FXML
    private Button tra;
    @FXML
    private Button obr;
    
int selectedmet=0;
int selectedaci=0;
int selectedamb=0;
int selectedtra=0;
int selectedobr=0;
    @FXML
    private ScrollPane SPviewalert;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tabpane.tabMinWidthProperty().bind(root.widthProperty().divide(tabpane.getTabs().size()).subtract(20));
        File fi1 = new File("/home/andre/Documentos/Code/pti2/pti2-1819/javaCode/AppveicularButtons/AppveicularButtons/src/appveicularbuttons/imgs/1.jpg");
        File fi2 = new File ("/home/andre/Documentos/Code/pti2/pti2-1819/javaCode/AppveicularButtons/AppveicularButtons/src/appveicularbuttons/imgs/2.jpg");
        File fi3 = new File ("/home/andre/Documentos/Code/pti2/pti2-1819/javaCode/AppveicularButtons/AppveicularButtons/src/appveicularbuttons/imgs/3.jpg");
        Image im1 = new Image(fi1.toURI().toString());
        Image im2 = new Image(fi2.toURI().toString());
        Image im3 = new Image(fi3.toURI().toString());
        img1.setImage(im1);
        img2.setImage(im2);
        img3.setImage(im3);
        cb1.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(cb1.isSelected()){
                    cb2.setSelected(false);
                    cb3.setSelected(false);

                }
            }
        });
    
    
        cb2.selectedProperty().addListener(new ChangeListener<Boolean>(){
          @Override
          public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
              if(cb2.isSelected()){
                  cb1.setSelected(false);
                  cb3.setSelected(false);
                  

              }
          }
      });

        cb3.selectedProperty().addListener(new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(cb3.isSelected()){
                cb1.setSelected(false);
                cb2.setSelected(false);

            }
        }
    });
         
         
        DropShadow shadow = new DropShadow();
        //Adding the shadow when the mouse cursor is on
        met.setOnAction(new EventHandler<ActionEvent>() {
        @Override 
        public void handle(ActionEvent e) {
                       
                if(selectedmet == 0 ){
                    met.setEffect(shadow);
                    aci.setEffect(null);
                    amb.setEffect(null);
                    obr.setEffect(null);
                    tra.setEffect(null);
                    cb1.setDisable(true);
                    cb2.setDisable(true);
                    cb3.setDisable(true);    
                    selectedmet =1;
                    selectedaci =0;
                    selectedtra =0;
                    selectedobr =0;
                    selectedamb =0;

                }
                else{
                    met.setEffect(null);
                    selectedmet=0;
                }
            }
        });
        
        aci.setOnAction(new EventHandler<ActionEvent>() {
        @Override 
        public void handle(ActionEvent e) {
                cb1.setDisable(false);
                cb2.setDisable(false);
                cb3.setDisable(false);  
                if(selectedaci == 0 ){
                    met.setEffect(null);
                    aci.setEffect(shadow);
                    amb.setEffect(null);
                    obr.setEffect(null);     
                    tra.setEffect(null);
                    selectedaci =1;
                    selectedmet =0;
                    selectedtra =0;
                    selectedobr =0;
                    selectedamb =0;
                }
                else{
                    aci.setEffect(null);
                    selectedaci=0;
                }
            }
        });
         amb.setOnAction(new EventHandler<ActionEvent>() {
        @Override 
        public void handle(ActionEvent e) {
                  cb1.setDisable(false);
                cb2.setDisable(false);
                cb3.setDisable(false);        
                if(selectedamb == 0 ){
                    amb.setEffect(shadow);
                    met.setEffect(null);
                    aci.setEffect(null);
                    obr.setEffect(null);     
                    tra.setEffect(null);
                    selectedamb =1;
                    selectedmet =0;
                    selectedaci =0;
                    selectedtra =0;
                    selectedobr =0;
                }
                else{
                    amb.setEffect(null);
                    selectedamb=0;
                }
            }
        });
          
          
        tra.setOnAction(new EventHandler<ActionEvent>() {
        @Override 
        public void handle(ActionEvent e) {
                  cb1.setDisable(false);
                cb2.setDisable(false);
                cb3.setDisable(false);        
                if(selectedtra == 0 ){
                    tra.setEffect(shadow);
                    met.setEffect(null);
                    aci.setEffect(null);
                    amb.setEffect(null);
                    obr.setEffect(null);     
                    selectedtra =1;
                    selectedmet =0;
                    selectedaci =0;
                    selectedobr =0;
                    selectedamb =0;
                }
                else{
                    tra.setEffect(null);
                    selectedtra=0;
                }
            }
        });
        obr.setOnAction(new EventHandler<ActionEvent>() {
        @Override 
        public void handle(ActionEvent e) {
                  cb1.setDisable(false);
                cb2.setDisable(false);
                cb3.setDisable(false);        
                if(selectedobr == 0 ){
                    obr.setEffect(shadow);
                    met.setEffect(null);
                    aci.setEffect(null);
                    amb.setEffect(null);
                    tra.setEffect(null);
                    selectedobr =1;
                    selectedmet =0;
                    selectedaci =0;
                    selectedtra =0;
                    selectedamb =0;
                }
                else{
                    obr.setEffect(null);
                    selectedobr=0;
                }
            }
        });
    }   
    
@FXML
    private void updateSP(Event event) {
        
    }
    
    @FXML
    private void addbutton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensagem de Confirmação");
        alert.setHeaderText("Atenção!");
        alert.setContentText("Tem a certeza que pretende adicionar o alerta?");
        ButtonType buttonTypeOne = new ButtonType("Sim");
        ButtonType buttonTypeTwo = new ButtonType("Não");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
        Effect effectmet = met.getEffect();
        Effect effectamb = amb.getEffect();
        Effect effectobr = obr.getEffect();
        Effect effecttra = tra.getEffect();
        Effect effectaci = aci.getEffect();
        
        if (effectmet instanceof DropShadow==true ){
           System.out.println("met todos");
           String tipo = "Meteorologia";
           int meteohops = 5;
           int ttl = 30;
           int area = 4;

        }
        else{
        if (effectamb instanceof DropShadow==true && cb1.isSelected()){
            System.out.println("amb 1");
            String tipo = "Ambulancia";
            int meteohops = 10;
            int ttl = 15;
            int area = 1;
        }else{
        if (effectamb instanceof DropShadow==true && cb2.isSelected()){
            System.out.println("amb 2");
            String tipo = "Ambulancia";
            int meteohops = 10;
            int ttl = 15;
            int area = 2;
        }else{
        if (effectamb instanceof DropShadow==true && cb3.isSelected()){
            System.out.println("amb 3");
            String tipo = "Ambulancia";
            int meteohops = 10;
            int ttl = 15;
            int area = 3;
        }else{
        
        if (effectobr instanceof DropShadow==true && cb1.isSelected()){
            System.out.println("obr 1");
            String tipo = "Obras";
            int meteohops = 10;
            int ttl = 30;
            int area = 1;
        }else{
        if (effectobr instanceof DropShadow==true && cb2.isSelected()){
            System.out.println("obr 2");
            String tipo = "Obras";
            int meteohops = 10;
            int ttl = 30;
            int area = 2;
        }else{
        if (effectobr instanceof DropShadow==true && cb3.isSelected()){
            System.out.println("obr 3");
            String tipo = "Obras";
            int meteohops = 10;
            int ttl = 30;
            int area = 3;
        }else{
        
        if (effecttra instanceof DropShadow==true && cb1.isSelected()){
            System.out.println("tra 1");
            String tipo = "Transito";
            int meteohops = 7;
            int ttl = 20;
            int area = 1;
        }else{
        if (effecttra instanceof DropShadow==true && cb2.isSelected()){
            System.out.println("tra 2");
            String tipo = "Transito";
            int meteohops = 7;
            int ttl = 20;
            int area = 2;
        }else{
        if (effecttra instanceof DropShadow==true && cb3.isSelected()){
            System.out.println("tra 3");
            String tipo = "Transito";
            int meteohops = 7;
            int ttl = 20;
            int area = 3;
        }else{
        if (effectaci instanceof DropShadow==true && cb1.isSelected()){
            System.out.println("aci 1");
            String tipo = "Acidente";
            int meteohops = 50;
            int ttl = 60;
            int area = 1;
        }else{
        if (effectaci instanceof DropShadow==true && cb2.isSelected()){
            System.out.println("aci 2");
            String tipo = "Acidente";
            int meteohops = 50;
            int ttl = 60;
            int area = 2;
        }else{
        if (effectaci instanceof DropShadow==true && cb3.isSelected()){
            System.out.println("aci 3");
            String tipo = "Acidente";
            int meteohops = 50;
            int ttl = 60;
            int area = 3;
        }  else{      
           System.out.println("nao");   
            Alert c = new Alert(Alert.AlertType.INFORMATION);
            c.setTitle("Mensagem de Erro");
            c.setHeaderText("Atenção preencha as opções todas!");
            ButtonType ok = new ButtonType("ok");
            c.getButtonTypes().setAll(ok);
            Optional<ButtonType> results = c.showAndWait();

        }}}}}}}}}}}}
        
        

        


        }
            // ... user chose "One"
        } else{
        if (result.get() == buttonTypeTwo) {
            // ... user chose "Two"
        }
        }


 
 
    }
}



