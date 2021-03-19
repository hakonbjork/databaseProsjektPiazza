import java.sql.*;
import java.util.Properties;

public abstract class DBConn {
    protected Connection conn;

    public DBConn() {
        System.out.println("Object created");
    }

    public void connect() {
        try {
            System.out.println("Started trying to connect...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Properties for user and password.
            Properties p = new Properties();
            p.put("user", "root");
            p.put("password", "databaseGruppe179");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/databaseprosjekt?"+
                    "&allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false", p);
            System.out.println("Success! ");
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect", e);
        }
    }

    public void closeConnection() {
        try {
            this.conn.close();
        }
        catch (Exception e){
            System.out.println("Error in closing connection");
        }
    }
}
