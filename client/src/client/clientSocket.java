package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
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
        switch (command) {
            case "authorization":
                getMessage();
                break;
        }
    }
    
    private static String getMessage(){
        String newMessage = "";
        
        return newMessage;
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
