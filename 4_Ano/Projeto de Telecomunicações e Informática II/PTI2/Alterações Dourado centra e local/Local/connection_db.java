import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class connection_db {
    //Variáveis de Conexão com o MySQL
    private Connection connec = null;
    public Statement st = null;
    public ResultSet rs = null;
    public PreparedStatement ps = null;

    //Variáveis de utilização com o MySQL
    private static final String MySQLPath = "jdbc:mysql://localhost/LocalRoadAlert?autoReconnect=true&useSSL=false&serverTimezone=Europe/Lisbon";
    private static final String MySQLUser = "local";
    private static final String MySQLPass = "password";
    private static String driverName = "com.mysql.cj.jdbc.Driver";

    public connection_db() {


        try{
            Class.forName(driverName);
        }
        catch(Exception e){}
        try {
            connec = DriverManager.getConnection(MySQLPath, MySQLUser, MySQLPass);
            connec.createStatement();
            System.out.println("Connection to Database");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    public void resetDBLocal() {
        try {
            Statement st = connec.createStatement();
            st.execute("drop database if exists LocalRoadAlert");
            st.execute("create database LocalRoadAlert");
            st.execute("use LocalRoadAlert");

        } catch (SQLException e) {
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }


    public void createTablesLocal() {
        System.out.println("Creating tables of database....");
        try {

            //Tabela de Tipos de Alertas
            st = connec.createStatement();
            String sqlAlertType = "create table alertType" +
                    "( id_alertType int not null auto_increment," +
                    "alertTp varchar(255) not null," +
                    "primary key(id_alertType))";

            st.executeUpdate(sqlAlertType);
        System.out.println("Criou tabela alertatype");

            //Tabela de Alertas
            st = connec.createStatement();
            String sqlAlert = "create table alert" +
                    "(index_alert int not null auto_increment," +
                    "id_alert int," + 
                    "dateAlert timestamp not null," +
                    "estado int," +
                    "ip_origem varchar(255) not null," +
                    "ip_rsu varchar(255) not null," +
                    "id_alertType int not null," +
                    "primary key(index_alert),"+
                  // "foreign key(id_local) references local(id_local) on update cascade on delete cascade"+
                    "foreign key(id_alertType) references alertType(id_alertType) on update cascade on delete cascade)";
                    

                    
            st.executeUpdate(sqlAlert);
            System.out.println("Criou tabela alerta");
/*
            //Tabela que associa o alerta a um local  
            st = connec.createStatement(); 
            String sqlalerta_local = "create table local" +
                    "(id_local int not null," + 
                    "primary key(id_local))";
                    st.executeUpdate(sqlalerta_local);        
            System.out.println("Criou tabela local");
    //Inserir na tabela que associa o id do alerta ao ip_rsu
    //public String insertAlerta_rsu(String )


            System.out.println("Created tables in given database...");
*/
        } catch (SQLException e) {
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
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

            String transito = "Transito";
            String tipo5 = "INSERT INTO alertType(alertTp) values ('" + transito + "')";
            ps = connec.prepareStatement(tipo5);
            ps.executeUpdate(tipo5);

        }catch(SQLException e){
            System.out.println("Error: " +e);
        }
    }

    //Get All Alerts
    public ArrayList<String> getAlerts () {
        System.out.println("Obter todos os alertas");
        ArrayList<String> resposta = new ArrayList<>();

        try {
            st = connec.createStatement();
            String query = "SELECT * from alert";
            rs = st.executeQuery(query);
            while (rs.next()) {
                int index = rs.getInt("index_alert");
                int id_alert = rs.getInt("id_alert");

                Timestamp tmstmp = rs.getTimestamp("dateAlert");
                String ip_origem = rs.getString("ip_origem");
                String ip_rsu = rs.getString("ip_rsu");
                String id_tipo = rs.getString("id_alertType");

                resposta.add(String.valueOf(index)+","+id_alert+","+tmstmp+","+ip_origem+","+ip_rsu+","+id_tipo);

                for(String res: resposta){
                    System.out.println(res);
                }
            }
            System.out.println("Response: " + resposta);
        } catch (SQLException e) {
            System.out.println("Erro getAlerts: " + e);
        }
        return resposta;

    }

    //Get One Alert
    public String getAlert ( int index_alert){
        System.out.println("Obter Alert %d" + index_alert);
        String resposta = null;

        try {
            st = connec.createStatement();
            String query = "SELECT * from alert WHERE id_user = '" + index_alert + " '";
            rs = st.executeQuery(query);
            while (rs.next()) {
                int id_alert = rs.getInt("id_alert");
                Timestamp dateAlert = rs.getTimestamp("dateAlert");
                int estado = rs.getInt("estado");
                String ip_origem = rs.getString("ip_origem");
                String ip_rsu = rs.getString("ip_rsu");
                resposta = "Id(index_alert): " + index_alert +", Id do alerta: " + id_alert + ", dateAlert: " + dateAlert + ", estado = " + estado + ", ip_origem = " + ip_origem + ", ip_rsu =" + ip_rsu;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return resposta;
    }
/*
    //Inserir alerta associado a um RSU
    public String insertLocal(int id_local, int index_alert){
        System.out.println("Insert Local");
        String resposta = null;
        try{
            String query = "INSERT INTO local (id_local,index_alert) VALUES ('" + id_local + "','" + index_alert+ "')";
            ps = connec.prepareStatement(query);
            ps.executeUpdate(query);
            resposta = "Novo Local";

        }catch(SQLException e){
            System.out.println("Erro ao inserir o local");
        }
        return resposta;
    }        

*/
    //Post Alert
    public String insertAlert (int id_alert, String dt,int state, String ip_origem, String ip_rsu, int id_tipo){
        System.out.println("Insert Alert");
        String res = null;
        try {
            Timestamp timestamp = Timestamp.valueOf(dt);
            String query = "INSERT INTO alert(id_alert,dateAlert,estado,ip_origem,ip_rsu,id_alertType) VALUES('" + id_alert+ "','" + timestamp + "','" + state + "','" + ip_origem + "','" + ip_rsu + "','" + id_tipo + "')";
            ps = connec.prepareStatement(query);
            ps.executeUpdate(query);
            res = "New alert add";
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return res;
    }


    //Update Alert
    public String updateAlert (int index,int id_alert,String dt, int state, String ip_origem, String ip_rsu, int id_tipo) {
        System.out.println("Update Alert");
        String res = null;
        Timestamp timestamp = Timestamp.valueOf(dt);

        try {
            String query = "UPDATE alert SET dateAlert = " + " '"  + timestamp + "'," +
                    "estado = " + state + "," +
                    "ip_origem = " + " '" + ip_origem + "'," +
                    "ip_rsu = " + " '" + ip_rsu + "'," +
                    "id_alertType = " + id_tipo + " WHERE index_alert = " + index + " ";
            ps = connec.prepareStatement(query);
            ps.executeUpdate(query);
            res = "Alerta atualizado com sucesso.";
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return res;
    }

    //Remove Alert
    public String removeAlert ( int index){
        System.out.println("Remove Alert");
        String res = null;
        try {
            String query = "DELETE FROM alert WHERE index_alert = '" + index + " '";
            ps = connec.prepareStatement(query);
            ps.executeUpdate(query);
            res = "Alert " + index + "removed";
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return res;
    }




}
