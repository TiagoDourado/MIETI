package interface3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javafx.scene.control.ScrollPane;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Client {

String tentativa; 
    public String criarcli(ArrayList<String> User) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null,null,null);
        SocketFactory socketFactory = context.getSocketFactory();
        SSLSocket cs = (SSLSocket) socketFactory.createSocket("2001:8a0:fe02:9c01:6208:c98c:6937:fa5f", 8888);
        cs.setEnabledCipherSuites(cs.getSupportedCipherSuites());

        //Socket cs=new Socket("127.0.0.1",8888);
        BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
        PrintWriter out = new PrintWriter(cs.getOutputStream(),true);
        String res = null;

        Thread t=new Thread(new listen_response(in));
        t.start();
        out.println(User);
        User.clear();
        Resposta re = Resposta.getInstance();
        String anterior = re.getrespostaanterior();
        while(res ==null || anterior==res){
        res = re.getresposta();
        re.putrespostaanterior(res);
        out.flush();
        }
        return res;


    }

    String criarcli(ScrollPane tiposdealertas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                    Resposta re = Resposta.getInstance();
                    re.putresposta(resposta);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

