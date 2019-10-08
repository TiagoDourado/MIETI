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

public class envio_forRSU implements Runnable{
    private int MULTICAST_PORT = 9999;
    private String MULTICAST_ADDRESS = "FF02::1";
    private cache_car cache;
    private HashMap<String,Integer> alertas = new HashMap<>(); 
    private ArrayList<String> lista_alertas = new ArrayList<String>();

    public envio_forRSU(cache_car cache){
        this.cache=cache;
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
        //para o envio de dados para o rsu, hellos e novos alertas
        InetAddress group = null;
        MulticastSocket ms = null;
        
        try{
            group = InetAddress.getByName(MULTICAST_ADDRESS);
        }
        catch(UnknownHostException e){
            e.printStackTrace();
        }


        List<InetAddress> addrs = obtainValidAddresses(group);
        Thread enviar_hello = new Thread(new enviar_hello(addrs,MULTICAST_PORT,group));
        enviar_hello.start();

        //String msg = "This is a test message sent by MULTICAST";
        //System.out.println("\nSending by multicast the message: " + msg);
        
        try{
            ms = new MulticastSocket();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        int size_lista_alertas=0;  //tamanho da lista de alertas
        String novo_alerta;

        while(true){   // Para enviar alertas para o RSU, verificar se existem novos alertas a enviar
            if(!cache.getEstado_alerta().isEmpty()){
                alertas = cache.getEstado_alerta();
            }
            
            for(String msg : alertas.keySet()){
                if(alertas.get(msg)==0){
                   
                    DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.length(), group, MULTICAST_PORT);
                    System.out.println("enviar: "+msg);    
                    for (InetAddress addr: addrs) {
                        //System.out.println("Sending on " + addr);
                        try{
                            ms.setInterface(addr);
                        }
                        catch(SocketException e){
                            e.printStackTrace();
                        }
                        try{
                            ms.send(dp);
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        } 
                    }
                    cache.replace_estado_alerta(msg, 1);
                    //ms.close();
                }
            }
            alertas.clear();
        }
    }
}
    

class enviar_hello implements Runnable{
    private List<InetAddress> addrs;
    private int MULTICAST_PORT;
    private InetAddress group;


    enviar_hello(List<InetAddress> addrs,int MULTICAST_PORT,InetAddress group){
        this.addrs=addrs;
        this.MULTICAST_PORT=MULTICAST_PORT;
        this.group=group;
    }

    public String Hello(){
        String msg_hello = "hello";
        String identificador = "carro";

        String hello = msg_hello + "," + identificador;
        return hello;
    }

    public void run(){
        String msg = Hello();
        while(true){
            try
            {
                Thread.sleep(10000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            MulticastSocket ms = null; 
            try{
                ms = new MulticastSocket();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.length(), group, MULTICAST_PORT);

            for (InetAddress addr: addrs) {
                //System.out.println("Sending on " + addr);
                try{
                    ms.setInterface(addr);
                }
                catch(SocketException e){
                    e.printStackTrace();
                }
                try{
                    ms.send(dp);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            ms.close();   
        }    
    }
}