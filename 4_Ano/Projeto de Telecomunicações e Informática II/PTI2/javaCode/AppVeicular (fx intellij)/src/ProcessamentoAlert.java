import java.io.BufferedReader;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.lang.Runnable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class ProcessamentoAlert {
    static int id ;
    static int ttl2;
    static int hop;
    static String tipo;
    static String dataehora;
    static int idlocal;
    static Cache cache = null;
    static int key=0;
    public static void alertParse(String t) throws IOException{
        Packet newPacket = new Packet(id,ttl2,hop,tipo,dataehora,idlocal);
        
        String[] al = t.split(",");
        System.out.println("al: " + Arrays.toString(al));
        String identifier = al[0].trim();

        if(!identifier.equals("hello")){

            cache = cache.getInstance();
            String idr = al[0];
            id = Integer.valueOf(idr);
            newPacket.setID(id);
          
            String ttl = al[1];
            ttl2 = Integer.valueOf(ttl);
            newPacket.setTTL(ttl2);
            
            String hops = al[2];
            hop = Integer.valueOf(hops);
            hop--;
            newPacket.setHops(hop);
            
           
            tipo = al[3];
            
            newPacket.setTipo(tipo);

            dataehora = al[4];
            newPacket.setDataehora(dataehora);
            idlocal = Integer.parseInt(al[5]);
            newPacket.setIDlocal(idlocal);
            String s = newPacket.toString();

            System.out.println("seq number: " + cache.get_sequence_number());



            if(! (cache.ttlisEmpty() && cache.hopsisEmpty())){
                System.out.println("Entrei");
                System.out.println(cache.get_ID_to_tipo_all());
                System.out.println(cache.get_ID_to_dataehora_all());
                Map<String,String> tipos = cache.get_ID_to_tipo_all();
                Map<String,String> timestamps = cache.get_ID_to_dataehora_all();
                for(String ktipos : tipos.keySet()){
                    for(String ktimestamps : timestamps.keySet()){
                        if(tipo == cache.get_ID_to_Tipo(ktipos) && dataehora == cache.get_ID_to_dataehora(ktimestamps)){
                           System.out.println("Alert already exists, dropped");

                        }

                        
                    }
                }
                cache.add_ID_to_ttl(idr,ttl);
                System.out.println("cache ttl: " + cache.get_ID_to_ttl(idr));
                concatena cd = new concatena();


                cache.add_ID_to_Hops(idr,String.valueOf(hop));
                cache.add_ID_to_Tipo(idr,tipo);
                cache.add_ID_to_dataehora(idr,dataehora);
                key ++;
                cache.setlenght(key);
                System.out.println("Key:****************"+key);
                cache.add_RecebidoID_to_tipo(String.valueOf(key),tipo);
                cache.add_RecebidoID_to_dataehora(String.valueOf(key),dataehora);
                cache.add_RecidoID_to_Local(String.valueOf(key),String.valueOf(idlocal));

                if(hop != 0){

                    System.out.println("NovoPacote: " + newPacket.toString());
                    ClientSender pcksender = new ClientSender(cache,null,s);
                    Thread pcktsender = new Thread(pcksender);
                    try{
                        pcktsender.sleep((int) (Math.random() * 5000));
                    }catch(InterruptedException e){
                        System.out.println("Error: "+e);
                    }
                    pcktsender.start();
                    }       
                else{
                    System.out.println("Pacote descartado");
                }
            }
        }
        else{
            
            System.out.println("Vou mandar o que tenho em cache");
            String tipos = cache.get_ID_to_tipo_all().toString();
            System.out.println("Os tipos de alertas que tenho são estes: " + tipos);
            String times = cache.get_ID_to_dataehora_all().toString();
            System.out.println("Os timestamps que tenho são estes: "+times);
            String ti = tipos + "," + times;
            ClientSender sendertipos = new ClientSender(cache,null,ti);
            Thread tsendertipos = new Thread(sendertipos);
            try{
                tsendertipos.sleep(1000);
            }catch(InterruptedException e){
                System.out.println("Error: " +e);
            }
            tsendertipos.start();

        }
    }
}