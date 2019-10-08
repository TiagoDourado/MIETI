

import java.io.IOException;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class udpCom implements Runnable {  //envio de informacao para carros
    private String msg;


    udpCom(String mensagem) throws IOException {
        this.msg = mensagem;  //mensagem do alerta a enviar para os carros

    }

    public void run(){
        String pacote = null;
        System.out.println("Mensagem: " + msg);
        try{
            if(!msg.equals("null")){
                pacote = msg;
                send(pacote);
            }
        }catch(Exception e){
            System.out.println("Error: " +e);
        }
    }


    synchronized void send(String pacotesender){
        try{
            InetAddress group = InetAddress.getByName(Configuracao.MULTICAST_ADDRESS);
            //System.out.println("Vou enviar par os outros RSU:" +msg);
            MulticastSocket ms = new MulticastSocket();
            DatagramPacket dp = new DatagramPacket(pacotesender.getBytes(), pacotesender.length(), group, Configuracao.MULTICAST_PORT);
            List<InetAddress> addrs = obtainValidAddresses(group);
            for (InetAddress addr: addrs) {
                ms.setInterface(addr);
                ms.send(dp);
            }
            ms.close();
        }catch(UnknownHostException e){
            System.out.println("Error: "+e);
        }catch(IOException e){
            System.out.println("Error: " +e);
        }catch(Exception e){
            System.out.println("Error: " +e);
        }
    }

    public static List<InetAddress> obtainValidAddresses(InetAddress group) {
        List<InetAddress> result = new ArrayList<InetAddress>();
        if (group == null || !group.isMulticastAddress()) return result;
        try {
            //obtain the network interfaces list
            Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
            while (ifs.hasMoreElements()) {
                NetworkInterface ni = ifs.nextElement();
                //ignoring loopback, inactive interfaces and the interfaces that do not support multicast
                if (ni.isLoopback() || !ni.isUp() || !ni.supportsMulticast()) {
                    //System.out.println("Ignoring Interface: " + ni.getDisplayName());
                    continue;
                }
                Enumeration<InetAddress> addrs = ni.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    InetAddress addr = addrs.nextElement();
                    //including addresses of the same type of group address
                    if (group.getClass() != addr.getClass()) continue;
                    if ((group.isMCLinkLocal() && addr.isLinkLocalAddress())
                            || (!group.isMCLinkLocal() && !addr.isLinkLocalAddress())) {
                        //System.out.println("Interface: " + ni.getDisplayName() + " Adress: " +addr);
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
}
