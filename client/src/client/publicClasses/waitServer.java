package client.publicClasses;

import client.admin.addNewPredmet;
import client.admin.adminForm;
import client.clientSocket;
import static client.clientSocket.messageCrypt;
import client.loginFrame;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JOptionPane;

public class waitServer extends javax.swing.JDialog {
    private static waitServer waitServer = new waitServer();
    public waitServer() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progressWait = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ожидание сервера");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressWait, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressWait, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void closeFrame(){
        dispose();
    }
    
    public static void main(String request, int count) throws IOException{
        waitServer.setSize(435, 100);
        waitServer.setVisible(true);
        progressWait.setMinimum(0);
        progressWait.setMaximum(count);
        int step = 0;
        int length = request.length();
        String command = "";
        String data = "";
        boolean flag = true;
        int i = 0;
        while(i < length){
            char ch = request.charAt(i);
            if(ch != '@'){
                if((ch != '$') && (flag == true)){
                    command = command + ch;
                    i++;
                }else{
                    if(flag == true){
                        flag = false;
                        i++;
                    }else{
                        data = data + ch;
                        i++;
                    }
                }
            }else{ 
                loginFrame.jButton1.doClick();
                clientSocket.sendRequest(command, data);
                int waitSrv = 0;
                switch (command){
                    //<editor-fold defaultstate="collapsed" desc="Добавление нового пользователя">
                    case "newUser":                        
                        command = "";
                        data = "";
                        flag = true;
                        while(messageCrypt.length() == 0){
                            waitSrv++;
                            System.out.println("Ожидание сервера: " + waitSrv);
                        }
                        String message = clientSocket.messageCrypt;
                        clientSocket.messageCrypt = "";
                        step++;
                        progressWait.setValue(step);
                        i++;
                      break;
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="Запрос пользователей">
                    case "getUsers":
                        command = "";
                        data = "";
                        flag = true;
                        loginFrame.jButton1.doClick();
                        while(messageCrypt.length() == 0){
                            waitSrv++;
                            System.out.println("Ожидание сервера: " + waitSrv);
                        }
                        String Users = clientSocket.messageCrypt;
                        clientSocket.messageCrypt = "";                        
                        adminForm.setUsersTable(Users);
                        step++;
                        progressWait.setValue(step);
                        i++;
                      break;
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="Удаление пользователя">
                    case "deleteUser":
                        command = "";
                        data = "";
                        flag = true;
                        loginFrame.jButton1.doClick();
                        while(messageCrypt.length() == 0){
                            waitSrv++;
                            System.out.println("Ожидание сервера: " + waitSrv);
                        }
                        String getMsg = clientSocket.messageCrypt;
                        clientSocket.messageCrypt = "";
                        step++;
                        progressWait.setValue(step);
                        i++;
                        break;
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="Добавление предмета">.
                    case "newPredmet":
                        command = "";
                        data = "";
                        flag = true;
                        loginFrame.jButton1.doClick();
                        while(messageCrypt.length() == 0){
                            waitSrv++;
                            System.out.println("Ожидание сервера: " + waitSrv);
                        }
                        getMsg = clientSocket.messageCrypt;
                        clientSocket.messageCrypt = "";
                        if(getMsg.equals("false")){
                            JOptionPane.showMessageDialog(waitServer, "Ошибка! Такой предмет уже существует");
                        }
                        step++;
                        progressWait.setValue(step);
                        i++;
                        break;
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="Запрос предметов">.
                    case "getPredmets":
                        command = "";
                        data = "";
                        flag = true;
                        loginFrame.jButton1.doClick();
                        while(messageCrypt.length() == 0){
                            waitSrv++;
                            System.out.println("Ожидание сервера: " + waitSrv);
                        }
                        getMsg = clientSocket.messageCrypt;
                        clientSocket.messageCrypt = "";
                        adminForm.setPredmetTable(getMsg);
                        step++;
                        progressWait.setValue(step);
                        i++;
                        break;
                    //</editor-fold>
                }
            }
        }
        waitServer.closeFrame();
    }    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JProgressBar progressWait;
    // End of variables declaration//GEN-END:variables
}
