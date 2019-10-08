import java.net.MulticastSocket;
import java.lang.Runnable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.UnknownHostException;

public class Client{
   public static void main(String[] args) throws Exception{
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);

        exMcastEnvia sender = new exMcastEnvia(x,y);
        Thread tsender = new Thread(sender);
        tsender.start();

        exMcastRecebe receiver = new exMcastRecebe();
        Thread treceiver = new Thread(receiver);
        treceiver.start();

        Thread enviarParaRSU = new Thread(new envio_forRSU());
        enviarParaRSU.start();
        
    }


    //envio de informacao para os RSU
    
}