
import java.awt.List;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class DBCentral {
    private Connection connec = null;
    public Statement st = null;
    public ResultSet rs = null;
    public PreparedStatement ps = null;
    private static final String SQLPath = "jdbc:mysql://localhost/RoadAlert?autoReconnect=true&useSSL=false&serverTimezone=Europe/Lisbon";
    private static final String SQLuser = "central";
    private static final String SQLpass = "password";

    public DBCentral() {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
//            connec = DriverManager.getConnection(SQLPath,SQLuser,SQLpass);
            connec = DriverManager.getConnection(SQLPath, SQLuser, SQLpass);
            connec.createStatement();
            System.out.println("Connection to Database");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }   */
    }

    public void resetDBCentral() {
        try {
            st = connec.createStatement();
            st.execute("drop database if exists RoadAlert");
            st.execute("create database RoadAlert");
            st.execute("use RoadAlert");

        } catch (SQLException e) {
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    public void createTablesCentral() {
        System.out.println("Creating tables of database....");
        try {
            //Tabela de Locais
            Statement st = connec.createStatement();
            String sqlLocalCity = "create table localCity" +
                    "(id_localCity int not null auto_increment," +
                    "nome varchar(255) not null," +
                    "ip varchar(255) not null," +
                    "cover int not null," +
                    "primary key (id_localCity))";
            System.out.println("BD local ok");
            st.executeUpdate(sqlLocalCity);
            //Tabela de Utilizadores

            st = connec.createStatement();
            String sqlUser = "create table utilizador" +
                    "(id_user int not null auto_increment," +
                    "nome varchar(255) not null," +
                    "typeUser int not null," +
                    "username varchar(255) not null," +
                    "email varchar(255) not null," +
                    "password varchar(255) not null," +
                    "address varchar(255) not null," +
                    "birthday date not null," +
                    "date_registration date not null," +
                    "primary key(id_user))";
            System.out.println("BD User ok");

            st.executeUpdate(sqlUser);


            //Tabela de Tipos de Alertas
            st = connec.createStatement();
            String sqlAlertType = "create table alertType" +
                    "(id_alertType int not null auto_increment," +
                    "alertTp varchar(255) not null," +
                    "primary key(id_alertType))";
            System.out.println("BD tipo alert ok");

            st.executeUpdate(sqlAlertType);

            //Tabela de Alertas
            st = connec.createStatement();
            String sqlAlert = "create table alert" +
                    "(id_alert int not null auto_increment," +
                    "dateAlert timestamp not null," +
                    "estado int," +
                    "ip_origem varchar(255) not null," +
                    "id_localCity int," +
                    "id_alertType int," +
                    "id_user int," +
                    "primary key(id_alert)," +
                    "foreign key(id_localCity) references localCity(id_localCity) on update cascade on delete cascade," +
                    "foreign key(id_alertType) references alertType(id_alertType) on update cascade on delete cascade," +
                    "foreign key(id_user) references utilizador(id_user) on update cascade on delete cascade)";
            System.out.println("BD Alerta ok");
            st.executeUpdate(sqlAlert);

        }catch(SQLException e){
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    public void populateDB() {
        System.out.println("Inserir RSUs existentes");
        try {
            String nome = "Lisboa";
            String ip = "127.0.0.2";
            int cover = 50;
            String lisboa = "INSERT INTO localCity(nome,ip,cover) values('" + nome + "','" + ip + "','" + cover + "')";
            ps = connec.prepareStatement(lisboa);
            ps.executeUpdate(lisboa);

            String nome2 = "Beja";
            String ip2 = "127.0.0.3";
            int var = 100;
            String beja = "INSERT INTO localCity(nome,ip,cover) values('" + nome2 + "','" + ip2 + "','" + var + "')";
            ps = connec.prepareStatement(beja);
            ps.executeUpdate(beja);

            String nome3 = "Santarém";
            String ip3 = "127.0.0.4";
            int var2 = 150;
            String santarem = "INSERT INTO localCity(nome,ip,cover) values('" + nome3 + "','" + ip3 +  "','" + var2 + "')";
            ps = connec.prepareStatement(santarem);
            ps.executeUpdate(santarem);


        }catch(SQLException e){
            System.out.println("Erro ao inserir locais: " +e);
        }
    }

    public void populateTypeAlert(){
        System.out.println("Inserir Tipo de Alerta");
        try{
            String meteo = "Meteorologia";
            String tipo1 = "INSERT INTO alertType(alertTp) values ('" + meteo + "')";
            ps = connec.prepareStatement(tipo1);
            ps.executeUpdate(tipo1);

            String obra = "Obras";
            String tipo2 = "INSERT INTO alertType(alertTp) values ('" + obra + "')";
            ps = connec.prepareStatement(tipo2);
            ps.executeUpdate(tipo2);

            String amb = "Ambulancia";
            String tipo3 = "INSERT INTO alertType(alertTp) values ('" + amb + "')";
            ps = connec.prepareStatement(tipo3);
            ps.executeUpdate(tipo3);

            String acid = "Acidente";
            String tipo4 = "INSERT INTO alertType(alertTp) values ('" + acid + "')";
            ps = connec.prepareStatement(tipo4);
            ps.executeUpdate(tipo4);

            String tran = "Trânsito";
            String tipo5 = "INSERT INTO alertType(alertTp) values ('" + tran + "')";
            ps = connec.prepareStatement(tipo5);
            ps.executeUpdate(tipo4);

        }catch(SQLException e){
            System.out.println("Erro populate type alerta: " +e);
        }
    }

    public void populateuser() throws ParseException {
        System.out.println("Inserir User Root");
        try {

            String nome = "root";
            int typeUser = 1;
            String username = "root";
            String email = "root";


            String password ="pass";
            String generatedSecuredPasswordHash = SCryptUtil.scrypt(password, 16, 16, 16);
            System.out.println(generatedSecuredPasswordHash);
            String address = "root";
            String birthday = "1993-11-20";
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date myDate =formatter.parse(birthday);
            java.sql.Date dateAlert= new java.sql.Date(myDate.getTime());

            String date_registration = "2019-05-23";
            java.util.Date myDate_regi =formatter.parse(date_registration);
            java.sql.Date date_registration_S= new java.sql.Date(myDate_regi.getTime());
            String root = "INSERT INTO utilizador(nome,typeUser,username,password,email,address,birthday,date_registration) values('" + nome + "','" + typeUser + "','" + username + "','" + generatedSecuredPasswordHash + "','" + email + "','" + address + "','" + dateAlert + "','" + date_registration_S + "')";
            ps = connec.prepareStatement(root);
            ps.executeUpdate(root);

        }catch(SQLException e){
            System.out.println("Error: " +e);
        }
    }

    //Get One User
    public String getUser(String id){
        System.out.println("Obter utilizador");
        String res = null;
        try {
            st = connec.createStatement();
            String query = "SELECT * FROM utilizador WHERE id_user = " + id;
            rs = st.executeQuery(query);
            while (rs.next()) {
                int idUser = rs.getInt("id_user");
                String nm = rs.getString("nome");
                int tipo = rs.getInt("typeUser");
                String user = rs.getString("username");
                String mail = rs.getString("email");
                String pass = rs.getString("password");
                String addr = rs.getString("address");
                Date birth = rs.getDate("birthday");
                Date reg = rs.getDate("date_registration");
                res = "id_user: " + idUser + "," + "nome: " + nm + "," + "typeUser: " + tipo + "," + "token: " + "username: "  + user + "," + "email: " + mail + "," + "address: " + addr + "," + "birthday: " + birth + "," + "date_registration: " + reg;
                System.out.println(res);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return res;
    }

    public String getIP_local(int id){
        String ip = null;

        try {
            st = connec.createStatement();
            String query = "SELECT ip from localCity where id_localCity = '" + id + " '";
            rs = st.executeQuery(query);
            while (rs.next()) {
                ip = rs.getString("ip");
                System.out.println(ip);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return ip;
    }

    //Get All Locals
    public ArrayList<String> getLocalCities() {
        System.out.println("Get All locals");
        ArrayList<String> myResponse = new ArrayList<>();
        try {
            st = connec.createStatement();
            String query = "SELECT * from localCity";
            rs = st.executeQuery(query);
            while (rs.next()) {
                System.out.println("here");
                String id_local = rs.getString("id_localCity");
                String name = rs.getString("Nome");
                String ip=rs.getString("ip");
                int coverage = rs.getInt("cover");
                String cover = String.valueOf(coverage);
                myResponse.add("ID do local:" +id_local);
                myResponse.add("Nome:" + name);
                myResponse.add("Endereço IP:" + ip);
                myResponse.add("Área de Convergência:" + cover);
                for(String res : myResponse){
                    System.out.println(res);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return myResponse;

    }


    //Get One Local by ID
    public String getLocalCity ( int id){
        System.out.println("Get One Local by " + id);
        String resposte = null;
        try {
            String sql = "SELECT * FROM localCity WHERE id_localCity = '" + id + " '";
            st = connec.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("Nome");
                String ip=rs.getString("ip");
                int coverage = rs.getInt("cover");
                resposte = "ID do local: " + id + "," + "Nome: " + name +  "," + "IP: " + ip + "," + "Área de Convergência: " + coverage;
                System.out.println(resposte);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return resposte;

    }
    //Post Local
    public String insertLocal (String nm, int cover, String ip){
        System.out.println("Post Local");
        String resposte = null;
        try {
            String query = "INSERT INTO localCity(nome,ip,cover) values('" + nm + "','" + ip + "','" + cover + "')";
            ps = connec.prepareStatement(query);
            ps.executeUpdate(query);
            resposte = "O novo local foi adicionado com sucesso.";
            System.out.println("Resposta: " + resposte);
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return resposte;
    }


    //Remove Local
    public String removeLocal ( int id_local){
        System.out.println("Remove local");
        String res = null;
        try {
            String query = "DELETE FROM localCity WHERE id_localCity = '" + id_local + " '";
            ps = connec.prepareStatement(query);
            ps.executeUpdate(query);
            res = "O local foi apagado com sucesso. ID:" + id_local;
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return res;

    }


    //Get One User
    public String login (String user, String pass){
        System.out.println("Iniciar sessão");
        String res = null;
        try {
            st = connec.createStatement();
            String usern = user;
            String passw = pass.substring(1);
            System.out.println(usern);
            System.out.println(passw);
            String query = "SELECT * FROM utilizador WHERE username = '" + usern + "'AND password = '" + passw + " '";
            rs = st.executeQuery(query);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        res = "ok";
        System.out.println(res);
        return res;
    }



    //Obter Utilizadores
    public ArrayList<String> getUsers () {
        System.out.println("Obter utilizadores");
        ArrayList<String> myResponse = new ArrayList<>();
        try {
            st = connec.createStatement();
            String query = "SELECT * from utilizador";
            rs = st.executeQuery(query);
            while (rs.next()) {
                int id_user = rs.getInt("id_user");
                String id_u = String.valueOf(id_user);
                String name = rs.getString("nome");
                int type = rs.getInt("typeUser");
                String tipo = String.valueOf(type);
                String username = rs.getString("username");
                String pass = rs.getString("password");
                String email = rs.getString("email");
                String address = rs.getString("address");
                Date birthday = rs.getDate("birthday");
                String birth = String.valueOf(birthday);
                Date date_registration = rs.getDate("date_registration");
                String date_r = String.valueOf(date_registration);
                myResponse.add("ID do utilizador:"+id_u);
                myResponse.add("Nome:"+name);
                myResponse.add("Tipo:"+ tipo);
                myResponse.add("Username:"+username);
                myResponse.add("Password:"+pass);
                myResponse.add("E-mail:"+email);
                myResponse.add("Morada:"+address);
                myResponse.add("Data de nascimento:"+birth);
                myResponse.add("Data de Registo:"+ date_r);
                for(String res: myResponse){
                    System.out.println(res);
                }

            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
        return myResponse;

    }


    //Registar um utilizador
    public String registerUser (String nm,int type, String usern, String email, String password, String address, Date birth, Date dateReg){
        System.out.println("Register User");
        String resposta = null;
        try {
            String sql = "INSERT INTO utilizador(nome,typeUser,username,email,password,address,birthday,date_registration) values ('" + nm + "','" + type + "','" + usern + "','" + email + "','" + password + "','" + address + "','" + birth + "','" + dateReg + "')";
            ps = connec.prepareStatement(sql);
            ps.executeUpdate(sql);
            resposta = "Um novo utilizador foi adicionado com sucesso.";
            System.out.println("response: " +resposta);
        } catch (SQLException e) {
            System.out.println("Error: " + e);
            resposta = "erro";

        }
        return resposta;


    }

    //Update Local
    public String updateLocal (int id_local, int cover, String ip){
        System.out.println("Update local");
        String resultado = null ;
        try {

            String query = "UPDATE localCity SET ip = " + " '" + ip + "'," +
                    "cover = " + cover + " WHERE id_localCity = " + id_local + " ";
            ps = connec.prepareStatement(query);
            ps.executeUpdate(query);
            resultado = "Local atualizado com sucesso.";
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return resultado;
    }


    //Update AlertType
    public String updateAlertType ( int id_alertType, int type, int id_alert){
        System.out.println("Update Alert Type");
        String res = null;
        try {
            String query = "UPDATE alertType SET alertTp = " + type + "," +
                    "id_alert = " + id_alert + " WHERE id_alertType = " + id_alertType  + " ";
            ps = connec.prepareStatement(query);
            ps.executeUpdate(query);
            res = "Tipo de Alerta Atulizado com sucesso:" + id_alertType;
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return res;
    }
    //Atualizar um utilizador
    public String updateUser ( int id_user, String nm,int type, String usern, String email, String pass, String address, Date birth, Date dateReg){
        System.out.println("Atualizar utilizador");
        String res = null;
        try {
            String query = "UPDATE utilizador SET nome = " + " '" + nm  + "'," +
                    "typeUser = " + type + "," +
                    "username = " + " '" + usern + "'," +
                    "email = " + " '" + email + "'," +
                    "password = " + " '" + pass + "'," +
                    "address = "  + " '" +address + "'," +
                    "birthday = " + " '" +birth + "'," +
                    "date_registration = " + " '" + dateReg +  " '" + " WHERE id_user = " + id_user + " ";
            ps = connec.prepareStatement(query);
            ps.executeUpdate(query);
            res = "Utilizador atualizado com sucesso";
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return res;
    }

    //Update Alert
    public String updateAlert (int id_alert,String dt, int state, String ip_origem, int local, int id_alerttype, int user) {
        System.out.println("Update Alert");
        String res = null;
        Timestamp timestamp = Timestamp.valueOf(dt);

        try {
            String query = "UPDATE alert SET dateAlert = " + " '"  + timestamp + "'," +
                    "estado = " + state + "," +
                    "ip_origem = " + " '" + ip_origem + "'," +
                    "id_localCity = "  + local + "," +
                    "id_alertType = " + id_alerttype + "," +
                    "id_user = " + user  + " WHERE id_alert = " + id_alert + " ";
            ps = connec.prepareStatement(query);
            ps.executeUpdate(query);
            res = "Alerta atualizado com sucesso.";
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return res;
    }

    //Remover um utilizador
    public String removeUser ( int id_user){
        System.out.println("Delete user");
        String res = null;
        try {
            String query = "DELETE FROM utilizador WHERE id_user = '" + id_user + " '";
            ps = connec.prepareStatement(query);
            ps.executeUpdate(query);
            res = "Utilizador removido com sucesso.";
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return res;
    }

    //Get One Alert
    public String getAlert ( int id_alert){
        System.out.println("Obter Alert %d" + id_alert);
        String resposta = null;

        try {
            st = connec.createStatement();
            String query = "SELECT * from alert WHERE id_user = '" + id_alert + " '";
            rs = st.executeQuery(query);
            while (rs.next()) {
                Date dateAlert = rs.getDate("dateAlert");
                int id_localCity = rs.getInt("id_localCity");
                int id_alertType = rs.getInt("id_alertType");
                int id_us = rs.getInt("id_user");
                int estado = rs.getInt("estado");
                String ip_origem = rs.getString("ip_origem");
                resposta = "Id do alerta: " + id_alert + ", Data do Alerta: " + dateAlert + ", Estado: " + estado + ", IP de origem: " + ip_origem + ", ID do Local: " + id_localCity + ", ID do Tipo de Alerta: " + id_alertType + ", ID do Utilizador: "  + id_us ;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return resposta;
    }


    //Post Alert
    public String insertAlert (String dt,int state, String ip_origem ,int id_local, int id_alertTp, int id_user){
        System.out.println("Insert Alert");
        String res = null;
        Timestamp timestamp = Timestamp.valueOf(dt);

        try {
            String query = "INSERT INTO alert(dateAlert, estado, ip_origem, id_localCity, id_alertType, id_user) VALUES('" + timestamp + "','" + state + "','" + ip_origem + "','"+ id_local + "','" + id_alertTp + "','" + id_user +"')";
            ps = connec.prepareStatement(query);
            ps.executeUpdate(query);
            res = "Um novo alerta foi adicionado com sucesso.";
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return res;
    }


    //Remove Alert
    public String removeAlert ( int id_alert){
        System.out.println("Remove Alert");
        String res = null;
        try {
            String query = "DELETE FROM alert WHERE id_alert = '" + id_alert + " '";
            ps = connec.prepareStatement(query);
            ps.executeUpdate(query);
            res = "Alerta removido com sucesso. ID:" +id_alert;
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return res;
    }
    //Get All Alerts
    public ArrayList<String> getAlerts () {
        System.out.println("Obter todos os alertas");
        ArrayList<String> resp = new ArrayList<>();
        try {
            st = connec.createStatement();
            String query = "SELECT * from alert";
            rs = st.executeQuery(query);
            while (rs.next()) {
                int id_alert = rs.getInt("id_alert");
                String id_a = String.valueOf(id_alert);
                Date date = rs.getDate("dateAlert");
                String date2 = String.valueOf(date);
                int id_localCity = rs.getInt("id_localCity");
                int id_alertType = rs.getInt("id_alertType");
                String id_at = String.valueOf(id_alertType);
                String id_l = String.valueOf(id_localCity);
                int id_us = rs.getInt("id_user");
                String id_u = String.valueOf(id_us);
                String ip_origem = rs.getString("ip_origem");
                resp.add("ID do alerta:"+id_a);
                resp.add("Data de criação:" + date2);
                resp.add("ID do local:" + id_l);
                resp.add("ID do tipo de alerta:" + id_at);
                resp.add("ID do utilizador:"+id_u);
                resp.add("IP de origem:"+ip_origem);
                for(String res: resp){
                    System.out.println(res);
                }


            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return resp;

    }
}