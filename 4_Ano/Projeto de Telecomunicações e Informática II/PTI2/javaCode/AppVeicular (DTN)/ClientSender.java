
import java.net.MulticastSocket;
import java.lang.Runnable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.UnknownHostException;

public class ClientSender{
    private static final boolean Cli_SENDER = true;
    public static void main(String[] args) throws Exception{

        cache_car cache = cache_car.getInstance();
        exMcastEnvia sender = new exMcastEnvia(cache);
        Thread tsender = new Thread(sender);
        tsender.start();

        Thread enviarParaRSU = new Thread(new envio_forRSU(cache));
        enviarParaRSU.start();

        Thread receber = new Thread(new exMcastRecebe());
        receber.start();

        int n; //verify the hellos

        while(Cli_SENDER){
           
                try{
                    tsender.sleep((long)Math.random()*5000);
                }catch(InterruptedException e){
                    System.out.println("Error: "+e);
                }
            }
        }
    }
