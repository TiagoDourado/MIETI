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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class LoginController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private AnchorPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void botaologin(ActionEvent event) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        
        ArrayList<String> User = new ArrayList<>();
        String[] trama = new String[3] ;

        trama[0]= "Login";
        trama[1]= username.getText();
        String pass =password.getText();
        String generatedSecuredPasswordHash = SCryptUtil.scrypt(pass, 16, 16, 16);
        //System.out.println(generatedSecuredPasswordHash);
        trama[2]= pass;
        
        for (int i=0;i<3;i++) {
            User.add(trama[i]);
        }  
      
        Client cli = new Client();
        String res = cli.criarcli(User);
        System.out.println(res);
        if (res.equals("true")){
            AnchorPane pane= FXMLLoader.load(getClass().getResource("addalert.fxml"));
            root.getChildren().setAll(pane);
            User.clear();
        }
        else{
            Alert c = new Alert(Alert.AlertType.INFORMATION);
            c.setTitle("Mensagem de Erro");
            c.setHeaderText("Atenção! Username ou password incorretos!");
            ButtonType ok = new ButtonType("ok");
            c.getButtonTypes().setAll(ok);
            Optional<ButtonType> results = c.showAndWait();

        }
    }
}
    

