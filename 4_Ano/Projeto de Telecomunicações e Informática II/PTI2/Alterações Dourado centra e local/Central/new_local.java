import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class new_local {
    //static PrintWriter out;
    int porta;

    /*public PrintWriter getOut(){
        return this.out;
    }*/
    public static ArrayList<PrintWriter> outs = new ArrayList<>();
    public static ArrayList<BufferedReader> ins =new ArrayList<>();


    public static ArrayList<PrintWriter> getOuts(){
        return outs;
    }

    public static void setOuts(PrintWriter writer){
        outs.add(writer);
    }

    public static ArrayList<BufferedReader> getIns(){
        return ins;
    }

    public static void setIns(BufferedReader in){
        ins.add(in);
    }


    public void new_local() throws IOException {
        //9001 9002 9003

        for(int i=0;i<3;i++) {
            porta=9001+i;
            Thread t = new Thread(new com(porta));
            t.start();
            System.out.println("criei o socket:");
        }
    }
}

class save_ip_printwriters {
    static ArrayList<String> lista = new ArrayList<String>();
    static ArrayList<PrintWriter> lista_printWriter = new ArrayList<>();

   /* public static ArrayList<String> getIPs(){
        return this.lista;
    }*/
   String ip;
   PrintWriter p;

   public void save_as(String ip, PrintWriter p) {
       Single_universal single = Single_universal.getInstance();
       single.put_ip_printwriter(ip, p);
   }
}

class com implements Runnable {
    private Socket cs;
    static PrintWriter out;
    BufferedReader in;
    private String receber;
    private int porta;
    private ArrayList<String> trama_hello = new ArrayList<String>();

    //private ArrayList<alerta>  alerta_clone = new ArrayList<alerta>();

    com(int porta) throws IOException {
        this.porta = porta;

    }
/////////////////////////

    public ArrayList<String> getHello() {
        return this.trama_hello;
    }

    public PrintWriter getOut() {
        return this.out;
    }

