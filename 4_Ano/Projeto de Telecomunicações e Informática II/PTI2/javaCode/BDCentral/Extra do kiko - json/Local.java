import org.json.simple.JSONObject;

public class Local{
    private final int id;
    private final String nome;
    private final int coordx;
    private final int coordy;
    private final String ip;
    private final int cover;

    public Local(int id, String nome, int x, int y, String ip, int var){
        this.id = id;
        this.nome = nome;
        this.coordx = x;
        this.coordy = y;
        this.ip = ip;
        this.cover = var;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public int getCoordX(){
        return this.coordx;
    }

    public void setCoordX(int x){
        this.coordx = x;
    }

    public int getCoordY(){
        return this.coordy;
    }

    public void setCoordY(int y){
        this.coordy = y;
    }

    public String getIp(){
        return this.ip;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public int getCover(){
        return this.cover;
    }

    public void setCover(int var){
        this.cover = var;
    }

    public String toString(){
        return "{" +
                "id_localCity = " + id +
                ", nome = '" + nome + '\'' +
                ", coordx = " + coordx +
                ", coordy = " + coordy +
                ", ip = '" + ip + '\'' +
                ", cover = " + cover +
                '}';
    }

   public JSONObject getJSONObject(){
       JSONObject tmp = new JSONObject();
       tmp.put("id_localCity",getId());
       tmp.put("nome",getNome());
       tmp.put("coordx",getCoordX());
       tmp.put("coordy",getCoordY());
       tmp.put("ip",getIp());
       tmp.put("cover",getCover());

       return tmp;
   }

    
}