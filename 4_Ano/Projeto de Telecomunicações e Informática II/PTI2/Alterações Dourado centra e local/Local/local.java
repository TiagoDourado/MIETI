import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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



//cliente
public class local{

    private static Socket cs;
    public static void main(String[] args) throws IOException {
        System.out.println("entrou 1");
        ArrayList<String> conf=new ArrayList<String>();
        connection_db con = new connection_db();
        System.out.println("entrou 2");

        con.resetDBLocal();
        System.out.println("entrou 3");

        con.createTablesLocal();
        System.out.println("entrou 4");

        con.populateTypeAlert();
        File fp=new File("/home/pedro/Desktop/PTI sem git/pti2-1819/javaCode/Local/local/configuracao.txt");

        Scanner scanner2=null;
        int porta;     //para receber start e stop;

        try {
            scanner2=new Scanner(fp);
            while(scanner2.hasNext()){
                conf.add(scanner2.next());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(local.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        porta=Integer.parseInt(conf.get(1));
        System.out.println("recebeu ip"+conf.get(0)+"porta:"+porta);
        cs=new Socket(conf.get(0),porta);
        PrintWriter out = new PrintWriter(cs.getOutputStream(),true);
        Thread thread= new Thread(new criar_local(cs,con,conf.get(0)));
        thread.start();
    }
}

class criar_local implements Runnable {
    Socket socket;
    //Hello hello;
    ArrayList<String> alert=new ArrayList<String>();
    String ip_destino;
    connection_db con;

    String current;
    BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
    String ip_rsu;

    criar_local(Socket Socket, connection_db con,String ip_rsu) {
        this.socket = Socket;
        this.con = con;
        this.ip_rsu = ip_rsu;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        String receber;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  //Para receber;
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 0;

        Thread thread_hello=new Thread(new send_Hello(out,ip_rsu));
        thread_hello.start();

        Thread thread = new Thread(new Alerta(out));
        thread.start();

        Thread receive_from_car = new Thread(new receive_from_carro(out));
        receive_from_car.start();

        //Thread receive_from_RSU = new Thread(new udpCom_receive(9999));
        //receive_from_RSU.start();
        ///////////////////////////////
        ArrayList<String> alertas = new ArrayList<>();

        while (true) {
                try { 
                    System.out.println("Entrou no criar local"); 
                    alertas = con.getAlerts();
                    System.out.println("print alertas:"+alertas);
                    receber=in.readLine();
                    System.out.println("print receber:"+receber);

                    System.out.println("Recebi da central a mensagem:" +receber);
                     
                    for (String s : receber.split(",")) {
                        alert.add(s.trim());
                    }

                    int count = 0; //contar o numero de ,
                    for(int j=0;j<receber.length();j++){  //para verificar o numero de , existentes
                        char c = receber.charAt(j);     //de forma a saber que tipo de trama é
                        if(c==','){
                            count++;
                        }
                    }

                    if(count==0){  //significa que se pretende remover um alerta
                        System.out.println("Vou eliminar um alerta");
                        con.removeAlert(Integer.parseInt(receber));
                    }

                    if(count==6){  //atualizar dados alerta, possui o campo id_alerta
                        System.out.println("Entrou no editar alerta");
                        String[] result = receber.split(",");
                        Hello hello = new Hello(ip_rsu);
                        String id_alerta = result[0];
                        String timeStamp = result[1];
                        String index = result[2];
                        String ip_origem = result[3];
                        String id_local = result[4];
                        String tipo = result[5];
                        String id_user = result[6];   
                        String resposta = null;
                        
                        if(tipo.equals("Meteorologia")){
                            resposta = con.updateAlert (Integer.parseInt(index),Integer.parseInt(id_alerta), timeStamp, 1, ip_origem, hello.getTrama_hello().get(1), 1);   
                            System.out.println(resposta);
                        }
                        if(tipo.equals("Obras")){
                            resposta = con.updateAlert (Integer.parseInt(index),Integer.parseInt(id_alerta), timeStamp, 1, ip_origem, hello.getTrama_hello().get(1), 2);        
                        }
                        if(tipo.equals("Ambulancia")){
                            resposta = con.updateAlert (Integer.parseInt(index),Integer.parseInt(id_alerta), timeStamp, 1, ip_origem, hello.getTrama_hello().get(1), 3);    
                        }
                        if(tipo.equals("Acidente")){
                            resposta = con.updateAlert (Integer.parseInt(index),Integer.parseInt(id_alerta), timeStamp, 1, ip_origem, hello.getTrama_hello().get(1), 4);    
                        }
                        if(tipo.equals("Transito")|| tipo.equals("Trânsito")){
                            resposta = con.updateAlert (Integer.parseInt(index),Integer.parseInt(id_alerta), timeStamp, 1, ip_origem, hello.getTrama_hello().get(1), 5);
                        }
                        System.out.println(resposta);
                    }
                    
                    if(count==7){   //adicionar novo alerta
                        System.out.println("recebi da central:" + receber);
                        
                        if(receber.contains("Meteorologia") || receber.contains("Obras") || receber.contains("Ambulancia")
                        ||receber.contains("Acidente") || receber.contains("Trânsito") || receber.contains("Transito")){
                                
                            String[] result = receber.split(",");
                            //String payload = result[1];
                            String timeStamp = result[0];
                            String estado = result[1];
                            String origem = result[2];
                            String ip_rsu = result[3];
                            String id_local = result[4];
                            String tipo = result[5];
                            String id_user = result[6];        
                            String id_alerta = result[7];                                       
                            Hello hello = new Hello(ip_rsu);

                            if(tipo.equals("Meteorologia")){
                                System.out.println("Adicionar alerta de meteorologia");
                                con.insertAlert(Integer.parseInt(id_alerta),timeStamp,1,origem,hello.getTrama_hello().get(1), 1);
                            }
                            if(tipo.equals("Obras")){
                                con.insertAlert(Integer.parseInt(id_alerta),timeStamp,1,origem,hello.getTrama_hello().get(1), 2);
                              //  con.insertLocal(Integer.parseInt(id_local),Integer.parseInt(id_alerta));
                            }
                            if(tipo.equals("Ambulancia")){
                                con.insertAlert(Integer.parseInt(id_alerta),timeStamp,1,origem,hello.getTrama_hello().get(1), 3);
                               // con.insertLocal(Integer.parseInt(id_local),Integer.parseInt(id_alerta));
                            }
                            if(tipo.equals("Acidente")){    
                                con.insertAlert(Integer.parseInt(id_alerta),timeStamp,1,origem,hello.getTrama_hello().get(1), 4);
                               // con.insertLocal(Integer.parseInt(id_local),Integer.parseInt(id_alerta));
                            }
                            if(tipo.equals("Transito") || tipo.equals("Trânsito")){
                                con.insertAlert(Integer.parseInt(id_alerta),timeStamp,1,origem,hello.getTrama_hello().get(1), 5);

                              //  con.insertLocal(Integer.parseInt(id_local),Integer.parseInt(id_alerta));
                            }
                        }
                    }

                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

class send_Hello implements Runnable{  //envio de Hellos para o servidor central
    PrintWriter out;
    String ip_rsu;

    send_Hello(PrintWriter out,String ip_rsu){
        this.out=out;
        this.ip_rsu=ip_rsu;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);   //10 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Hello hello = new Hello(ip_rsu);
            out.println(hello.getTrama_hello());
        }
    }
}

class receive_from_carro implements Runnable{
    PrintWriter out; //para enviar para a central

    public receive_from_carro(PrintWriter out){ 
        this.out = out;
    }

    public static List<InetAddress> obtainValidAddresses(InetAddress group) {
        List<InetAddress> result = new ArrayList<InetAddress>();
        //System.out.println("\nObtain valid addresses according to group address");
        //verify if group is a multicast address
        if (group == null || !group.isMulticastAddress()) return result;
        try {
        //obtain the network interfaces list
           Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
           while (ifs.hasMoreElements()) {
              NetworkInterface ni = ifs.nextElement();
              //ignoringg loopback, inactive interfaces and the interfaces that do not support multicast
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
                    || (!group.isMCLinkLocal() && !addr.isLinkLocalAddress())){ 
                    //System.out.println("Interface: " + ni.getDisplayName() + "));
                    result.add(addr);
                 } else {
                    //System.out.println("Ignoring addr: " + addr + " of interfa)ayName());
                 }
              }
           }
        } catch (SocketException ex) {
            System.out.println("Error: " + ex);
        }
        return result;
     }

    public void run(){
        InetAddress group=null;
        MulticastSocket ms=null;

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
            ms = new MulticastSocket(9999);
        }
        catch(IOException e){
            e.printStackTrace();
        }    
        try{
            ms.joinGroup(group);
        }
        catch(IOException e){
            e.printStackTrace();
        }    
        byte[] buffer = new byte[8192];
        String s = " "; 
        int num_hello=0;
        while(true){
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length); 
            try{
                ms.receive(dp);
            }
            catch(IOException i){
                i.printStackTrace();
            }
            s = new String(dp.getData(), 0, dp.getLength()).trim();
            String origem = new String(dp.getAddress().toString());  //endereço de origem

            String parts[] = s.split(",");    //divisao da string proveniente do carro

            //alerta -> id,ttl,hops,prioridade,tipo,payload
            int count = 0; //contar o numero de ,
            for(int i=0;i<s.length();i++){  //para verificar o numero de , existentes
                char c = s.charAt(i);     //de forma a saber que tipo de trama é
                if(c==','){
                    count++;
                }
            }
               
            if(count==5){  //significa que é um alerta proveniente do carro
                //String id = parts[0];
                String tipo = parts[3];
                String timeStamp = parts[4];
                String id_local_destino = parts[5];

                String estado = Integer.toString('1');
                
                String alerta = timeStamp+","+estado+","+origem+","+id_local_destino+","+tipo; 


                System.out.println("Vou enviar o alerta para a central:" +alerta);
                out.println(alerta);
            }
            s = " ";
            Arrays.fill(buffer, (byte)0);  //limpar o array
        }
    }
}


class receive_from_RSU{
    private String alerta;

    public receive_from_RSU(String alerta){
        this.alerta=alerta;
    }
}