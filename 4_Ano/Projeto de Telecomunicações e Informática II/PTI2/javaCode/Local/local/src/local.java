

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.print.DocFlavor.STRING;

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
        //connection_db con = new connection_db();
        LocalDB con = new LocalDB();
        System.out.println("entrou 2");

        //con.resetDBLocal();
        System.out.println("entrou 3");

        //con.createTablesLocal();
        System.out.println("entrou 4");

        //con.populateTypeAlert();
        File fp=new File("/home/andre/Documentos/Code/pti2/pti2-1819/javaCode/Local/local/configuracao.txt");

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
        System.out.println("|"+conf.get(0)+"|");
        System.out.println("|"+porta+"|");
        String ip = conf.get(0);

        System.out.println("recebeu ip"+conf.get(0)+"porta:"+porta);
        cs=new Socket(ip,porta);
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
    //connection_db con;
    LocalDB con;
    ArrayList<Integer> alertas_cancelados = new ArrayList<>();

    String current;
    BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
    String ip_rsu;

    criar_local(Socket Socket, LocalDB con,String ip_rsu) {
        this.socket = Socket;
        this.con = con;
        this.ip_rsu = ip_rsu;
    }

    public void atualizar_alerta(String receber, int estado,PrintWriter out){
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
        String trama = null;

        if(estado==0){
            if(tipo.equals("Meteorologia")){
                resposta = con.updateAlert (Integer.parseInt(index),Integer.parseInt(id_alerta), timeStamp, 0, ip_origem, hello.getTrama_hello().get(1), 1);
                System.out.println(resposta);
                trama = timeStamp +","+ "0" +","+ip_origem +","+ id_local +","+ "1";
            }
            if(tipo.equals("Obras")){
                resposta = con.updateAlert (Integer.parseInt(index),Integer.parseInt(id_alerta), timeStamp, 0, ip_origem, hello.getTrama_hello().get(1), 2);
                System.out.println(resposta);
                trama = timeStamp +","+ "0" +","+ip_origem +","+ id_local +","+ "2";
            }
            if(tipo.equals("Ambulancia")){
                resposta = con.updateAlert (Integer.parseInt(index),Integer.parseInt(id_alerta), timeStamp, 0, ip_origem, hello.getTrama_hello().get(1), 3);
                System.out.println(resposta);
                trama = timeStamp +","+ "0" +","+ip_origem +","+ id_local +","+ "3";
            }
            if(tipo.equals("Acidente")){
                resposta = con.updateAlert (Integer.parseInt(index),Integer.parseInt(id_alerta), timeStamp, 0, ip_origem, hello.getTrama_hello().get(1), 4);
                System.out.println(resposta);
                trama = timeStamp +","+ "0" +","+ip_origem +","+ id_local +","+ "4";
            }
            if(tipo.equals("Transito")|| tipo.equals("Trânsito")){
                resposta = con.updateAlert (Integer.parseInt(index),Integer.parseInt(id_alerta), timeStamp, 0, ip_origem, hello.getTrama_hello().get(1), 5);
                System.out.println(resposta);
                trama = timeStamp +","+ "0" +","+ip_origem +","+ id_local +","+ "5";
            }
            out.println(trama);
        }
        else{
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

        Thread receive_from_car = new Thread(new receive_from_carro(out,con,ip_rsu));
        receive_from_car.start();

        //Thread receive_from_RSU = new Thread(new udpCom_receive(9999));
        //receive_from_RSU.start();
        ///////////////////////////////
        ArrayList<String> alertas = new ArrayList<>();

        while (true) {
            alertas_cancelados = Control_time.getAlertas();

            try {
                receber=in.readLine();
                alertas = con.getAlerts();
                System.out.println("Entrou no criar local");

                System.out.println("print alertas:"+alertas);

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
                    con.removeAlert(Integer.parseInt(receber),out);
                }

                if(count==6){  //atualizar dados alerta, possui o campo id_alerta
                    int estado = 1;
                    atualizar_alerta(receber,estado,out);
                }

                if(count==7){   //adicionar novo alerta
                    String ttl = null;
                    String hops = null;

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

                        String enviar_para_o_carro = null;


                        if(tipo.equals("Meteorologia")){
                            System.out.println("Adicionar alerta de meteorologia");
                            con.insertAlert(Integer.parseInt(id_alerta),timeStamp,1,origem,hello.getTrama_hello().get(1), 1);
                            hops = String.valueOf(Configuracao.meteohops);
                            ttl = String.valueOf(Configuracao.meteottl);
                            System.out.println("Olá");
                            enviar_para_o_carro = id_alerta +"," +ttl+","+ hops +","+ tipo +","+
                                    timeStamp +","+ id_local;

                            //String tempo_atual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                            try{
                                Thread control_time = new Thread (new Control_time(ttl,timeStamp,Integer.parseInt(id_alerta),con,out));
                                control_time.start();
                            }catch(Exception e){}

                            udpCom enviar = new udpCom(enviar_para_o_carro);
                            Thread thread2 = new Thread(enviar);
                            thread2.start();

                        }
                        if(tipo.equals("Obras")){
                            con.insertAlert(Integer.parseInt(id_alerta),timeStamp,1,origem,hello.getTrama_hello().get(1), 2);

                            hops = String.valueOf(Configuracao.obrahops);
                            ttl = String.valueOf(Configuracao.obrattl);

                            enviar_para_o_carro = id_alerta +"," +ttl+","+ hops +","+ tipo +","+
                                    timeStamp +","+ id_local;
                            udpCom enviar = new udpCom(enviar_para_o_carro);

                            udpCom enviar2 = new udpCom(enviar_para_o_carro);
                            Thread thread3 = new Thread(enviar2);
                            thread3.start();

                            Thread control_time = new Thread (new Control_time(ttl,timeStamp,Integer.parseInt(id_alerta),con,out));
                            control_time.start();

                        }
                        if(tipo.equals("Ambulancia")){
                            con.insertAlert(Integer.parseInt(id_alerta),timeStamp,1,origem,hello.getTrama_hello().get(1), 3);

                            hops = String.valueOf(Configuracao.ambhops);
                            ttl = String.valueOf(Configuracao.ambttl);

                            enviar_para_o_carro = id_alerta +"," +ttl+","+ hops +","+ tipo +","+
                                    timeStamp +","+ id_local;
                            udpCom enviar = new udpCom(enviar_para_o_carro);

                            udpCom enviar4 = new udpCom(enviar_para_o_carro);
                            Thread thread4 = new Thread(enviar4);
                            thread4.start();

                            Thread control_time = new Thread (new Control_time(ttl,timeStamp,Integer.parseInt(id_alerta),con,out));
                            control_time.start();
                        }
                        if(tipo.equals("Acidente")){
                            con.insertAlert(Integer.parseInt(id_alerta),timeStamp,1,origem,hello.getTrama_hello().get(1), 4);

                            hops = String.valueOf(Configuracao.acidhops);
                            ttl = String.valueOf(Configuracao.acidttl);

                            enviar_para_o_carro = id_alerta +"," +ttl+","+ hops +","+ tipo +","+
                                    timeStamp +","+ id_local;
                            udpCom enviar = new udpCom(enviar_para_o_carro);

                            udpCom enviar5 = new udpCom(enviar_para_o_carro);
                            Thread thread5 = new Thread(enviar5);
                            thread5.start();

                            Thread control_time = new Thread (new Control_time(ttl,timeStamp,Integer.parseInt(id_alerta),con,out));
                            control_time.start();
                        }
                        if(tipo.equals("Transito") || tipo.equals("Trânsito")){
                            con.insertAlert(Integer.parseInt(id_alerta),timeStamp,1,origem,hello.getTrama_hello().get(1), 5);
                            hops = String.valueOf(Configuracao.transitohops);
                            ttl = String.valueOf(Configuracao.transitottl);

                            enviar_para_o_carro = id_alerta +"," +ttl+","+ hops +","+ tipo +","+
                                    timeStamp +","+ id_local;
                            udpCom enviar = new udpCom(enviar_para_o_carro);

                            udpCom enviar6 = new udpCom(enviar_para_o_carro);
                            Thread thread6 = new Thread(enviar6);
                            thread6.start();

                            Thread control_time = new Thread (new Control_time(ttl,timeStamp,Integer.parseInt(id_alerta),con,out));
                            control_time.start();
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
                Thread.sleep((int) (Math.random() * 5000));   //10 segundos
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
    //connection_db con;
    LocalDB con;
    String ip_rsu;
    /*
    public receive_from_carro(PrintWriter out,connection_db con,String ip_rsu){
        this.out = out;
        this.con = con;
        this.ip_rsu = ip_rsu;
    }*/
    public receive_from_carro(PrintWriter out,LocalDB con,String ip_rsu){
        this.out = out;
        this.con = con;
        this.ip_rsu = ip_rsu;
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

        ArrayList<String> alertas_existentes = new ArrayList<>();

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
                alertas_existentes = con.getAlerts();

                if(alertas_existentes.size()>=2){
                    sendAll();
                }
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
           /* if(count==1){  //rececao de alertas
                String tipo = parts[0];
                String timeStamp = parts[1];
                String estado = Integer.toString(1);

                String alerta = timeStamp+","+estado+","+origem+","+ip_rsu+","+tipo;

                if(tipo.equals("Meteorologia")) {
                    con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,1);
                }
                if(tipo.equals("Ambulancia")) {
                    con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,3);
                }
                if(tipo.equals("Obras")) {
                        con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,2);
                }
                if(tipo.equals("Acidente")) {
                    con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,4);
                }
                if(tipo.equals("Transito")) {
                    con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,5);
                }
                System.out.println("Vou enviar o alerta para a central:" +alerta);
                out.println(alerta);
            }  */

            if(count==5){  //significa que é um alerta proveniente do carro
                //String id = parts[0];
                String tipo = parts[3];
                String timeStamp = parts[4];
                String id_local_destino = parts[5];
                String estado = Integer.toString(1);
                ArrayList<String> alertas = new ArrayList<>();
                alertas = con.getAlerts();
                //int valor = 0;

                String alerta = timeStamp+","+estado+","+origem+","+ip_rsu+","+tipo;

                if(alertas.isEmpty()){
                 /*   String alerta = timeStamp+","+estado+","+origem+","+"1"+","+tipo;
                    ArrayList<String> getAlertas = new ArrayList<>();
                    getAlertas = con.getAlerts();

                    for(int i=0;i<getAlertas.size();i++){
                        String value = getAlertas.get(i);
                        String[] posicao = value.split(",");
                        String time = posicao[2];

                        if(time.equals(timeStamp)){
                            valor = 1;
                        }

                        if(!time.equals(timeStamp) && i==getAlertas.size()){

                        }
                    }*/

                    if(id_local_destino.equals(ip_rsu)){  //se o id_local == ao id do rsu que  recebeu, guarda na BD
                        if(tipo.equals("Meteorologia")) {
                            con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,1);

                            //nteger.parseInt(id_alerta),timeStamp,1,origem,hello.getTrama_hello().get(1), 1);
                        }
                        if(tipo.equals("Ambulancia")) {
                            con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,3);
                        }
                        if(tipo.equals("Obras")) {
                            con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,2);
                        }
                        if(tipo.equals("Acidente")) {
                            con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,4);
                        }
                        if(tipo.equals("Transito")) {
                            con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,5);
                        }
                        System.out.println("Vou enviar o alerta para a central:" +alerta);
                        out.println(alerta);
                    }
                    else{
                        System.out.println("Vou enviar o alerta para a central:" +alerta);
                        out.println(alerta);
                    }
                }

                for(int i=0;i<alertas.size();i++){
                    String value = alertas.get(i);
                    String buf[] = value.split(",");

                    if((i==alertas.size()-1) && !buf[2].equals(timeStamp)){
                        //String alerta = timeStamp+","+estado+","+origem+","+id_local_destino+","+tipo;
                        alerta = timeStamp+","+estado+","+origem+","+"1"+","+tipo;
                        if(id_local_destino.equals(ip_rsu)){  //se o id_local == ao id do rsu que  recebeu, guarda na BD
                            if(tipo.equals("Meteorologia")) {
                                con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,1);

                                //nteger.parseInt(id_alerta),timeStamp,1,origem,hello.getTrama_hello().get(1), 1);
                            }
                            if(tipo.equals("Ambulancia")) {
                                con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,3);
                            }
                            if(tipo.equals("Obras")) {
                                con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,2);
                            }
                            if(tipo.equals("Acidente")) {
                                con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,4);
                            }
                            if(tipo.equals("Transito")) {
                                con.insertAlert(0,timeStamp,Integer.valueOf(estado),origem,ip_rsu,5);
                            }
                            System.out.println("Vou enviar o alerta para a central:" +alerta);
                            out.println(alerta);
                        }
                        else{
                            System.out.println("Vou enviar o alerta para a central:" +alerta);
                            out.println(alerta);
                        }
                    }
                }
            }
            s = " ";
            Arrays.fill(buffer, (byte)0);  //limpar o array
        }
    }

    public void sendAll(){
        ArrayList<String> lista_alertas = new ArrayList<>();
        ArrayList<String> enviar_para_o_carro = new ArrayList<>();
        lista_alertas = con.getAlerts();
        System.out.println("Entrei no send all");
        int id = 0;




        for(String s : lista_alertas){
            String parts[] = s.split(",");  //divisao da string proveniente da base de dados
            String timeStamp_bd = parts[2];
            String ip_rsu_bd = parts[5];
            int id_local = 0;
            int ttl = 0;
            int hops = 0;

            if(ip_rsu_bd.equals(Configuracao.ip1)){
                id_local = 1;
            }
            if(ip_rsu_bd.equals(Configuracao.ip2)){
                id_local = 2;
            }
            if(ip_rsu_bd.equals(Configuracao.ip3)){
                id_local = 3;
            }

            String tipo_bd = parts[6];
            System.out.println("*********************");
            System.out.println("Tipo de alerta:"+tipo_bd);
            System.out.println("*********************");

            id++;
            String tipo = null;
            Packet_to_car tocar = new Packet_to_car(id, ttl, hops, tipo, timeStamp_bd,id_local);
            tocar.setDataehora(timeStamp_bd);
            System.out.println("Timestamp: "+ tocar.getDataehora());
            tocar.setIDlocal(Integer.valueOf(id_local));
            System.out.println("ID_Local: " + tocar.getIDLocal());


            if(tipo_bd.equals("1")){  //meteorologia
                hops = Configuracao.meteohops;
                ttl = Configuracao.meteottl;
                tipo = "Meteorologia";
                tocar.setHops(hops);
                System.out.println("Hops: " + tocar.getHops());
                tocar.setTTL(ttl);
                System.out.println("TTL: " + tocar.getTTL());
                tocar.setTipo(tipo);
                System.out.println("Tipo: " + tocar.getTipo());
            }
            if(tipo_bd.equals("2")){  //Obras
                hops = Configuracao.obrahops;
                ttl = Configuracao.obrattl;
                tipo = "Obras";
                tocar.setHops(hops);
                System.out.println("Hops: " + tocar.getHops());
                tocar.setTTL(ttl);
                System.out.println("TTL: " + tocar.getTTL());
                tocar.setTipo(tipo);
                System.out.println("Tipo: " + tocar.getTipo());
            }
            if(tipo_bd.equals("3")){   //AMbulancia
                hops = Configuracao.ambhops;
                ttl = Configuracao.ambttl;
                tipo = "Ambulancia";
                tocar.setHops(hops);
                System.out.println("Hops: " + tocar.getHops());
                tocar.setTTL(ttl);
                System.out.println("TTL: " + tocar.getTTL());
                tocar.setTipo(tipo);
                System.out.println("Tipo: " + tocar.getTipo());
            }
            if(tipo_bd.equals("4")){  //Acidente
                hops = Configuracao.acidhops;
                ttl = Configuracao.acidttl;
                tipo = "Acidente";
                tocar.setHops(hops);
                System.out.println("Hops: " + tocar.getHops());
                tocar.setTTL(ttl);
                System.out.println("TTL: " + tocar.getTTL());
                tocar.setTipo(tipo);
                System.out.println("Tipo: " + tocar.getTipo());
            }
            if(tipo_bd.equals("5")){   //Transito
                hops = Configuracao.transitohops;
                ttl = Configuracao.transitottl;
                tipo = "Transito";
                tocar.setHops(hops);
                System.out.println("Hops: " + tocar.getHops());
                tocar.setTTL(ttl);
                System.out.println("TTL: " + tocar.getTTL());
                tocar.setTipo(tipo);
                System.out.println("Tipo: " + tocar.getTipo());
            }

            String pacote = tocar.toString();
            System.out.println("Print do pacote:"+pacote);
            try{
                udpCom senviar = new udpCom(pacote);
                Thread tenviar = new Thread(senviar);
                tenviar.start();
                //Thread.sleep(2000);
            }catch (Exception e){}

        }
    }
}
