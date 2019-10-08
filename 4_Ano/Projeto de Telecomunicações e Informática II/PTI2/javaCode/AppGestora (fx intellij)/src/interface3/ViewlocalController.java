/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interface3;

import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author pedro
 */
public class ViewlocalController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField id_local;
    @FXML
    private TextArea respostaviewlocal;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loadadduser(ActionEvent event) throws IOException {
         AnchorPane pane= FXMLLoader.load(getClass().getResource("adduser.fxml"));
        root.getChildren().setAll(pane);
    }
    @FXML
    private void loadaddalerta(ActionEvent event) throws IOException {
         AnchorPane pane= FXMLLoader.load(getClass().getResource("addalert.fxml"));
        root.getChildren().setAll(pane);
    }
    @FXML
    private void loadedituser(ActionEvent event) throws IOException {
         AnchorPane pane= FXMLLoader.load(getClass().getResource("edituser.fxml"));
        root.getChildren().setAll(pane);
    }


    @FXML
    private void loadeditalert(ActionEvent event) throws IOException {
         AnchorPane pane= FXMLLoader.load(getClass().getResource("editalerta.fxml"));
        root.getChildren().setAll(pane);
    }
    
    @FXML
    private void loadrmalert(ActionEvent event) throws IOException {
         AnchorPane pane= FXMLLoader.load(getClass().getResource("rmalert.fxml"));
        root.getChildren().setAll(pane);
    }

    @FXML
    private void loadrmuser(ActionEvent event) throws IOException {
         AnchorPane pane= FXMLLoader.load(getClass().getResource("rmuser.fxml"));
        root.getChildren().setAll(pane);
    }
    @FXML
    private void loadviewlocal(ActionEvent event) throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("viewlocal.fxml"));
        root.getChildren().setAll(pane);
    }
    
    @FXML
    private void loadviewalert(ActionEvent event) throws IOException {
         AnchorPane pane= FXMLLoader.load(getClass().getResource("viewalert.fxml"));
        root.getChildren().setAll(pane);
    }
    @FXML
    private void loadviewuser(ActionEvent event) throws IOException {
         AnchorPane pane= FXMLLoader.load(getClass().getResource("viewuser.fxml"));
        root.getChildren().setAll(pane);
    }

    @FXML
    private void loadviewsusers(ActionEvent event) throws IOException {
         AnchorPane pane= FXMLLoader.load(getClass().getResource("viewalluser.fxml"));
        root.getChildren().setAll(pane);
    }
    @FXML
    private void loadviewslocals(ActionEvent event) throws IOException {
         AnchorPane pane= FXMLLoader.load(getClass().getResource("viewalllocal.fxml"));
        root.getChildren().setAll(pane);
    }
    @FXML
    private void loadviewsalert(ActionEvent event) throws IOException {
         AnchorPane pane= FXMLLoader.load(getClass().getResource("viewallalerts.fxml"));
        root.getChildren().setAll(pane);
    }
    
    @FXML
    private void sendViewLocal(ActionEvent event) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        String s = "Dados:\n";
         ArrayList<String> User = new ArrayList<>();
         String[] trama = new String[2] ;
                
                trama[0]= "viewLocal";
                trama[1]=id_local.getText();
                for (int i=0;i<2;i++) {
                    User.add(trama[i]);
                }  
                


            Client cli = new Client();
            String res = cli.criarcli(User);
            User.clear();  
            String[] array= res.split(",");
            for (String element : array) {
                s = s+"\n"+element;
                
                
            }
           respostaviewlocal.setText(s);
            // ... user chose "One"
       
        }
    private void loadalerttype(ActionEvent event) throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("AddalerttypeController.fxml"));
        root.getChildren().setAll(pane);
    }

    @FXML
    private void logoutbt(ActionEvent event) throws IOException {
        Alert logoutalt = new Alert(Alert.AlertType.CONFIRMATION);
        logoutalt.setTitle("Mensagem de Confirmação");
        logoutalt.setHeaderText("Atenção!");
        logoutalt.setContentText("Tem a certeza que pretende adicionar?");

        ButtonType losim = new ButtonType("Sim");
        ButtonType lonao = new ButtonType("Não");

        logoutalt.getButtonTypes().setAll(losim, lonao);

        Optional<ButtonType> resultados = logoutalt.showAndWait();
        if (resultados.get() == losim){
           
        AnchorPane pane= FXMLLoader.load(getClass().getResource("Login.fxml"));
        root.getChildren().setAll(pane);
            
            // ... user chose "One"
        } else if (resultados.get() == lonao) {
            // ... user chose "Two"
        }
    }
    }
    

