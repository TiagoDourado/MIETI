
public class Packet{
    private int id;
    private int ttl;
    private int hops;
    private String tipo;
    private String payload;
    private String dataehora;
    private int idlocal;

    public Packet(int id, int ttl, int hops, String tipo,String dataehora,int idlocal){
        this.id = id;
        this.ttl = ttl;
        this.hops = hops;
        this.tipo = tipo;
        this.dataehora = dataehora;
        this.idlocal = idlocal;
    }

    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id = id;

    }

    public int getTTL(){
        return ttl;
    }

    public void setTTL(int ttl){
        this.ttl = ttl;
    }

    public int getHops(){
        return hops;
    }

    public void setHops(int hops){
        this.hops = hops;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getDataehora(){
        return dataehora;
    }

    public void setDataehora(String dataehora){
        this.dataehora = dataehora;
    }

    public int getIDLocal(){
        return idlocal;
    }

    public void setIDlocal(int idlocal){
        this.idlocal = idlocal;
    }

    public String toString(){
        return id+","+ttl+","+hops+","+tipo+","+dataehora+","+idlocal;
    }


}