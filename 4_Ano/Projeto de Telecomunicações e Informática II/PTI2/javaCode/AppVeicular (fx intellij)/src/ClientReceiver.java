import java.io.IOError;
import java.io.IOException;
import java.net.*;
import java.lang.Runnable;
import java.util.*;

public class ClientReceiver implements Runnable{
  
   ProcessamentoAlert parseAlert;

   public ClientReceiver(ProcessamentoAlert parseAlert){
      this.parseAlert = parseAlert;
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

     public void run(){
         try{
                InetAddress group = InetAddress.getByName(ReadConfigFile.MULTICAST_ADDRESS);

                List<InetAddress> addrs = obtainValidAddresses(group);
                for(InetAddress addr: addrs){
                System.out.println("Address " + addr);
            }

            MulticastSocket ms = new MulticastSocket(ReadConfigFile.MULTICAST_PORT);
            ms.joinGroup(group);
            byte[] buffer = new byte[8192];
            while(true){
               
               System.out.println("A esperar pela chegada de um alerta enviado por multicast");
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                ms.receive(dp);
                String s = new String(dp.getData(), 0 , dp.getLength());
                String addr = new String(dp.getAddress().toString());

                System.out.println("A receber alerta " + s + " from " + addr );
                parseAlert.alertParse(s);
               }
        }catch(UnknownHostException e){
            System.out.println("Error "+e);
         }catch(IOException e){
            System.out.println("Error "+e);
         }catch(Exception e){
            System.out.println("Error: "+e);
         }
    }
}