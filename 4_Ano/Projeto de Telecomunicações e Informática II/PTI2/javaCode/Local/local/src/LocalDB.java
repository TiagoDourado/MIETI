import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.MulticastSocket;
import java.lang.Runnable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.*;
import java.util.*;
import java.util.HashMap;

public class LocalDB{
    private HashMap<String,Integer> tipos_de_alerta = new HashMap<>();
    public ArrayList<String> alertas = new ArrayList<>();

    public void LocalDB(){
        tipos_de_alerta.put("Meteorologia",1);
        tipos_de_alerta.put("Obras",2);
        tipos_de_alerta.put("Ambulancia",3);
        tipos_de_alerta.put("Acidente",4);
        tipos_de_alerta.put("Transito",5);
    }

    public ArrayList<String> getAlerts(){
        return this.alertas;
    }

    public String getAlert(int index_alert){
        System.out.println("Obter Alert %d" + index_alert);
        String resposta = null;

        for(String s : alertas){
            String parts[] = s.split(",");
            String index = parts[0];
            int valor_index = Integer.parseInt(index);

            if(valor_index==index_alert){
                return s;
            }
        }
        return null;
    }

    //alert(id_alert,dateAlert,estado,ip_origem,ip_rsu,id_alertType) VALUES('" + id_alert+ "','" + timestamp + "','" + state + "','" + ip_origem + "','" + ip_rsu + "','" + id_tipo + "')"
    public String insertAlert (int id_alert, String dt,int state, String ip_origem, String ip_rsu, int id_tipo){
        System.out.println("Insert Alert");
        System.out.println("Array de alertas tem:"+alertas);
        String res = null;

        //Timestamp timestamp = Timestamp.valueOf(dt);
        int index = alertas.size() + 1;
        String index_alert = Integer.toString(index);
        alertas.add(index_alert + "," + id_alert+ "," + dt + "," + state + "," + ip_origem + "," + ip_rsu + "," + id_tipo);

        res = "New alert add";
        return res;
    }

    public String updateAlert (int index_alert,int id_alert,String dt, int state, String ip_origem, String ip_rsu, int id_tipo) {
        System.out.println("Update Alert");
        String res = null;

        for(int i=0;i<alertas.size();i++){
            String s = alertas.get(i);
            String parts[] = s.split(",");
            String index = parts[0];
            int valor_index = Integer.parseInt(index);

            if(valor_index==index_alert){
                alertas.set(i,index_alert + "," + id_alert+ "," + dt + "," + state + "," + ip_origem + "," + ip_rsu + "," + id_tipo);
                res = "Alerta atualizado com sucesso.";
                return res;
            }
        }
        return null;
    }

    public String removeAlert(int index_alert,PrintWriter out){
        System.out.println("Remover alerta");
        System.out.println("Array de alertas:"+alertas);
        String res = null;
        System.out.println("O tamnanho do arraylist é:"+alertas.size());
        for(int i=0;i<alertas.size();i++){
            String s = alertas.get(i);
            System.out.println("O valor da string é:"+s);
            String parts[] = s.split(",");
            String index = parts[0];
            int valor_index = Integer.parseInt(index);
            System.out.println("valor index é:"+valor_index);

            if(valor_index==index_alert-1){
                alertas.remove(i);
                res = "Alerta removido com sucesso.";
                System.out.println("Vou enviar o indes:"+index_alert);
                out.println(index_alert);
                return res;
            }
        }
        return null;
    }
}