package client;

import client.student.studentForm;
import client.teacher.teacherForm;
import client.admin.adminForm;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class clientSocket {
    private static Thread backgroundThread;
    public static String messageCrypt = "";
    
    public static void sendRequest(String command, String data) throws UnknownHostException, IOException{
        String IP = "127.0.0.1";
        int port = 4444;
        
        InetAddress ipAdress = InetAddress.getByName(IP);
        Socket send = new Socket(ipAdress, port);
        ObjectOutputStream out = new ObjectOutputStream(send.getOutputStream());
        
        Vector toSend = new Vector();
        toSend.add(command);
        toSend.add(data);
        toSend.add(send.getInetAddress().toString());
        String MyIP = send.getInetAddress().toString();
        
        out.writeObject(toSend);
        out.flush();
        
        out.close();
        send.close();
        
        String message = "";
        int waitServer = 0;
        switch (command) {
            //<editor-fold defaultstate="collapsed" desc="Авторизация">
            case "authorization":
                while(messageCrypt.length() == 0){
                    waitServer++;
                    System.out.println("Ожидание сервера: " + waitServer);
                }
                message = messageCrypt;
                messageCrypt = "";
                String login = "";
                int i = 0;
                while(true){
                    char ch = data.charAt(i);
                    if(ch != '|'){
                        login = login + ch;
                        i++;
                    }else{
                        break;
                    }
                }
                switch(message){
                    case "teacher":
                        teacherForm.main(true);
                        break;
                    case "student":
                        studentForm.main(true);
                        break;
                    case "admin":
                        adminForm.loginLabel.setText(login);
                        adminForm.MyIP = MyIP.substring(1, MyIP.length());
                        adminForm.main(true);
                        break;
                }
                break;
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Выход">
            case "logout":
                
                break;
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Добавление пользователя">
            case "addUser":
                
                break;
            //</editor-fold>
        }
    }
    
    public static void getMessage() throws IOException{
        ServerSocket client = new ServerSocket(5555);
        Socket get = client.accept();
        
        InputStream getIn = get.getInputStream();
        DataInputStream in = new DataInputStream(getIn);
        String getLine = null;    
        
        getLine = in.readUTF();
        
        in.close();
        getIn.close();
        get.close();
        client.close();
        messageCrypt = getLine;
    }
    
    private static Vector getObject(){
        Vector newObject = new Vector();
        
        return newObject;
    }
    
    public static void sendVector(Vector toSend) throws UnknownHostException, IOException{
        String IP = "127.0.0.1";
        int port = 4444;
        
        InetAddress ipAdress = InetAddress.getByName(IP);
        Socket send = new Socket(ipAdress, port);
        ObjectOutputStream out = new ObjectOutputStream(send.getOutputStream());
        
        out.writeObject(toSend);
        out.flush();
        
        out.close();
        send.close();
    }
    
    public static void listenServer(){
        backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getMessage();
                } catch (IOException ex) {}
            }
        });
        backgroundThread.start();
    }
}
