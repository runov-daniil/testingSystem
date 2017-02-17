package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

public class clientSocket {
    private static Thread backgroundThread;
    
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
        
        out.writeObject(toSend);
        out.flush();
        
        out.close();
        send.close();
        
        String message = "";
        switch (command) {
            //<editor-fold defaultstate="collapsed" desc="Авторизация">
            case "authorization":
                message = getMessage();
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
                        adminForm.main(true);
                        break;
                }
                break;
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Выход">
            case "logout":
                
                break;
            //</editor-fold>
        }
    }
    
    private static String getMessage() throws IOException{
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
        return getLine;
    }
    
    private static Vector getObject(){
        Vector newObject = new Vector();
        
        return newObject;
    }
    
    private static void listenServer(){
        backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                
            }
        });
        backgroundThread.start();
    }
}
