
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.print.DocFlavor.STRING;

import java.net.MulticastSocket;
import java.lang.Runnable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.net.*;
import java.util.*;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Control_time implements Runnable{
    private String ttl;
    private String TimeStamp;
    private int identifier;
    LocalDB localDB;
    ArrayList<Integer> alertas_cancelados = new ArrayList<>();
    PrintWriter out;

    public ArrayList<Integer> getCancelados(){
        return this.alertas_cancelados;
    }

    Control_time(String ttl, String TimeStamp, int id, LocalDB db, PrintWriter out){
        this.ttl = ttl;
        this.TimeStamp = TimeStamp;
        this.identifier = id;
        this.localDB = db;
        this.out = out;
    }

    public void run(){
        //String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        //TimeStamp atual = time.valueOf(time);
        //Timestamp timestamp = Timestamp.valueOf(time);

        int ttl_int = Integer.parseInt(ttl);
        int mili_sec = ttl_int*1000; 
        try{
            Thread.sleep(mili_sec);
            alertas_cancelados.add(identifier);
            System.out.println("Identificador é:"+identifier);
            System.out.println("Vou eliminar o alerta");
            localDB.removeAlert(identifier,out);
            
        }catch (Exception e){}
    }



	public static ArrayList<Integer> getAlertas() {
		return null;
	}
}