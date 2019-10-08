
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

public class Server {


    public static void main(String args[]) throws IOException, ParseException, NoSuchAlgorithmException, KeyManagementException {

        DBCentral central;
        central = new DBCentral();
        central.resetDBCentral();
        central.createTablesCentral();
        central.populateDB();
        central.populateuser();
        central.populateTypeAlert();

        //new_local com=new new_local();
        //com.new_local();
        Thread thread = new Thread(new com_local());
        thread.start();
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null,null,null);
        SSLServerSocketFactory serverSocketFactory = context.getServerSocketFactory();
        SSLServerSocket ss = (SSLServerSocket)serverSocketFactory.createServerSocket(8888);
        ss.setEnabledCipherSuites(ss.getSupportedCipherSuites());

        //ServerSocket ss = new ServerSocket(8888);
        /*SSLSocket socket = (SSLSocket) ss.accept();*/
        SSLSocket cs ;
        while ((cs =(SSLSocket) ss.accept()) != null) {
            System.out.println("Novo Cliente");
            CommunicatorHelper cl = new CommunicatorHelper(cs);
            Thread td = new Thread(cl);
            td.start();

        }
        ss.close();
    }
}


class com_local implements Runnable{

    @Override
    public void run() {
        new_local com = new new_local();
        try {
            com.new_local();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class CommunicatorHelper implements Runnable {

    Socket cs;
    PrintWriter out;
    BufferedReader in;

    /*public void envio(String env) throws IOException {
         com com=new com(cs);
        //com.send_information(env);
     }*/
    public CommunicatorHelper(Socket cs) throws IOException {
        this.cs = cs;
        try {
            in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(cs.getOutputStream()),true);

            //pipe para leitura
        } catch (IOException e) {
        }
    }
    public static String charRemove(String str, int p) {
        return str.substring(0, p) + str.substring(p + 1);
    }

    public void run() {

        String transmissao = null;

        ArrayList<PrintWriter> outs;
        outs=new_local.getOuts();

        ArrayList<BufferedReader> ins = new ArrayList<>();
        ins=new_local.getIns();

        int identificador_alerta = 0;


        try {
            while ((transmissao = in.readLine()) != null) {
                System.out.println(transmissao);
                System.out.println("Trama Recebida:" + transmissao);
                Scanner scanner = new Scanner(transmissao);
                scanner.useDelimiter(" ");
                transmissao = charRemove(transmissao, 0);
                transmissao = charRemove(transmissao, transmissao.length() - 1);

                String[] trama = transmissao.split(",");
                String Tipo_raw = trama[0];
                String Tipo_trama = Tipo_raw;
                //System.out.println("Tipo raw:" + Tipo_raw);
                //System.out.println("Tipo_tram" +Tipo_trama);
                //in.close();
                in = new BufferedReader(new InputStreamReader(cs.getInputStream()));


                if (Tipo_trama.equals("addLocal")) {
                    String name2 = trama[1];

                    String ip2 = trama[3];
                    String cover2 = trama[2];
                    String name = name2.substring(1);
                    String ip = ip2.substring(1);
                    String cover = cover2.substring(1);
                    System.out.println(name);
                    System.out.println(cover);

                    DBCentral db = new DBCentral();
                    String resposta = db.insertLocal(name, Integer.valueOf(cover), ip);
                    //out.println(res);
                    //envio(res);

                    //System.out.println("ola");
                    //out.println("imprimir resposta" +resposta);
                    PrintWriter outm = com.out;
                    //ArrayList<String> ips_hello = save_ip_printwriters.lista;
                    //ArrayList<PrintWriter> printWriters = save_ip_printwriters.lista_printWriter;
                    out.println(resposta);

                    //System.out.println("arrayList de ips: "+ips_hello);
                    //  send_to_local send = new send_to_local(ip,outm,resposta);

                }

                if (Tipo_trama.equals("addUser")) {
                    String name2 = trama[1];
                    String type2 = trama[2];
                    String usern2 = trama[3];
                    String email2 = trama[4];
                    String password2 = trama[5];
                    String address2 = trama[6];
                    String date_birth2 = trama[7];
                    //String date_regi2 = trama[8];
                    String name = name2.substring(1);
                    String type = type2.substring(1);
                    String usern = usern2.substring(1);
                    String email = email2.substring(1);
                    String password = password2.substring(1);

                    String address = address2.substring(1);
                    String date_birth = date_birth2.substring(1);
                    //String date_regi = date_regi2.substring(1);


                    /*
                    System.out.println(name);
                    System.out.println(type);
                    System.out.println(type);
                    System.out.println(token);
                    System.out.println(passw);
                    System.out.println(email);
                    System.out.println(address);
                    System.out.println(date_birth);
                    System.out.println(date_regi);*/
                    DBCentral db = new DBCentral();
                  /*
                    String[] parts = date_birth.split("-");
                    System.out.println((Integer.valueOf(parts[0])+"nome"+Integer.valueOf(parts[1])+"nome"+Integer.valueOf(parts[2])));
                    java.sql.Date date_b= new java.sql.Date(Integer.valueOf(parts[0]),Integer.valueOf(parts[1]),Integer.valueOf(parts[2]));
                    String[] parts2 = date_regi.split("-");
                    System.out.println((Integer.valueOf(parts2[0])+"nome"+Integer.valueOf(parts2[1])+"nome"+Integer.valueOf(parts2[2])));
                    java.sql.Date date_r= new java.sql.Date(Integer.valueOf(parts2[0]),Integer.valueOf(parts2[1]),Integer.valueOf(parts2[2]));
                    */

                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date myDate = formatter.parse(date_birth);
                    java.sql.Date date_b = new java.sql.Date(myDate.getTime());


                    java.util.Date date = new java.util.Date();
                    String data = formatter.format(date);
                    java.util.Date datereg = formatter.parse(data);
                    java.sql.Date date_r = new java.sql.Date(datereg.getTime());

                    String resp = db.registerUser(name, Integer.parseInt(type), usern, email, password, address, date_b, date_r);
                    out.println(resp);
                }
                if (Tipo_trama.equals("addAlerta")) {
                    System.out.println("entrou no add alerta \n");
                    //String date2 = trama [2];
                    String state2 = trama[1];
                    String ip_origem2 = trama[2];
                    String id_local2 = trama[3];
                    String id_alertTp2 = trama[4];
                    String id_user2 = trama[5];
                    //String date = date2.substring(1);
                    String state = state2.substring(1);
                    String ip_origem = ip_origem2.substring(1);
                    String id_local = id_local2.substring(1);
                    String alertTp = id_alertTp2.substring(1);
                    String id_user = id_user2.substring(1);
                    DBCentral db = new DBCentral();
                    System.out.println(id_local);
                    String ip_rsu = db.getIP_local(Integer.valueOf(id_local)); // endereco ip rsu destinatario

                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date datex = new java.util.Date();
                    String data = formatter.format(datex);
                    //java.util.Date myDate = formatter.parse(data);
                    //java.sql.Date dateAlert = new java.sql.Date(myDate.getTime());
                    String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                    System.out.println(timestamp);
                    System.out.println(Integer.valueOf(state));
                    System.out.println(ip_origem);
                    //System.out.println(ip_rsu);
                    System.out.println(Integer.valueOf(Integer.valueOf(id_local)));
                    System.out.println("Tipo de alerta:"+alertTp);
                    System.out.println(Integer.valueOf(id_user));

                    String res=null;
                    String trama_local=null;



                    if (alertTp.equals("Meteorologia")) {
                        identificador_alerta++;
                        res = db.insertAlert(timestamp, Integer.valueOf(state), ip_origem, Integer.valueOf(id_local), 1, Integer.valueOf(id_user));
                        trama_local =timestamp+","+ Integer.valueOf(state) +","+ip_origem +","+ip_rsu+","+Integer.valueOf(id_local)+","+alertTp+","+Integer.valueOf(id_user) +","+identificador_alerta;

                        for(int i=0;i<=2;i++){
                            System.out.println(outs.get(i));
                            send_to_local send = new send_to_local(outs.get(i), trama_local);
                        }
                    }
                    if (alertTp.equals("Obras")) {
                        identificador_alerta++;
                        res = db.insertAlert(timestamp, Integer.valueOf(state), ip_origem, Integer.valueOf(id_local), 2, Integer.valueOf(id_user));
                        trama_local =timestamp+","+ Integer.valueOf(state) +","+ip_origem +","+ip_rsu+","+Integer.valueOf(id_local)+","+alertTp+","+Integer.valueOf(id_user)+","+identificador_alerta;

                        if(ip_rsu.contains(Configuracao.ip1)){
                            new send_to_local(outs.get(0), trama_local);
                        }
                        if(ip_rsu.contains(Configuracao.ip2)){
                            new send_to_local(outs.get(1), trama_local);
                        }
                        if(ip_rsu.contains(Configuracao.ip3)){
                            new send_to_local(outs.get(2), trama_local);
                        }
                    }
                    if (alertTp.equals("Ambulancia")) {
                        identificador_alerta++;
                        res = db.insertAlert(timestamp, Integer.valueOf(state), ip_origem, Integer.valueOf(id_local), 3, Integer.valueOf(id_user));
                        trama_local =timestamp+","+ Integer.valueOf(state) +","+ip_origem +","+ip_rsu+","+Integer.valueOf(id_local)+","+alertTp+","+Integer.valueOf(id_user)+","+identificador_alerta;

                        if(ip_rsu.contains(Configuracao.ip1)){
                            new send_to_local(outs.get(0), trama_local);
                        }
                        if(ip_rsu.contains(Configuracao.ip2)){
                            new send_to_local(outs.get(1), trama_local);
                        }
                        if(ip_rsu.contains(Configuracao.ip3)){
                            new send_to_local(outs.get(2), trama_local);
                        }
                    }
                    if (alertTp.equals("Acidente")) {
                        identificador_alerta++;
                        res = db.insertAlert(timestamp, Integer.valueOf(state), ip_origem, Integer.valueOf(id_local), 4, Integer.valueOf(id_user));
                        trama_local =timestamp+","+ Integer.valueOf(state) +","+ip_origem +","+ip_rsu+","+Integer.valueOf(id_local)+","+alertTp+","+Integer.valueOf(id_user)+","+identificador_alerta;

                        if(ip_rsu.contains(Configuracao.ip1)){
                            new send_to_local(outs.get(0), trama_local);
                        }
                        if(ip_rsu.contains(Configuracao.ip2)){
                            new send_to_local(outs.get(1), trama_local);
                        }
                        if(ip_rsu.contains(Configuracao.ip3)){
                            new send_to_local(outs.get(2), trama_local);
                        }
                    }
                    if (alertTp.equals("Trânsito") || alertTp.equals("Transito")) {
                        identificador_alerta++;
                        res = db.insertAlert(timestamp, Integer.valueOf(state), ip_origem,Integer.valueOf(id_local), 5, Integer.valueOf(id_user));
                        trama_local =timestamp+","+ Integer.valueOf(state) +","+ip_origem +","+ip_rsu+","+Integer.valueOf(id_local)+","+alertTp+","+Integer.valueOf(id_user)+","+identificador_alerta;

                        if(ip_rsu.contains(Configuracao.ip1)){
                            new send_to_local(outs.get(0), trama_local);
                        }
                        if(ip_rsu.contains(Configuracao.ip2)){
                            new send_to_local(outs.get(1), trama_local);
                        }
                        if(ip_rsu.contains(Configuracao.ip3)){
                            new send_to_local(outs.get(2), trama_local);
                        }
                    }
                    out.println(res);
                }
                if (Tipo_trama.equals("editLocal")) {
                    String id_local2 = trama[1];
                    String name2 = trama[2];
                    // String coordx2 = trama[3];
                    //String coordy2 = trama[4];
                    String ip = trama[3];
                    String cover2 = trama[4];
                    String name = name2.substring(1);
                    String id_local = id_local2.substring(1);

                    String cover = cover2.substring(1);
                    DBCentral db = new DBCentral();
                    String resposta = db.updateLocal(Integer.valueOf(id_local), Integer.valueOf(cover), ip);
                    out.println(resposta);
                }

                if (Tipo_trama.equals("editUser")) {
                    String id2 = trama[1];
                    String name2 = trama[2];
                    String type2 = trama[3];
                    String usern2 = trama[5];
                    String email2 = trama[6];
                    String password2 = trama[6];

                    String address2 = trama[7];
                    String date_birth2 = trama[8];
                    String date_regi2 = trama[9];
                    String id = id2.substring(1);
                    String name = name2.substring(1);
                    String type = type2.substring(1);
                    String usern = usern2.substring(1);
                    String email = email2.substring(1);
                    String password = password2.substring(1);

                    String address = address2.substring(1);
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date myDate = formatter.parse(date_birth2);
                    java.sql.Date date_b = new java.sql.Date(myDate.getTime());
                    java.util.Date datereg = formatter.parse(date_regi2);
                    java.sql.Date date_r = new java.sql.Date(datereg.getTime());
                    DBCentral db = new DBCentral();
                    String res = db.updateUser(Integer.valueOf(id), name, Integer.valueOf(type), usern, email, password, address, date_b, date_r);
                    out.println(res);
                }

                if (Tipo_trama.equals("editAlerta")) {

                    System.out.println(Arrays.toString(trama));
                    String id_alerta2 = trama[1];
                    String index = trama[2];
                    String date2 = trama[3];
                    String state2 = trama[4];
                    String ip_origem2 = trama[5];
                    //String ip_rsu2 = trama[6];
                    String local2 = trama[6];
                    String tipo = trama[7];
                    String user2 = trama[8];
                    System.out.println(user2);
                    String id_alerta = id_alerta2.substring(1);
                    String index2 = index.substring(1);
                    String date = date2.substring(1);
                    //String hora = hora2.substring(1);
                    String state = state2.substring(1);
                    String ip_origem = ip_origem2.substring(1);
                    //String ip_rsu = ip_rsu2.substring(1);
                    String id_local = local2.substring(1);
                    String tipo2 = tipo.substring(1);

                    //String alertTp = id_alertTp2.substring(1);
                    String user = user2.substring(1);
                    DBCentral db = new DBCentral();
                    String timestamp = date;
                    //String timestamp = date +" "+hora;
                    System.out.println(timestamp);

                    //String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datahora);
                    String ip_destino = db.getIP_local(Integer.valueOf(id_local));
                    System.out.println("Valor do tipo: "+tipo2);
                    String resposta = null;
                    String alerta = id_alerta+","+timestamp+","+index2+","+ip_origem+","+id_local+","+tipo2+","+user;
                    if (tipo2.equals("Meteorologia")) {
                        resposta = db.updateAlert(Integer.valueOf(id_alerta), timestamp, Integer.valueOf(state), ip_origem, Integer.valueOf(id_local), 1, Integer.valueOf(user));

                        for(int i=0;i<=2;i++){
                            if(in!=ins.get(i)) {
                                send_to_local send = new send_to_local(outs.get(i), alerta);
                            }
                        }
                    }
                    else {

                        if (tipo2.equals("Obras")) {
                            resposta = db.updateAlert(Integer.valueOf(id_alerta), timestamp, Integer.valueOf(state), ip_origem, Integer.valueOf(id_local), 2, Integer.valueOf(user));
                        }
                        if (tipo2.equals("Ambulancia")) {
                            resposta = db.updateAlert(Integer.valueOf(id_alerta), timestamp, Integer.valueOf(state), ip_origem, Integer.valueOf(id_local), 3, Integer.valueOf(user));
                        }
                        if (tipo2.equals("Acidente")) {
                            resposta = db.updateAlert(Integer.valueOf(id_alerta), timestamp, Integer.valueOf(state), ip_origem, Integer.valueOf(id_local), 4, Integer.valueOf(user));
                        }
                        if (tipo2.equals("Trânsito")) {
                            resposta = db.updateAlert(Integer.valueOf(id_alerta), timestamp, Integer.valueOf(state), ip_origem, Integer.valueOf(id_local), 5, Integer.valueOf(user));
                        }

                        if (ip_destino.contains(Configuracao.ip1)) {
                            new send_to_local(outs.get(0), alerta);
                        }
                        if (ip_destino.contains(Configuracao.ip2)) {
                            new send_to_local(outs.get(1), alerta);
                        }
                        if (ip_destino.contains(Configuracao.ip3)) {
                            new send_to_local(outs.get(2), alerta);
                        }
                    }
                    out.println(resposta);
                }
                if (Tipo_trama.equals("rmLocal")) {
                    String idlocal2 = trama[1];
                    String idlocal = idlocal2.substring(1);
                    DBCentral db = new DBCentral();
                    String remove = db.removeLocal(Integer.valueOf(idlocal));
                }

                if (Tipo_trama.equals("rmUser")) {
                    String id2 = trama[1];
                    String id = id2.substring(1);
                    System.out.println(id);
                    DBCentral db = new DBCentral();
                    db.removeUser(Integer.valueOf(id));
                }
                if (Tipo_trama.equals("rmAlerta")) {
                    String id_alerta2 = trama[1];
                    String id_alerta = id_alerta2.substring(1);
                    DBCentral db = new DBCentral();
                    String resp = db.removeAlert(Integer.valueOf(id_alerta));

                    for(int i=0;i<=2;i++){
                        send_to_local send = new send_to_local(outs.get(i), id_alerta);
                    }
                    out.println(resp);
                }
                if (Tipo_trama.equals("viewUser")) {
                    String user2 = trama[1];
                    String user = user2.substring(1);
                    DBCentral db = new DBCentral();
                    String res = db.getUser(user);
                    out.println(res);
                }
                if (Tipo_trama.equals("viewAlerta")) {
                    String idalerta2 = trama[1];
                    String idalerta = idalerta2.substring(1);
                    DBCentral db = new DBCentral();
                    String alertas = db.getAlert(Integer.valueOf(idalerta));
                    out.println(alertas);
                }

                if (Tipo_trama.equals("viewLocal")) {
                    String idlocal2 = trama[1];
                    String idlocal = idlocal2.substring(1);
                    DBCentral db = new DBCentral();
                    String respo = db.getLocalCity(Integer.valueOf(idlocal));
                    out.println(respo);
                }


                if (Tipo_trama.equals("viewlocals")) {
                    DBCentral db = new DBCentral();
                    ArrayList<String> locals = db.getLocalCities();
                    out.println(locals);
                }


                if (Tipo_trama.equals("Login")) {
                    //ler a tensao
                    String email_raw = trama[1];
                    String pass_raw = trama[2];
                    String email = email_raw.substring(1);
                    String pass = pass_raw.substring(1);
                    System.out.println("email Trama:" + email);
                    System.out.println("password" + pass);
                    DBCentral db = new DBCentral();
                    Boolean res = db.login(email,pass);
                    out.println(String.valueOf(res));
                    //int resultado = db.getUser(email,pass);
                    // System.out.println("Resultado:" + resultado);


                }



                if (Tipo_trama.equals("viewUsers")) {
                    DBCentral db = new DBCentral();
                    ArrayList<String> Users = db.getUsers();
                    out.println(Users);
                }


                if (Tipo_trama.equals("viewAlertas")) {
                    DBCentral db = new DBCentral();
                    ArrayList<String> alertas = db.getAlerts();
                    out.println(alertas);

                }


            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