    public Socket getSocket() {
        return this.getSocket();
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(porta);
            cs = ss.accept();
            out = new PrintWriter(cs.getOutputStream(), true);
            new_local.setOuts(out);
            in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            new_local.setIns(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

                while ((receber = in.readLine()) != null) {  //ler do socket,até fazer control_D no clieente

                int count = 0; //contar o numero de ,
                for(int i=0;i<receber.length();i++){  //para verificar o numero de , existentes
                    char c = receber.charAt(i);     //de forma a saber que tipo de trama é
                    if(c==','){
                        count++;
                    }
                }

                if (receber.substring(1).contains("Hello")) {   //trama Hello recebida
                    System.out.println("recebi um Hello: " + receber);
                    for (String val : receber.split(",")) {
                        trama_hello.add(val);
                    }
                    String ip_trama_hello = trama_hello.get(1);
                    Single_universal single = Single_universal.getInstance();
                    PrintWriter nout = single.getPrintWriter(ip_trama_hello.substring(1,ip_trama_hello.length()-1));

                    if (nout == null){
                        System.out.println("Adicionou ip ao single");
                        //System.out.println("ip" +(ip_trama_hello.substring(1,ip_trama_hello.length()-1)));
                        //System.out.println("printwriter" +out);

                        single.put_ip_printwriter(ip_trama_hello.substring(1,ip_trama_hello.length()-1), out);
                        single.put_printwriter_ip(out,ip_trama_hello.substring(1,ip_trama_hello.length()-1));

                    }

                    //save.save_PrintWriter(out);
                }
                       //      String alerta = timeStamp+","+estado+","+origem+","+id_local_destino+","+tipo;

                if(count==4) {  //receber alertas provenientes do RSU
                    receber = charRemove(receber, 0);
                    //receber = charRemove(receber,1);
                    receber = charRemove(receber, receber.length() - 1);
                    String[] parts = receber.split(",");

                    System.out.println("Recebi um novo alerta: " + receber);

                    DBCentral db = new DBCentral();

                    String[] val = new String[0];

                    String tokens[] = receber.split(",");
                    String ip_hello = tokens[1];

                    ip_hello = ip_hello.substring(0, ip_hello.indexOf(","));

                    //System.out.println(receber);
                    String new_ip = ip_hello.trim();


                    String time_Stamp = parts[0];
                    String estado = parts[1];   // para saber de que tipo é o alerta, para poder enviar para os restantes RSU
                    String ip_origem = parts[2];
                    String id_local = parts[3];
                    String tipo = parts[4];

                    String ip_destino = db.getIP_local(Integer.valueOf(id_local));



                    ArrayList<PrintWriter> outs;
                    outs=new_local.getOuts();
                    ArrayList<BufferedReader> ins;
                    ins=new_local.getIns();

                    //trama_local = nome +","+ timestamp+","+ Integer.valueOf(state) +","+ip_origem +","+ip_rsu+","+Integer.valueOf(id_local)+","+3+","+Integer.valueOf(id_user);

                    if(!id_local.equals(null)) {
                        if (tipo.equals("Meteorologia")) {
                            String res = db.insertAlert(time_Stamp, Integer.parseInt(estado), ip_origem, Integer.parseInt(id_local), 1, 0);  //ip_hello-> é o ip destino do rsu
                            for(int i=0;i<=2;i++){
                                if(in!=ins.get(i)) {
                                    send_to_local send = new send_to_local(outs.get(i), receber);
                                }
                            }
                        }
                        if (tipo.equals("Obras")) {
                            String res = db.insertAlert(time_Stamp, 1, ip_origem,Integer.parseInt(id_local), 2, 0);  //ip_hello-> é o ip destino do rsu

                            if(ip_destino.contains(".2")){
                                new send_to_local(outs.get(0),receber);
                            }
                            if(ip_destino.contains(".3")){
                                new send_to_local(outs.get(1),receber);
                            }
                            if(ip_destino.contains(".4")){
                                new send_to_local(outs.get(2),receber);
                            }
                        }
                        if (tipo.equals("Ambulancia")) {
                            String res = db.insertAlert(time_Stamp, 1, ip_origem,Integer.parseInt(id_local), 3, 0);  //ip_hello-> é o ip destino do rsu
                            if(ip_destino.contains(".2")){
                                new send_to_local(outs.get(0),receber);
                            }
                            if(ip_destino.contains(".3")){
                                new send_to_local(outs.get(1),receber);
                            }
                            if(ip_destino.contains(".4")){
                                new send_to_local(outs.get(2),receber);
                            }
                        }
                        if (tipo.equals("Acidente")) {
                            String res = db.insertAlert(time_Stamp, 1, ip_origem,Integer.parseInt(id_local), 4, 0);  //ip_hello-> é o ip destino do rsu
                            if(ip_destino.contains(".2")){
                                new send_to_local(outs.get(0),receber);
                            }
                            if(ip_destino.contains(".3")){
                                new send_to_local(outs.get(1),receber);
                            }
                            if(ip_destino.contains(".4")){
                                new send_to_local(outs.get(2),receber);
                            }
                        }
                        if (tipo.equals("Trânsito")) {
                            String res = db.insertAlert(time_Stamp, 1, ip_origem,Integer.parseInt(id_local), 5, 0);  //ip_hello-> é o ip destino do rsu
                            if(ip_destino.contains(".2")){
                                new send_to_local(outs.get(0),receber);
                            }
                            if(ip_destino.contains(".3")){
                                new send_to_local(outs.get(1),receber);
                            }
                            if(ip_destino.contains(".4")){
                                new send_to_local(outs.get(2),receber);
                            }
                        }
                    }
                    else{
                        System.out.println("O ip do rsu nao existe");
                    }
                }
            } //alerta, mensagem, data, ip_origem, ip_rsu, id_local,id_alertType,id_user
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String charRemove(String str, int p) {

        return str.substring(0, p) + str.substring(p + 1);
    }
}
