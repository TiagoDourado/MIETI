package pti.wifi_fingerprint;

import android.support.v7.app.AppCompatActivity;

public class Element extends AppCompatActivity {
    private String SSID;
    private int sinal;
    private String endereco_mac;

    public Element(){

    }

    public Element(String SSID, int sinal, String endereco_mac){
        this.SSID=SSID;
        this.sinal=sinal;
        this.endereco_mac=endereco_mac;
    }

    public String getSSID(){
        return this.SSID;
    }
    public  void setSSID(String SSID){
        this.SSID=SSID;
    }
    public int getSinal(){
        return this.sinal;
    }
    public void setSinal(int sinal){
        this.sinal=sinal;
    }
    public String getEndereco_mac(){
        return this.endereco_mac;
    }
    public void setEndereco_mac(String endereco_mac){
        this.endereco_mac=endereco_mac;
    }
}
