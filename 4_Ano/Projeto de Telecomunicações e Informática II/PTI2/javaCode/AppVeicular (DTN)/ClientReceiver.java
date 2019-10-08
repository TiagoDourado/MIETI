import java.net.MulticastSocket;
import java.lang.Runnable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.UnknownHostException;

public class ClientReceiver{
    private static final boolean Cli_RECEIVER = true;
    public static void main(String[] args){
        exMcastRecebe receiver = new exMcastRecebe();
        Thread treceiver = new Thread(receiver);
        treceiver.start();
        int n; //verify the hello receivers

        while(Cli_RECEIVER){
            try{
                treceiver.sleep((long)Math.random()*5000);
            }catch(InterruptedException e){
                System.out.println("Error: "+e);
            }
        }
    }
}