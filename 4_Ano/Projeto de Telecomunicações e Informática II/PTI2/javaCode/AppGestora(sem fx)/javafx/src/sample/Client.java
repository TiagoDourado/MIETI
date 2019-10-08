package sample;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Client {


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {

        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null,null,null);
        SocketFactory socketFactory = context.getSocketFactory();
        SSLSocket cs = (SSLSocket) socketFactory.createSocket("127.0.0.1", 8888);
        cs.setEnabledCipherSuites(cs.getSupportedCipherSuites());
        BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
        ArrayList<String> User = new ArrayList<>();
        PrintWriter out = new PrintWriter(cs.getOutputStream(),true);


        Thread t=new Thread(new listen_response(in));
        t.start();

        while(true){
            BufferedReader desc = new BufferedReader(new InputStreamReader(System.in));
            String from_input= desc.readLine();
            String[] trama = from_input.split(" ");


            // System.out.println(trama[0]);
            for(int i = 0 ; i<trama.length;i++){
                User.add(trama[i]);
            }
            out.println(User);
            User.clear();
            out.flush();
        }
    }
}

class listen_response implements Runnable{
    BufferedReader response;

    listen_response(BufferedReader in){
        this.response=in;
    }

    @Override
    public void run() {
        while(true){
            try {
                String resposta = (response.readLine());
                if(resposta!=null){
                    System.out.println(resposta);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
