
import java.util.ArrayList;

public final class Hello {

    private String Hello="Hello";
    private String IP;

    private ArrayList<String> trama_hello=new ArrayList<String>();

    Hello(String ip){
        this.IP=ip;
        trama_hello.add(Hello);
        trama_hello.add(IP);
    }



    public ArrayList<String> getTrama_hello() {
        return trama_hello;
    }
}
