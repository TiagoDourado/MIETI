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
/**
 *
 * @author joao
 */


public class exMcastEnvia implements Runnable{  
   private static final boolean send = true;
   private static int idx = 0;
   //public static final String MULTICAST_ADDRESS = "FF15::1:2:3";
   public static final String MULTICAST_ADDRESS = "FF02::1";
   public static final int MULTICAST_PORT = 9999;
   cache_car cache;

   public exMcastEnvia(cache_car cachex){
      this.cache = cachex;
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
                  System.out.println("Ignoring addr: " + addr + " of interface " + ni.getDisplayName());
               }
            }
         }
      } catch (SocketException ex) {
          System.out.println("Error: " + ex);
      }
      return result;
   }

   public static ArrayList<String> new_alerta() throws IOException{
      ArrayList<String> list = new ArrayList<>();
      ArrayList<String> type_alert = new ArrayList<>();
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      BufferedReader msg = new BufferedReader(new InputStreamReader(System.in));
      int op=0;
      cache_car cache = cache_car.getInstance();
      System.out.println("Type of alert");
      String input = reader.readLine();
      
      type_alert.add("addMeteorologia");
      type_alert.add("addAmbulancia");
      type_alert.add("addAcidente");
      type_alert.add("addObras");

      int pos=0;
      for(String s : type_alert){
         pos++;
         
         if(input.equals(s)){
            idx++;
            if(input.equals("addMeteorologia")){     //prioridade 1
               String ttl = "24";
               int timelive = Integer.parseInt(ttl);
               MulticastSocket ms2 = new MulticastSocket();
               ms2.setTimeToLive(timelive);
               String hops = "5";
               System.out.println("Write a new alert message:");
               String new_alerta = msg.readLine();
      
               Calendar calendar = Calendar.getInstance();
               DateFormat dateFormat = new SimpleDateFormat("HH:mm");
               Date date = new Date();
               String data = dateFormat.format(calendar.getTime());
               
               list.add("alerta " + ",");
               list.add(idx +  ",");
               
               cache.add_sequence_number(idx);
               list.add(ttl + ",");
               list.add(hops + ",");
               list.add(new_alerta + ",");
               list.add(data);
               list.add("1"); //prioridade
               
               //Packet packet = new packet();
            }
            if(input.equals("addAmbulancia")){  //prioridade 4
               String ttl = "15";
               int timelive = Integer.parseInt(ttl);
               MulticastSocket ms2 = new MulticastSocket();
               ms2.setTimeToLive(timelive);
            
               String hops = "10";
               System.out.println("Write a new alert message:");
               String new_alerta = msg.readLine();
      
               Calendar calendar = Calendar.getInstance();
               DateFormat dateFormat = new SimpleDateFormat("HH:mm");
               Date date = new Date();
               String data = dateFormat.format(calendar.getTime());
              
               list.add("alerta" + ",");
               list.add(idx + ",");
               cache.add_sequence_number(idx);
               list.add(ttl + ",");
               list.add(hops + ",");
               list.add(new_alerta + ",");
               list.add(data);
               list.add("4"); //prioridade
               
            }
            if(input.equals("addAcidente")){   //prioridade 3
               String ttl = "60"; // uma hora
               int timelive = Integer.parseInt(ttl);
               MulticastSocket ms2 = new MulticastSocket();
               ms2.setTimeToLive(timelive);
               String hops = "50";
               System.out.println("Write a new alert message:");
               String new_alerta = msg.readLine();
               
               Calendar calendar = Calendar.getInstance();
               DateFormat dateFormat = new SimpleDateFormat("HH:mm");
               Date date = new Date();
               String data = dateFormat.format(calendar.getTime());
              
               list.add("alerta" + ",");
               list.add(idx + ",");
               cache.add_sequence_number(idx);
               list.add(ttl + ",");
               list.add(hops + ",");
               list.add(new_alerta + ",");
               list.add(data);
               list.add("3"); //prioridade
               
            }
            if(input.equals("addObras")){   //prioridade 2
               String ttl = "70"; // corresponde a 12 horas
               int timelive = Integer.parseInt(ttl);
               MulticastSocket ms2 = new MulticastSocket();
               ms2.setTimeToLive(timelive);
               String hops = "10";
               System.out.println("Write a new alert message:");
               String new_alerta = msg.readLine();
      
               Calendar calendar = Calendar.getInstance();
               DateFormat dateFormat = new SimpleDateFormat("HH:mm");
               Date date = new Date();
               String data = dateFormat.format(calendar.getTime());
            
               list.add("alerta " + ",");
               list.add(idx + ",");
               cache.add_sequence_number(idx);
               list.add(ttl+",");
               list.add(hops+",");
               list.add(new_alerta+",");
               list.add(data);
               list.add("2"); //prioridade
               
            }
         }
         if(pos==type_alert.size() && !input.equals(s)){
            System.out.println("Type of alert not available");
         }
      }
      return list;
   }

   public static int verify_payload(ArrayList<String> alerta, cache_car cache){
      Map<String,String> payloads = new HashMap<String,String>();
      payloads = cache.get_ID_to_Payload_all();
      int pos=0;

      if(payloads.size()==0){
         return 1;
      }

      for(String key : payloads.keySet()){
         System.out.println("Valor da key: "+key);
         pos++;
         String msg = alerta.get(4);   //mensagem do alerta

         System.out.println("Valor do alerta: "+msg);

         String[] parts = (payloads.get(key)).split(",");
         String msg_alert = parts[4]+",";

         System.out.println("Valor do payload: "+msg_alert);

         if(!msg.equals(msg_alert) && pos==payloads.size()){
            return 1;
         }
         if(msg.equals(msg_alert)){
            return 0;
         }
      }
      return 2;
}

