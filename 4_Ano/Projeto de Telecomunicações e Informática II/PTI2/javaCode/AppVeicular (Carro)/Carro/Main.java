import java.lang.Runnable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.UnknownHostException;

public class Main{
    public static void main(String[] args) throws Exception{
        
        Cache cache = Cache.getInstance();
        CreateAlert newAlert = new CreateAlert();
        ClientSender sender = new ClientSender(cache,newAlert,null);
        Thread tsender = new Thread(sender);

        tsender.start();
            
        ProcessamentoAlert parseAlert = new ProcessamentoAlert();
        ClientReceiver receiver = new ClientReceiver(parseAlert);
            
        Thread treceiver = new Thread(receiver);
        treceiver.start();
        
    }
}