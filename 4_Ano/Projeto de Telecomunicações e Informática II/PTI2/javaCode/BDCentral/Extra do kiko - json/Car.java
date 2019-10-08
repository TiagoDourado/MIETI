import org.json.simple.JSONObject;

public class Car{
    private int id;
    private String brand;
    private String registration;
    private int coordx;
    private int coordy;
    private String ip;
    private int local;
    private int user;
    private int alert;

    public Car(int id, String marca, String reg, int x, int y, String ip, int local, int user, int alerta){
        this.id = id;
        this.brand = marca;
        this.registration = reg;
        this.coordx = x;
        this.coordy = y;
        this.ip = ip;
        this.local = local;
        this.user = user;
        this.alert = alerta;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getBrand(){
        return this.brand;
    }

    public void setBrand(String marca){
        this.brand = marca;
    }

    public String getRegistration(){
        return this.registration;
    }

    public void setRegistration(String reg){
        this.registration = marca;
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

    public int getLocal(){
        return this.local;
    }

    public void setLocal(int local){
        this.local = local;
    }

    public int getUser(){
        return this.user;
    }

    public void setUser(int user){
        this.user = user;
    }

    public int getAlerta(){
        return this.alert;
    }

    public void setAlerta(int alerta){
        this.alert = alerta;
    }

    public String toString(){
        return "{" +
                "id_car = " + id + 
                ", brand = '" + brand + '\'' +
                ", registration = '" + registration + '\'' +
                ", coordx = " + coordx +
                ", coordy = " + coordy +
                ", ip = '" + ip + '\'' +
                ", id_localCity = " + local +
                ", id_user = " + user +
                ", id_alert = " + alert +
                '}';
    }

    public JSONObject getJsonObject(){
        JSONObject tmp = new JSONObject();
        tmp.put("id_car",getId());
        tmp.put("brand",getBrand());
        tmp.put("registration",getRegistration());
        tmp.put("coordx",getCoordX());
        tmp.put("coordy",getCoordY());
        tmp.put("ip",getIp());
        tmp.put("id_localCity",getLocal());
        tmp.put("id_user",getUser());
        tmp.put("id_alert",getAlerta());

        return tmp;
    }
}