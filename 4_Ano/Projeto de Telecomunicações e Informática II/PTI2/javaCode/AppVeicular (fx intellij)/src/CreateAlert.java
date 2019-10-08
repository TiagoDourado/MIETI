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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

 public class CreateAlert{

  //Criação de um Alerta pelo Carro
  public static String newAlert() throws IOException{
    String meteopayload = null;
    String obrapayload = null;
    String ambpayload = null;
    String acidpayload = null;
    String transitopayload = null;
    String timestamp = null;
    ValidationTTL ativateTTL;
    
    BufferedReader req = new BufferedReader(new InputStreamReader(System.in));   
    BufferedReader res = new BufferedReader(new InputStreamReader(System.in));
    
    Cache cache = Cache.getInstance();
    
    System.out.println("Tipo de Alerta que deseja adicionar: ");
    String tipo = cache.get_Constante_to_Tipo("1");
    //String inp = req.readLine();
    String inp = tipo;

    String s = null;
    int id = 0;
    int sequencia_mostraralertar = cache.get_sequence_number();
    int idc = Integer.valueOf(cache.get_Constante_to_local("1"));
    int idlocal = idc;
    System.out.println(idlocal);
    System.out.println(tipo);
    if(inp != null){
        id++;

        switch(inp){
          case ReadConfigFile.METEOROLOGIA:
            Packet pck = new Packet(id,ReadConfigFile.meteottl,ReadConfigFile.meteohops,ReadConfigFile.METEOROLOGIA,timestamp,idlocal);
            pck.setTipo(ReadConfigFile.METEOROLOGIA);
            pck.setID(id);
            pck.setTTL(ReadConfigFile.meteottl);
            ativateTTL = new ValidationTTL(ReadConfigFile.meteottl,cache);
            Thread ative = new Thread(ativateTTL);
            ative.start();
            pck.setHops(ReadConfigFile.meteohops);

            timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            pck.setDataehora(timestamp);
            cache.add_ID_to_dataehora(String.valueOf(id),timestamp);
            cache.add_ID_to_local(String.valueOf(id),String.valueOf(idlocal));
            cache.add_ID_to_ttl(String.valueOf(id),String.valueOf(pck.getTTL()));
            cache.add_ID_to_Hops(String.valueOf(id),String.valueOf(pck.getHops()));

            pck.setIDlocal(idlocal);
            System.out.println("Pacote "+pck.toString());
            s = pck.toString();
            break;

            case ReadConfigFile.OBRAS:

            Packet pckt = new Packet(id,ReadConfigFile.obrattl,ReadConfigFile.obrahops,ReadConfigFile.OBRAS,timestamp,idlocal);
            
            pckt.setTipo(ReadConfigFile.OBRAS);
            pckt.setID(id);
            pckt.setTTL(ReadConfigFile.obrattl);
            ativateTTL = new ValidationTTL(ReadConfigFile.meteottl,cache);
            Thread ative2 = new Thread(ativateTTL);
            ative2.start();
            pckt.setHops(ReadConfigFile.obrahops);

              timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            pckt.setDataehora(timestamp);
            pckt.setIDlocal(idlocal);
            pckt.toString();
              cache.add_ID_to_Hops(String.valueOf(id),String.valueOf(pckt.getHops()));
              cache.add_ID_to_ttl(String.valueOf(id),String.valueOf(pckt.getTTL()));
              cache.add_ID_to_dataehora(String.valueOf(id),timestamp);
              cache.add_ID_to_local(String.valueOf(id),String.valueOf(idlocal));

              System.out.println("Pacote " + pckt.toString());
            s = pckt.toString();
            break;

            case ReadConfigFile.AMBULANCIA:
            Packet pack = new Packet(id,ReadConfigFile.ambttl,ReadConfigFile.ambhops,ReadConfigFile.AMBULANCIA,timestamp,idlocal);
            pack.setID(id);
            pack.setTipo(ReadConfigFile.AMBULANCIA);        
            pack.setTTL(ReadConfigFile.ambttl);
            ativateTTL = new ValidationTTL(ReadConfigFile.ambttl,cache);
            Thread ative3 = new Thread(ativateTTL);
            ative3.start();
            cache.add_ID_to_ttl(String.valueOf(id),String.valueOf(pack.getTTL()));
            pack.setHops(ReadConfigFile.ambhops);
            cache.add_ID_to_Hops(String.valueOf(id),String.valueOf(pack.getHops()));
            timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            pack.setDataehora(timestamp);
            cache.add_ID_to_dataehora(String.valueOf(id),timestamp);
              cache.add_ID_to_local(String.valueOf(id),String.valueOf(idlocal));

              pack.setIDlocal(idlocal);
            System.out.println("Pacote " + pack.toString());
            s = pack.toString();
            break;

            case ReadConfigFile.ACIDENTE:
            Packet packt = new Packet(id, ReadConfigFile.acidttl,ReadConfigFile.acidhops,ReadConfigFile.ACIDENTE,timestamp,idlocal);
            packt.setTipo(ReadConfigFile.ACIDENTE);
            packt.setID(id);
            packt.setTTL(ReadConfigFile.acidttl);
            ativateTTL = new ValidationTTL(ReadConfigFile.meteottl,cache);
            Thread ative4 = new Thread(ativateTTL);
            ative4.start();
            cache.add_ID_to_ttl(String.valueOf(id),String.valueOf(packt.getTTL()));
            packt.setHops(ReadConfigFile.acidhops);
            
            cache.add_ID_to_Hops(String.valueOf(id),String.valueOf(packt.getHops()));
            cache.add_ID_to_local(String.valueOf(id),String.valueOf(idlocal));
              timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            packt.setDataehora(timestamp);
            cache.add_ID_to_dataehora(String.valueOf(id),timestamp);
            packt.setIDlocal(idlocal);
            packt.toString();
            System.out.println("Pacote" + packt.toString());
            s =  packt.toString();
            break;

            case ReadConfigFile.TRANSITO:
            Packet pacote = new Packet(id, ReadConfigFile.transitottl, ReadConfigFile.transitohops,ReadConfigFile.TRANSITO,timestamp,idlocal);
            pacote.setTipo(ReadConfigFile.TRANSITO);
            pacote.setID(id);
            pacote.setTTL(ReadConfigFile.transitottl);
            ativateTTL = new ValidationTTL(ReadConfigFile.meteottl,cache);
            Thread ative5 = new Thread(ativateTTL);
            ative5.start();
            cache.add_ID_to_ttl(String.valueOf(id),String.valueOf(pacote.getTTL()));
            pacote.setHops(ReadConfigFile.transitohops);
            cache.add_ID_to_Hops(String.valueOf(id),String.valueOf(pacote.getHops()));
            cache.add_ID_to_local(String.valueOf(id),String.valueOf(idlocal));
            timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            pacote.setDataehora(timestamp);
            cache.add_ID_to_dataehora(String.valueOf(id),timestamp);
            pacote.setIDlocal(idlocal);
            pacote.toString();
            System.out.println("Pacote " + pacote.toString());
            s = pacote.toString();
            break;
        }
      
   }

    cache.add_sequence_number(id);
    System.out.println("seq_number: " + cache.get_sequence_number());
  
  
   return s;
}


}

  