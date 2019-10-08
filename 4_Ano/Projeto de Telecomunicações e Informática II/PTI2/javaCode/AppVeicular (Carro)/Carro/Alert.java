import java.sql.Timestamp;

public class Alert{
    private String nome;
    private Timestamp time;
    private int estado;
    private String ip_origem;
    private String ip_rsu;
    
    public Alert(String nome, Timestamp time, int estado, String ip_origem, String ip_rsu){
        this.nome = nome;
        this.time = time;
        this.estado = estado;
        this.ip_origem = ip_origem;
        this.ip_rsu = ip_rsu;
    }

    public String getNome(){
        return nome;
    }

    public void SetNome(String nome){
        this.nome = nome;
    }

    public Timestamp getTime(){
        return time;
    }

    public void setTime(Timestamp time){
        this.time = time;
    }

    public int getEstado(){
        return estado;
    }

    public void setEstado(int estado){
        this.estado = estado;
    }

    public String getIp_Origem(){
        return ip_origem;
    }

    public void setIp_Origem(String ip_origem){
        this.ip_origem = ip_origem;
    }

    public String getIp_Rsu(){
        return ip_rsu;
    }

    public void SetIp_Rsu(String ip_rsu){
        this.ip_rsu = ip_rsu;
    }

    public String toString(){
        return nome+","+time+","+estado+","+ip_origem+","+ip_rsu;
    }
}