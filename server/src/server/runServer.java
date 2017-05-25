package server;

import com.sun.corba.se.pept.transport.ListenerThread;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class runServer extends javax.swing.JFrame {
    private static boolean firstStart = true;
    public static boolean statusServer = true;
    private static Thread listenThread;
    private static DefaultTableModel dtm = (DefaultTableModel)serverPanel.getRequests.getModel();
    private static DefaultTableModel dtmOnline = (DefaultTableModel)serverPanel.onlineTable.getModel();
    private static runServer runServer = new runServer();
    private static String dataUser = "";
    public runServer() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startBTN = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        startBTN.setFont(new java.awt.Font("Times New Roman", 2, 24)); // NOI18N
        startBTN.setText("Старт");
        startBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(startBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(startBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 34, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void startBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startBTNActionPerformed
        if(firstStart == true){
            serverPanel.startBTN.setText("Стоп сервер");
            serverPanel.startBTN.setEnabled(true);
            firstStart = false;
            
            Vector headerRequest = new Vector();
            headerRequest.add("Запрос");
            headerRequest.add("Данные");
            headerRequest.add("Адрес");
            Vector data = new Vector();
            dtm.setDataVector(data, headerRequest);
            Vector headerOnline = new Vector();
            headerOnline.add("Логин");
            headerOnline.add("Адрес");
            Vector outData = new Vector();
            dtmOnline.setDataVector(outData, headerOnline);
            this.hide();
        }
        if(statusServer == true){
            listen();
        }else{
            serverPanel.startBTN.setText("Старт сервер");
            statusServer = true;
        }
    }//GEN-LAST:event_startBTNActionPerformed

    private static void listen() {
        listenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(4444);
                    Socket listen = serverSocket.accept();
                    
                    ObjectInputStream in = new ObjectInputStream(listen.getInputStream());
                    Object getMessage = new Object(); 
                    getMessage = in.readObject();
                    
                    listen.close();
                    serverSocket.close();
                    
                    dtm.addRow((Vector) getMessage);
                    
                    unCrypt();
                    
                    listenThread.interrupt();
                    
                    if(statusServer == true){
                       startBTN.doClick();
                    }
                } catch (IOException ex) {} catch (ClassNotFoundException ex) {} catch (SQLException ex) {
                    Logger.getLogger(runServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        listenThread.start();
    }
    
    private static void unCrypt() throws UnknownHostException, IOException, ClassNotFoundException, SQLException{
        int countRow = serverPanel.getRequests.getRowCount();
        String messageToSend = "";
        String command = serverPanel.getRequests.getValueAt(countRow - 1, 0).toString();
        switch(command){
            //<editor-fold defaultstate="collapsed" desc="Авторизация">
            case "authorization":
                String data = serverPanel.getRequests.getValueAt(countRow-1, 1).toString();
                String login = "";
                String password = "";
                int i = 0;
                boolean detect = false;
                while(true){
                    char ch = data.charAt(i);
                    if(detect == false){
                        if(ch != '|'){
                            login = login + ch;
                            i++;
                        }else{
                            detect = true;
                            i++;
                        }
                    }else{
                        password = password + ch;
                        i++;
                    }
                    if(i == data.length()){
                        break;
                    }
                }
                messageToSend = dataBase.authorization(login, password);
                if(!(messageToSend.equals("error"))){
                    Vector newOnline = new Vector();
                    newOnline.add(login);
                    String IP = serverPanel.getRequests.getValueAt(countRow-1, 2).toString();
                    IP = IP.substring(1);
                    newOnline.add(IP);
                    dtmOnline.addRow(newOnline);
                }
                send(messageToSend);
                break;
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Выход">
            case "logout":
                int nextOnline = 0;
                while(true){
                    String dataLogin = serverPanel.getRequests.getValueAt(countRow-1, 1).toString();
                    String loginOnline = serverPanel.onlineTable.getValueAt(nextOnline, 0).toString();
                    if(loginOnline.equals(dataLogin)){
                        dtmOnline.removeRow(nextOnline);
                        break;
                    }else{
                        nextOnline++;
                    }
                }
                break;
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Добавление нового пользователя">
            case "newUser":
                dataUser = serverPanel.getRequests.getValueAt(countRow-1, 1).toString() + '|';
                String newLogin = unCryptData();
                String newPassword = unCryptData();
                String newFIO = unCryptData();
                String level = unCryptData();
                dataBase.addUser(newLogin, newPassword, newFIO, level);
                send("Данные успешно приняты!");
            break;                
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Запрос пользователей базы">
            case "getUsers":
                String Users = dataBase.getUsers();
                send(Users);
                break;
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Удаление пользователя">
            case "deleteUser":
                dataBase.deleteUser(serverPanel.getRequests.getValueAt(countRow - 1, 1).toString());
                send("Запрос на удаление принят!");
                break;
            //</editor-fold>
        }
    }
    
    private static String unCryptData() {
        String getData = "";
        int countChars = 0;
        while(true){
            char ch = dataUser.charAt(countChars);
            if(ch != '|'){
                getData = getData + ch;
                countChars++;
            }else{
                dataUser = dataUser.substring(countChars+1);
                break;
            }
        }
        return getData;
    }
    
    private static void send(String messageToSend) throws UnknownHostException, IOException{
        int countRow = serverPanel.getRequests.getRowCount();
        String IP = serverPanel.getRequests.getValueAt(countRow - 1, 2).toString();
        IP = IP.substring(1, IP.length());
        int port = 5555;
        
        InetAddress ipAdress = InetAddress.getByName(IP);
        Socket send = new Socket(ipAdress, port);
        
        OutputStream out = send.getOutputStream();
        DataOutputStream outData = new DataOutputStream(out);
        outData.writeUTF(messageToSend);
        out.flush();
        
        outData.close();
        out.close();
        send.close();
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        serverPanel.startBTN.setEnabled(true);
    }//GEN-LAST:event_formWindowClosing

    public static void main(boolean visible) {
        runServer.setResizable(false);
        runServer.setSize(200, 79);
        runServer.setVisible(visible);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton startBTN;
    // End of variables declaration//GEN-END:variables
}
