package pti.wifi_fingerprint;

import android.support.v7.app.AppCompatActivity;

public class element_espaco extends AppCompatActivity {
    private String nome_espaco;
    private String nome_dono;

    public element_espaco(String nome_espaco,String nome_dono){//,String nome_dono){
        this.nome_dono=nome_dono;
        this.nome_espaco=nome_espaco;
    }


    public String getNome_espaco(){
        return this.nome_espaco;
    }

    public void setNome_espaco(String nome_espaco){
        this.nome_espaco=nome_espaco;
    }

    public String getNome_dono(){
        return this.nome_dono;
    }

    public void setNome_dono(String nome_dono){
        this.nome_dono=nome_dono;
    }

}
