
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Packet{
    private int blocksize;
    //private String src;
    private String dest;
    //private String rcf;
    private long ttl;
    private int hops;
    private String payload;

    private ArrayList<String> list = new ArrayList<>();

    //blockSize fica em stand-by
    Packet(ArrayList<String> list){ 
        //this.blocksize = blocksize;
        //this.src = source;
        
        //this.rcf = receivedfrom;

        /*
        this.dest = destination;
        this.ttl = ttl;
        this.hops = hops;
        this.payload = payload;  */

        this.list = list;
        this.payload = list.get(0);
        this.ttl = Long.parseLong(list.get(1));
        this.hops = Integer.parseInt(list.get(2));
    }
    /*Packet(String destination, long ttl, int hops, String payload){ 
        //this.blocksize = blocksize;
        //this.src = source;
        this.dest = destination;
        //this.rcf = receivedfrom;
        this.ttl = ttl;
        this.hops = hops;
        this.payload = payload;
    }*/

    public int getBlockSize(){
        return blocksize;
    }

    /*public String getSource(){
        return source;
    }*/

    public String getDestination(){
        return dest;
    }

    /*public String getReceivedFrom(){
        return receivedfrom;
    }*/

    public long getTtl(){
        return ttl;
    }

    public int getHops(){
        return hops;
    }

    public String getPayload(){
        return payload;
    }

    public boolean checkifExpired(){
        long expired = getTtl() - System.currentTimeMillis();
        long tp = TimeUnit.MILLISECONDS.toMinutes(expired);

        if(tp > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public JSONObject toJsonObject(){
        JSONObject Jsonpacket = new JSONObject();
        //Jsonpacket.put("BlockSize",getBlockSize());
        //Jsonpacket.put("Source",getSource());
        //Jsonpacket.put("Destination",getDestination());
        //Jsonpacket.put("ReceivedFrom",getReceivedFrom());
        Jsonpacket.put("TTL",getTtl());
        Jsonpacket.put("Hops",getHops());
        Jsonpacket.put("Payload",getPayload());
        return Jsonpacket;
    }

    public String toString(){
        JSONObject Jsonpacket = new JSONObject();
        //Jsonpacket.put("BlockSize",getBlockSize());
        //Jsonpacket.put("Source",getSource());
        //Jsonpacket.put("Destination",getDestination());
        //Jsonpacket.put("ReceivedFrom",getReceivedFrom());
        Jsonpacket.put("TTL",getTtl());
        Jsonpacket.put("Hops",getHops());
        Jsonpacket.put("Payload",getPayload());
        
        return Jsonpacket.toJsonString();
    }

}