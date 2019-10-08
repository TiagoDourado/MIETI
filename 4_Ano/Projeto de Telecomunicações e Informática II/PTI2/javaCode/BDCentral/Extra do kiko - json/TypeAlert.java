import org.json.simple.JSONObject;

public class TypeAlert{
    private final int id_typealert;
    private final String type;
    private final int alert;

    public TypeAlert(int id, String tipo, int alerta){
        this.id_typealert = id;
        this.type = tipo;
        this.alert = alerta;
    }

    public int getId(){
        return this.id_typealert;
    }

    public void setId(int id){
        this.id_typealert = id;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String tipo){
        this.type = tipo;
    }

    public int getAlert(){
        return this.alert;
    }

    public void setAlert(int alerta){
        this.alert = alerta;
    }

    public String toString(){
        return "{" + 
                "id_alertType = " + id_typealert + 
                ", alertTp '" + type + '\'' + 
                ", id_alert = " + alert +
                '}';
    }

    public JSONObject getJsonObject(){
        JSONObject tmp = new JSONObject();
        tmp.put("id_alertType",getId());
        tmp.put("alertTp",getType());
        tmp.put("id_alert",getAlert());

        return tmp;
    }


}