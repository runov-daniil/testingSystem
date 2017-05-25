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
    public static void addUser(String login, String password, String FIO, String level) throws ClassNotFoundException, SQLException {
        getConnection();
        st.execute("INSERT INTO users(login, password, FIO, level) VALUES ('"+login+"', '"+password+"', '"+FIO+"', '"+level+"');");
        closeConnection();
    }
    //Формирование списка пользователей
    public static String getUsers() throws ClassNotFoundException, SQLException {
        getConnection();
        String Users = "";
        rs = st.executeQuery("SELECT * FROM users;");
        while(rs.next()) {
            Users = Users + rs.getString("login") + "|";
            Users = Users + rs.getString("FIO") + "|";
            Users = Users + rs.getString("level") + "$";
        }        
        System.out.println(Users);
        closeConnection();
        return Users;
    }
    //Удаление пользователя
    public static void deleteUser(String login) throws ClassNotFoundException, SQLException {
        getConnection();
        st.execute("DELETE FROM users WHERE login = '"+login+"';");
        closeConnection();
    }
}