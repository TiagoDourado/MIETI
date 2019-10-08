import java.sql.Date;
import org.json.simple.JSONObject;

public class User{
    private final int id;
    private final String nome;
    private final int type;
    private final String token;
    private final String username;
    private final String password;
    private final String email;
    private final String address;
    private final Date birthday;
    private final Date date_reg;
    
    public User(int id, String nome, int tipo, String token, String usern, String passw, String mail, String rua, Date birth, Date reg){
        this.id = id;
        this.nome = nome;
        this.type = tipo;
        this.token = token;
        this.username = usern;
        this.password = passw;
        this.email = mail;
        this.address = rua;
        this.birthday = birth;
        this.date_reg = reg;
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

    public int getType(){
        return this.type;
    }

    public void setType(int tipo){
        this.type = tipo;
    }

    public String getToken(){
        return this.token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String usern){
        this.username = usern;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String passw){
        this.password = passw;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String mail){
        this.email = mail;
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String rua){
        this.address = rua;
    }

    public Date getBirthday(){
        return this.birthday;
    }

    public void setBirthday(Date birth){
        this.birthday = birth;
    }

    public Date getDateRegistration(){
        return this.date_reg;
    }

    public void setDateRegistration(Date reg){
        this.date_reg = reg;
    }

    public String toString(){
        return "{" +
                "id_user " + id +
                ", nome = '" + nome + '\'' +
                ", typeUser = " + type + 
                ", token = '" + token + '\'' +
                ", username = '" + username  + '\'' +
                ", password = '" + password + '\'' +
                ", email = '" + email + '\'' + 
                ", address = '" + address + '\'' +
                ", birthday = '" + birthday + '\'' + 
                ", date_registration = '" + date_reg +
                '}';
    }

    public JSONObject getJsonObject(){
        JSONObject tmp = new JSONObject();
        tmp.put("id_user",getId());
        tmp.put("nome",getNome());
        tmp.put("typeUser",getType());
        tmp.put("token",getToken());
        tmp.put("username",getUsername());
        tmp.put("password",getPassword());
        tmp.put("email",getEmail());
        tmp.put("address",getAddress());
        tmp.put("birthday",getBirthday());
        tmp.put("date_registration",getDateRegistration());

        return tmp;
    }
}