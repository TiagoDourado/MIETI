import java.io.BufferedReader;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.lang.Runnable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


import com.sun.org.apache.xpath.internal.SourceTree;

import javafx.scene.input.DataFormat;

public class ClientSender implements Runnable{
    
    Cache cache;
    CreateAlert newAlert;
    String s;

    public ClientSender(Cache cache, CreateAlert newAlert,String s){
        this.cache = cache;
        this.newAlert = newAlert;
        this.s = s;
    
    }

    public static List<InetAddress> obtainValidAddresses(InetAddress group) {
        List<InetAddress> result = new ArrayList<InetAddress>();
  
  
        System.out.println("\nObtain valid addresses according to group address");
        //verify if group is a multicast address
        if (group == null || !group.isMulticastAddress()) return result;
        try {
        //obtain the network interfaces list
           Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
           while (ifs.hasMoreElements()) {
              NetworkInterface ni = ifs.nextElement();
            //ignoring loopback, inactive interfaces and the interfaces that do not support multicast
              if (ni.isLoopback() || !ni.isUp() || !ni.supportsMulticast()) {
                 System.out.println("Ignoring Interface: " + ni.getDisplayName());
                 continue;                     
              }
              Enumeration<InetAddress> addrs = ni.getInetAddresses();
              while (addrs.hasMoreElements()) {
                 InetAddress addr = addrs.nextElement();
                 //including addresses of the same type of group address
                 if (group.getClass() != addr.getClass()) continue;
                 if ((group.isMCLinkLocal() && addr.isLinkLocalAddress())
                    || (!group.isMCLinkLocal() && !addr.isLinkLocalAddress())) {
                    System.out.println("Interface: " + ni.getDisplayName() + " Adress: " +addr);
                    result.add(addr);
                 } else {
                    //System.out.println("Ignoring addr: " + addr + " of interface " + ni.getDisplayName());
                 }
              }
           }
        } catch (SocketException ex) {
            System.out.println("Error: " + ex);
        }
        return result;
    }


  

    //Lançamento da Thread para enviar
    public void run(){
      String pacote = null;
      try{
         if(newAlert == null){
            pacote = s;
         }
         else{
            pacote = newAlert.newAlert();
         }
      }catch(IOException e){
         System.out.println("Error: " +e);
      }
      if(pacote !=  null){
         send(pacote);
      }
      
   }
   
   synchronized void send(String pacote){     
      
      try{
            
            InetAddress group = InetAddress.getByName(ReadConfigFile.MULTICAST_ADDRESS);
            String msg = pacote.toString() ; /**TODO associar msg ao alerta a enviar*/
            System.out.println("\nA enviar alerta por multicast" + msg);
            MulticastSocket ms = new MulticastSocket();
            DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.length(), group, ReadConfigFile.MULTICAST_PORT);

            List<InetAddress> addrs = obtainValidAddresses(group);
            for(InetAddress addr: addrs){
                System.out.println("A enviar " + addr);
                ms.setInterface(addr);
                ms.send(dp);
            }
        ms.close();
    

    }catch(UnknownHostException e){
        System.out.println("Error "+e);
     }catch(IOException e){
        System.out.println("Error "+e);
     }catch(Exception e){
        System.out.println("Error1: "+e);
     }
    
    
    }

  

}