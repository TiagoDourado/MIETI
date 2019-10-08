

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class udpCom_receive implements Runnable{
    private int port;
    private InetAddress group;
    private MulticastSocket ms;

    public udpCom_receive(int porta){
        this.port = porta;
    }

    public void run(){
        try{
            group = InetAddress.getByName("FF02::1");
        }       
        catch(UnknownHostException e){
            e.printStackTrace();
        }   

        List<InetAddress> addrs = obtainValidAddresses(group);
        for (InetAddress addr: addrs) {
            //System.out.println("Address " + addr);
        }

        try{
            ms = new MulticastSocket(port);
            ms.joinGroup(group);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        
        byte[] buffer = new byte[8192];
        while(true){
            //System.out.println("Waiting for a multicast message sent to FF02::1");
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length); 
            try{
                ms.receive(dp);
            }catch(IOException e){
                e.printStackTrace();
            }
            String s = new String(dp.getData(), 0, dp.getLength());

            if(s.contains("from rsu")){
                String addr = new String(dp.getAddress().toString());
                System.out.println("Recebi o alerta, uma vez que sou RSU: "+s);
            }
            //System.out.println("Receive message " + s + " from " + addr  );
        }
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
                System.out.println("Ignoring addr: " + addr + " of interface " + ni.getDisplayName());
                }
              }
           }
        } catch (SocketException ex) {
            System.out.println("Error: " + ex);
        }
        return result;
     }

    
}