package server;

import com.sun.corba.se.pept.transport.ListenerThread;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class runServer extends javax.swing.JFrame {
    private static boolean firstStart = true;
    public static boolean statusServer = true;
    private static Thread listenThread;
    private static runServer runServer = new runServer();
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
                    
                    Vector header = new Vector();
                    header.add("Команда");
                    header.add("Данные");
                    header.add("Адресс");
                    Vector get = (Vector)getMessage;
                    DefaultTableModel dtm = (DefaultTableModel)serverPanel.getRequests.getModel();
                    dtm.setDataVector(get, header);
                    
                    listenThread.interrupt();
                    
                    if(statusServer == true){
                       startBTN.doClick();
                    }
                } catch (IOException ex) {} catch (ClassNotFoundException ex) {}
            }
        });
        listenThread.start();
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
