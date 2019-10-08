import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;


public class cache_car {
    private int sequence_number;
    private Map<String,String> ID_to_Destination = new HashMap<String, String>();
    private Map<String,String> ID_to_ttl = new HashMap<String,String>();
    private Map<String,String> ID_to_Hops = new HashMap<String,String>();
    private Map<String,String> ID_to_Payload = new HashMap<String,String>();
    private Map<String,String> ID_to_Prioridade = new HashMap<String,String>();
    private Map<String,String> ID_to_dataehora = new HashMap<String,String>();
    private Map<String,String> ID_to_ip = new HashMap<String,String>();

    private HashMap<String,Integer> estado_alerta = new HashMap<String,Integer>();  //alertas enviados para o RSU tem estado 1, senao 0


    private static cache_car instance;


    public static cache_car getInstance() {
        if (instance==null){
            instance = new cache_car();
        }
        return instance;

    }

    private cache_car() {
    }

    public void rm_all (String ID){
        ID_to_Destination.remove(ID);
        ID_to_ttl.remove(ID);
        ID_to_Hops.remove(ID);
        ID_to_Payload.remove(ID);
        ID_to_Prioridade.remove(ID);
        ID_to_dataehora.remove(ID);
        ID_to_ip.remove(ID);
    }
    /////////////////////////SEQ NUMBER ///////////////////
    public void add_sequence_number(int sequence_number_new){
        this.sequence_number = sequence_number_new;
    }
    public int get_sequence_number () { 
        return sequence_number;
    }

    ///adicionei isto
    public HashMap<String,Integer> getEstado_alerta(){
        return this.estado_alerta;
    }

    public void add_estado_alerta(String alerta,int estado){
        estado_alerta.put(alerta,estado);
    }

    public void replace_estado_alerta(String alerta,int estado){
        for(String s : estado_alerta.keySet()){
            if(s.equals(alerta)){
                estado_alerta.replace(s, 0, estado);
            }
        }
    }

    //////////////////////


    ////////////////////////////// DESTINATION////////////////////////
    public void add_ID_to_Destination(String ID, String Destination){
        ID_to_Destination.put(ID,Destination);
    }

    public String get_ID_to_Destination (String ID){
        String destination_new = ID_to_Destination.get(ID);
        return destination_new;
    }
    
    public void rm_ID_to_Destination (String ID){
        ID_to_Destination.remove(ID);
        
    }

    ///////////////////////////ttl/////////////////////

    public void add_ID_to_ttl(String ID, String ttl){
        ID_to_ttl.put(ID,ttl);
    }

    public String get_ID_to_ttl (String ID){
        String ttl_new = ID_to_ttl.get(ID);
        return ttl_new;
    }
    public Map<String,String> get_ID_to_ttl_all(){
        return ID_to_ttl;
    }
    
    public void rm_ID_to_ttl (String ID){
        ID_to_ttl.remove(ID);
    }

    ////////////////////////////HOPS ///////////

    public void add_ID_to_Hops(String ID, String hops){
        ID_to_Hops.put(ID,hops);
    }

    public String get_ID_to_Hops (String ID){
        String hops_new = ID_to_Hops.get(ID);
        return hops_new;
    }
    public Map<String,String> get_ID_to_Hops_all(){
        return ID_to_Hops;
    }
    
    public void rm_ID_to_Hops (String ID){
        ID_to_Hops.remove(ID);
    }

    ///////////////////////////////////payload //////////////////////////

    public void add_ID_to_Payload(String ID, String payload){
        ID_to_Payload.put(ID,payload);
    }

    public String get_ID_to_Payload (String ID){
        String hops_new = ID_to_Payload.get(ID);
        return hops_new;
    }

    public Map<String,String> get_ID_to_Payload_all(){
        return this.ID_to_Payload;
    }

    public void rm_ID_to_Payload (String ID){
        ID_to_Payload.remove(ID);
    }
    //////////////////////////////// is empty /////////////////////////7

    public boolean destinationisEmpty(){
        return ID_to_Destination.isEmpty();
    }

    public boolean ttlisEmpty(){
        return ID_to_ttl.isEmpty();
    }

    public boolean hopsisEmpty(){
        return ID_to_Hops.isEmpty();
    }

    public boolean payloadisEmpty(){
        return ID_to_Payload.isEmpty();
    }

////////////////////////////////////////data e hora //////////////////////////////////
    public void add_ID_to_dataehora(String ID, String dataehora){
        ID_to_dataehora.put(ID,dataehora);
    }

    public String get_ID_to_dataehora (String ID){
        String dataehora_new = ID_to_dataehora.get(ID);
        return dataehora_new;
    }

    public Map<String,String> get_ID_to_dataehora_all(){
        return this.ID_to_dataehora;
    }

    public void rm_ID_to_dataehora(String ID){
        ID_to_dataehora.remove(ID);
    }


/////////////////////////PRIORIDADE ///////////////////
public Map<String,String> getPrioridade(){
    return this.ID_to_Prioridade;
}
////////////////////////////IP ///////////////////////////////////////////////////////
public void add_ID_to_ip(String ID, String ip){
    ID_to_ip.put(ID,ip);
}

public String get_ID_to_ip(String ID){
    String ip_new = ID_to_ip.get(ID);
    return ip_new;
}

public Map<String,String> get_ID_to_ip(){
    return this.ID_to_ip;
}

public void rm_ID_to_ip(String ID){
    ID_to_ip.remove(ID);
}


///////////////////////////////////////////////////////////////////

    public void add_ID_to_Prioridade(String tipo_alerta){
        if(tipo_alerta.equals("addMeteorologia")){
            ID_to_Prioridade.put("1",tipo_alerta);
        }
        if(tipo_alerta.equals("addAmbulancia")){
            ID_to_Prioridade.put("4",tipo_alerta);
        }
        if(tipo_alerta.equals("addObras")){
            ID_to_Prioridade.put("2",tipo_alerta);
        }
        if(tipo_alerta.equals("addAcidente")){
            ID_to_Prioridade.put("3",tipo_alerta);
        }
    }


    
}