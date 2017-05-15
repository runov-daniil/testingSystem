package client.publicClasses;

import client.clientSocket;
import java.io.IOException;
import java.util.Vector;

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

    public static void main(String request, int count) throws IOException{
        waitServer.setSize(435, 100);
        waitServer.setVisible(true);
        Vector toSend = new Vector();
        progressWait.setMinimum(0);
        progressWait.setMaximum(count);
        int step = 0;
        int length = request.length();
        String column = "";
        int i = 0;
        while(i < length){
            char ch = request.charAt(i);
            if(ch != '@'){
                if(ch != '$'){
                    column = column + ch;
                    i++;
                }else{
                    toSend.add(column);
                    column = "";
                    i++;
                }
            }else{
                clientSocket.sendVector(toSend);
                toSend.removeAllElements();
                step++;
                progressWait.setValue(step);
                i++;
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JProgressBar progressWait;
    // End of variables declaration//GEN-END:variables
}
