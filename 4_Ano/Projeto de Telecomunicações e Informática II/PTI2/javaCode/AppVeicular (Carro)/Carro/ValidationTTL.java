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
public class ValidationTTL implements Runnable{
    
    int timelive;
    Cache cache;
    private volatile boolean isRunning = true;
    public ValidationTTL(int timelive, Cache cache){
        this.timelive = timelive;
        this.cache = cache;
    }

    public void run(){
        while(isRunning){
            try{
                Thread.sleep(timelive*2000);
            }catch(InterruptedException e){
                System.out.println("Erro: " +e);
            }
            Map<String,String> ttls = cache.get_ID_to_ttl_all();
            for(String key : ttls.keySet()){
                if(cache.get_ID_to_ttl(key).equals(timelive)){
                    cache.rm_all(key);
                    isRunning = false;
                    System.out.println("pacote eliminado");
                    
                }
            }
            
           
        }

    }
    
   
}