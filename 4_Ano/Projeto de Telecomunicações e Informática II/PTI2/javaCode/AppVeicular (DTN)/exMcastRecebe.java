import java.io.IOError;
import java.io.IOException;
import java.net.*;
import java.lang.Runnable;
import java.util.*;
/**
 *
 * @author joao
 */
public class exMcastRecebe implements Runnable {
   public static final int MULTICAST_PORT = 9999;
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
            //ignoringg loopback, inactive interfaces and the interfaces that do not support multicast
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
                  System.out.println("Interface: " + ni.getDisplayName() + " Address: " +addr);
                  result.add(addr);
               } else {
                  System.out.println("Ignoring addr: " + addr + " of interface "
 + ni.getDisplayName());
               }
            }
         }
      } catch (SocketException ex) {
          System.out.println("Error: " + ex);
      }
      return result;
   }

   public static String charRemove(String str, int p) {
      return str.substring(0, p) + str.substring(p + 1);
  }

   public static void alertParse(String t) throws IOException{
     
      String[] al = t.split(",");
      System.out.println("al:"+Arrays.toString(al));
      String identifier = al[0].trim();
      
      ////////////////////////////////////
      
      if(al[6].equals("Meteorologia") || al[6].equals("Obras") || al[6].equals("Ambulancia")
      || al[6].equals("Acidente") || al[6].equals("Trânsito")){
         System.out.println("Recebi um alerta proveniente de um RSU ");
         String mensagem_alerta = al[0];
                  
         System.out.println("O alerta é: "+Arrays.toString(al));
      }


      /////////////////////////////////

      if(identifier.equals("alerta")){
         String idz = al[1];
         System.out.println("id: " + idz); 
         String ttl = al[2];
         System.out.println("ttl: " + ttl);
         String hops = al[3];
         System.out.println("hops: " + hops);
         int hop = Integer.parseInt(hops);
         String alert = al[4];
         System.out.println("alert: " + alert);
         String dataehora = al[5];
         System.out.println("data e hora: " + dataehora);
         System.out.println ("Recebeu: " + identifier + " id: " + idz + " hops: " + hops + " ttl: " + ttl+ " alert: " + alert + " dataehora: " + dataehora);    
         cache_car cache = cache_car.getInstance();
         System.out.println("seq_number: " + cache.get_sequence_number());

      if (Integer.parseInt(idz) == cache.get_sequence_number()){
            System.out.println("entrei");
            Map<String,String> payloads = cache.get_ID_to_Payload_all();
            for(String key : payloads.keySet()){
               int int_key = Integer.parseInt(key);
               if(alert.equals(payloads.get(key))){
                  System.out.println("Alert already exists,dropped");
               }
            }
         }
      else{
         cache.add_ID_to_Payload(idz,alert);
         cache.add_ID_to_ttl(idz,ttl);
         cache.add_ID_to_Hops(idz,hops);
         cache.add_ID_to_dataehora(idz,dataehora);
         if(hop != 0) {
            hop--;
            String pld = cache.get_ID_to_Payload(idz);
            if(alert.equals(pld)){
               System.out.println("Alert already exists, dropped");
            }
            else{
               sendPacketAgain(identifier,idz,ttl,hop,alert,dataehora);
            }
         }
         else{
            System.out.println("Packet dropped");
         }
      }
   }
}

   public static void sendPacketAgain(String ident, String idz, String timelive, int nhop, String al, String dateandtime) throws IOException{
      String samemsg = ident + "," + idz + "," + timelive + "," + String.valueOf(nhop) + "," + al + "," + dateandtime;
      System.out.println("here: " + samemsg);
      int timelive2 = Integer.parseInt(timelive.trim());
      try{
         InetAddress group2 = InetAddress.getByName("FF02::1");
         DatagramPacket pckt = new DatagramPacket(samemsg.getBytes(),samemsg.length(),group2,MULTICAST_PORT);
         MulticastSocket ms2 = new MulticastSocket();
        
         List<InetAddress> adds = obtainValidAddresses(group2);
         for(InetAddress adr: adds){
            try{
               Thread.sleep((long)Math.random()*5000);
            }catch(InterruptedException e){
               System.out.println("Error1: "+e);
            }
            System.out.println("Sending on " + adr + "msg: " + samemsg);
            ms2.setInterface(adr);
            ms2.send(pckt);
            ms2.setTimeToLive(timelive2);
            ms2.setLoopbackMode(true);
            ms2.close();
         }
      }catch(Exception e){
         System.out.println("Error2 " + e);
      } 
      
      
   } 

   public void run(){
      //InetAddress group = InetAddress.getByName("FF15::1:2:3");
      try{
         InetAddress group = InetAddress.getByName("FF02::1");

         List<InetAddress> addrs = obtainValidAddresses(group);
         for (InetAddress addr: addrs) {
            System.out.println("Address " + addr);
         }
         MulticastSocket ms = new MulticastSocket(9999);
         ms.joinGroup(group);
         byte[] buffer = new byte[8192];
         
            while(true){
               System.out.println("Waiting for a multicast message sent to FF02::1");
               DatagramPacket dp = new DatagramPacket(buffer, buffer.length); 
               ms.receive(dp);
               String s = new String(dp.getData(), 0, dp.getLength());
               String addr = new String(dp.getAddress().toString());
               System.out.println("Receive message: " + s + " from " + addr  );
               alertParse(s);
            }

      }catch(UnknownHostException e){
         System.out.println("Error: "+e);
      }catch(IOException e){
         System.out.println("Error: "+e);
      }catch(Exception e){
         System.out.println("Error: "+e);
      } 
   }  
}