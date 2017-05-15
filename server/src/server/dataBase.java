package server;

import java.sql.*;

public class dataBase {
    private static Connection conn;
    private static Statement st;
    private static ResultSet rs;
    //Подключение к базе
    public static void getConnection() throws ClassNotFoundException, SQLException{
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:data.db");
        st = conn.createStatement();
        conn.setAutoCommit(true);
    }
    //Закрытие базы
    public  static void closeConnection() throws ClassNotFoundException, SQLException {
        conn.close();
        st.close();
        rs.close();
    }
    //Авторизация
    public static String authorization(String login, String password) throws ClassNotFoundException, SQLException {
        getConnection();
        String level = "error";
        rs = st.executeQuery("SELECT level FROM users WHERE login = '"+login+"' AND password = '"+password+"';");
        level = rs.getString("level");
        closeConnection();
        return level;
    }
    //Добавление нового пользователя
    public static void addUser() throws ClassNotFoundException, SQLException {
        
    }
    //Формирование списка пользователей
}