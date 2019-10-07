package pti.wifi_fingerprint;

import android.support.v7.app.AppCompatActivity;

public class element_utilizadores extends AppCompatActivity {
    private String nome_utilizador;
    private String tipo;

    public element_utilizadores(String nome_utilizador,String tipo){//,String nome_dono){
        this.nome_utilizador=nome_utilizador;
        this.tipo=tipo;
    }


    public String getNome_utilizador(){
        return this.nome_utilizador;
    }

    public void setNome_utilizador(String nome_utilizador){
        this.nome_utilizador=nome_utilizador;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
