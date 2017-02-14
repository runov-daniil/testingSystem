package server;

import com.sun.corba.se.pept.transport.Connection;
import java.sql.*;

public class dataBase {
    private static Connection conn;   
    public static void getConnection() throws ClassNotFoundException, SQLException{
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = (Connection) DriverManager.getConnection("jdbc:sqlite:data.db");
    }
    //Закрытие базы
    public  static void closeConnection() throws ClassNotFoundException, SQLException {
        conn.close();
    }    
}
