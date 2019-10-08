import java.util.HashMap;
import java.util.Map;


public class Cache {
    private int sequence_number;
    private Map<String,String> ID_to_Destination = new HashMap<String, String>();
    private Map<String,String> ID_to_ttl = new HashMap<String,String>();
    private Map<String,String> ID_to_Hops = new HashMap<String,String>();
    private Map<String,String> ID_to_Local = new HashMap<String,String>();
    private Map<String,String> ID_to_dataehora = new HashMap<String,String>();
    private Map<String,String> ID_to_ip = new HashMap<String,String>();
    private Map<String,String> ID_to_Tipo = new HashMap<String,String>();
    private Map<String,String> Constante_to_Tipo = new HashMap<String,String>();
    private Map<String,String> Constante_to_local= new HashMap<String,String>();
    String x;
    private Map<String, String> RecebidoID_to_ttl = new HashMap<String, String>();
    private Map<String, String> RecebidoID_to_Hops = new HashMap<String, String>();
    private Map<String, String> RecidoID_to_Local = new HashMap<String, String>();


    private HashMap<String,Integer> estado_alerta = new HashMap<String,Integer>();  //alertas enviados para o RSU tem estado 1, senao 0

    int lenght;

    private static Cache instance;


    public static Cache getInstance() {
        if (instance==null){
            instance = new Cache();
        }
        return instance;

    }

    private Cache() {
    }



    public void rm_all (String ID){
        ID_to_Destination.remove(ID);
        ID_to_ttl.remove(ID);
        ID_to_Hops.remove(ID);
        ID_to_Local.remove(ID);
        ID_to_dataehora.remove(ID);
        ID_to_ip.remove(ID);
        ID_to_Tipo.remove(ID);
    }
    public void  setlenght(int l){
        this.lenght=l;
    }
    public int lenght (){
        return this.lenght;
    }
    public String getX() {
        String y = x;
        return y;

    }

    public void setX(String y) {
        this.x = x + y;
    }

    public void add_RecebidoID_to_dataehora(String ID, String local) {
        RecebidoID_to_ttl.put(ID, local);
    }

    public String get_RecebidoID_to_dataehora(String ID) {
        String local_new = RecebidoID_to_ttl.get(ID);
        return local_new;
    }

    public void add_RecebidoID_to_tipo(String ID, String local) {
        RecebidoID_to_Hops.put(ID, local);
    }

    public String get_RecebidoID_to_tipo(String ID) {
        String local_new = RecebidoID_to_Hops.get(ID);
        return local_new;
    }

    public void add_RecidoID_to_Local(String ID, String local) {
        RecidoID_to_Local.put(ID, local);
    }

    public String get_RecidoID_to_Local(String ID) {
        String local_new = RecidoID_to_Local.get(ID);
        return local_new;
    }
    ////////////////////////////LOCAL ///////////

    public void add_ID_to_local(String ID, String local){
        ID_to_Local.put(ID,local);
    }

    public String get_ID_to_local (String ID){
        String local_new = ID_to_Local.get(ID);
        return local_new;
    }
    public Map<String,String> get_ID_to_local_all(){
        return ID_to_Local;
    }

    public void rm_ID_to_local (String ID){
        ID_to_Local.remove(ID);
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

    ////////////////////////////// constantente e local////////////////////////
    public void add_Constante_to_local(String consta, String tipo){
        Constante_to_local.put("1",tipo);
    }

    public String get_Constante_to_local(String ID){
        String tipo = Constante_to_local.get("1");
        return tipo;
    }

    public void rm_Constante_to_local(String ID){
        Constante_to_local.remove(ID);

    }
    ////////////////////////////// constantente e tipo////////////////////////
    public void add_Constante_to_Tipo(String consta, String tipo){
        Constante_to_Tipo.put("1",tipo);
    }

    public String get_Constante_to_Tipo (String ID){
        String tipo = Constante_to_Tipo.get("1");
        return tipo;
    }

    public void rm_Constante_to_Tipo (String ID){
        Constante_to_Tipo.remove(ID);

    }

    ///////////////////////////ttl/////////////////////

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

    public String get_allTTL(){
        String ttl = ";";
        for (String key: ID_to_ttl.keySet()) {
            System.out.println("ID: " + key);
            System.out.println("TTL: " +  ID_to_ttl.get(key));
            ttl = "ID:"+key+"TTL:"+ID_to_ttl.get(key)+"\n";


        }
        return ttl;

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
    /*public String get_AllHops(){

    }*/

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

////////////////////////////////////////tipo//////////////////////////////////////////////
    public void add_ID_to_Tipo(String ID, String tipo){
        ID_to_Tipo.put(ID,tipo);
    }

    public String get_ID_to_Tipo(String ID){
        String tiponew = ID_to_Tipo.get(ID);
        return tiponew;
    }

    public Map<String,String> get_ID_to_tipo_all(){
        return this.ID_to_Tipo;
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




    
}