public static ArrayList<Integer> verify_priority(ArrayList<String> alert, cache_car cache){
   Map<String,String> prioridade = new HashMap<String,String>();
   prioridade = cache.getPrioridade();
   int value_prioridade = Integer.parseInt(alert.get(6));
   int pos=0;
   ArrayList<Integer> posicoes = new ArrayList<Integer>(); //posições do HashMap dos alertas com prioridade superior

   for(String key : prioridade.keySet()){
      
      int int_key = Integer.parseInt(key);  //valor da prioridade no hashMap, o valor da prioridade é key
      int size_prioridade = prioridade.size();

      if(Integer.parseInt(alert.get(6))<int_key){
         posicoes.add(pos);
      }
      pos++;
   }
   return posicoes;
} 
   
   public void run(){
      HashMap<String,String> prioridade = new HashMap<String,String>();
      Map<String,String> payloads = new HashMap<String,String>();
      ArrayList<Integer> posicoes = new ArrayList<Integer>();
      

      cache_car cache = cache_car.getInstance();
    

      //String ip = args[2];  //ip destino
      try{
         while(true){
            ArrayList<String> alerta =new ArrayList<>();
            payloads = cache.get_ID_to_Payload_all();
            String msg = " ";
            alerta = new_alerta();
            posicoes = verify_priority(alerta,cache);

            if(!posicoes.isEmpty()){
               Collection<String> values = payloads.values();
               ArrayList<String> conteudo_payload = new ArrayList<String>(values);  //possui os values do HashMap de payloads
               //conteudo_payload = getValues(payloads);
               for(int n : posicoes){
                  msg = conteudo_payload.get(n);
                  System.out.println("n: " + n);
                  System.out.println("Conteúdo prioritário a enviar: "+msg);
                //  String ttl2 = cache.get_ID_to_ttl();
                  InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
                  DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.length(), group, MULTICAST_PORT);
                  MulticastSocket ms = new MulticastSocket();
                  int ttl = ms.getTimeToLive();
                  List<InetAddress> addrs = obtainValidAddresses(group);
                  //ms.setTimetoLive(ttl);
                  for (InetAddress addr: addrs) {
                     try{
                        Thread.sleep((long)Math.random()*5000);
                     }catch(InterruptedException e){
                        System.out.println("Error: "+e);
                     }
                     System.out.println("Sending on " + addr);
                     ms.setInterface(addr);
                     ms.send(dp);
                     ms.setTimeToLive(ttl);
                     ms.setLoopbackMode(true);
                     try{
                        while(Thread.currentThread().isInterrupted()){
                           for(InetAddress adrs: addrs){
                              System.out.println("Address: " + addr);
                           }
                           ms.joinGroup(group);
                           byte[] receiveBuff = new byte[8192];
                           DatagramPacket pcktreceiver = new DatagramPacket(receiveBuff, receiveBuff.length);
                           ms.receive(pcktreceiver);
                           String res = new String(pcktreceiver.getData(), 0 , pcktreceiver.getLength());
                           String adr = new String(pcktreceiver.getAddress().toString());
                           System.out.println("Receive message: " + res + "from: " + adr);              
                        }
                     }catch(Exception e){
                        System.out.println("Error: "+ e);
                     }
                     
                  }
               }
            }
      
            msg = " ";
            for(int i=0; i<alerta.size()-1;i++){   //alerta-1, para a string nao ficar com o valor da prioridade
               msg += alerta.get(i);
            }
         
            int op = verify_payload(alerta,cache);

            if(op == 1){   //enviar trama e adiciona à cache

               InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
               System.out.println("\nSending by multicast the message: " + msg);
               System.out.println("msg: " + msg);

               DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.length(), group, MULTICAST_PORT);
               MulticastSocket ms = new MulticastSocket();
               int ttl = ms.getTimeToLive();
               List<InetAddress> addrs = obtainValidAddresses(group);
               for (InetAddress addr: addrs) {
                  try{
                     Thread.sleep((long)Math.random()*5000);
                  }catch(InterruptedException e){
                     System.out.println("Error: "+e);
                  }
                  System.out.println("Sending on " + addr);
                  ms.setInterface(addr);
                  ms.send(dp);
                  ms.setTimeToLive(ttl);
                  ms.setLoopbackMode(true);
                  try{
                     while(Thread.currentThread().isInterrupted()){
                        for(InetAddress adrs: addrs){
                           System.out.println("Address: " + addr);
                        }
                        ms.joinGroup(group);
                        byte[] receiveBuff = new byte[8192];
                        DatagramPacket pcktreceiver = new DatagramPacket(receiveBuff, receiveBuff.length);
                        ms.receive(pcktreceiver);
                        String res = new String(pcktreceiver.getData(), 0 , pcktreceiver.getLength());
                        String adr = new String(pcktreceiver.getAddress().toString());
                        System.out.println("Receive message: " + res + "from: " + adr);              
                     }
                  }catch(Exception e){
                     System.out.println("Error1: "+ e);
                  }
                  
               }

               String id = String.valueOf(idx);
               int value_prioridade = Integer.parseInt(alerta.get(6));
               cache.add_ID_to_ttl(id,msg);
               cache.add_ID_to_Hops(id,msg);
               cache.add_ID_to_Payload(id,msg);
               
   
               if(value_prioridade == 1){
                     cache.add_ID_to_Prioridade("addMeteorologia");
               }
               if(value_prioridade == 2){
                  cache.add_ID_to_Prioridade("addObras");
               }
               if(value_prioridade == 3){
                  cache.add_ID_to_Prioridade("addAcidente");
               }
               if(value_prioridade == 4){
                  cache.add_ID_to_Prioridade("addAmbulancia");
               }
               System.out.println("New alert added to cache");
               

               ms.close();
            }
            else{ //op==0 alerta já existente
               System.out.println("Alert already exist");
            }
            msg = " ";
         }
      }catch(UnknownHostException e){
         System.out.println("Error "+e);
      }catch(IOException e){
         System.out.println("Error "+e);
      }catch(Exception e){
         System.out.println("Error1: "+e);
      }
   }
}