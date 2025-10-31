import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.charset.StandardCharsets;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Admin
 */
public class Chat extends javax.swing.JFrame {

    static InputStream inStream;
    static OutputStream outStream;
    static Socket socket;
    static String username;
    /**
     * Creates new form Chat
     */
    public Chat() {
        initComponents();
        startMessageReader();
    }
    
    private void startMessageReader() {
        new Thread(() -> {
            try {
                while (true) {
                    if (inStream != null) {
                        StringBuffer sb = new StringBuffer();
                        int k;
                        while ((k = inStream.read()) != -1 && k != '\n') {
                            sb.append((char) k);
                        }
                        String message = sb.toString().trim();
                        if (!message.isEmpty()) {
                            if (message.startsWith("/username")) {
                                final String assigned = message.substring(10).trim();
                                Chat.username = assigned;
                                SwingUtilities.invokeLater(() -> {
                                    Chat.this.setTitle("Chat - " + assigned);
                                });
                                continue;
                            }
                            if (message.startsWith("/users ")) {
                                final String listCsv = message.substring(7).trim();
                                final String[] users = listCsv.isEmpty() ? new String[0] : listCsv.split(",");
                                SwingUtilities.invokeLater(() -> {
                                    if (username == null || username.trim().isEmpty()) {
                                        usersList.setListData(users);
                                    } else {
                                        java.util.List<String> filtered = new java.util.ArrayList<>();
                                        for (String u : users) {
                                            if (!u.equals(username)) filtered.add(u);
                                        }
                                        usersList.setListData(filtered.toArray(new String[0]));
                                    }
                                });
                            } else if (message.startsWith("/private ")) {
                                String[] parts = message.split(" ", 4);
                                if (parts.length >= 4) {
                                    String sender = parts[1];
                                    String reciver = parts[2];
                                    String msg = parts[3];
                                    if (reciver.equals(username) || sender.equals(username)) {
                                        final String otherParty = sender.equals(username) ? reciver : sender;
                                        SwingUtilities.invokeLater(() -> {
                                            Private privateWindow = null;
                                            for (java.awt.Window window : java.awt.Window.getWindows()) {
                                                if (window instanceof Private) {
                                                    Private pw = (Private) window;
                                                    if (otherParty.equals(pw.reciver)) {
                                                        privateWindow = pw;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (privateWindow == null) {
                                                privateWindow = new Private(otherParty);
                                                privateWindow.setTitle("Prywatna rozmowa z " + otherParty);
                                                privateWindow.setVisible(true);
                                            }
                                            privateWindow.reciveMessage(sender + ": " + msg);
                                        });
                                    }
                                }
                            } else {
                                displayMessage(message);
                            }
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, e);
            }
        }).start();
    }
    
    private void displayMessage(final String message) {
        if (SwingUtilities.isEventDispatchThread()) {
            messageArea.append(message + "\n");
            messageArea.setCaretPosition(messageArea.getDocument().getLength());
        } else {
            SwingUtilities.invokeLater(() -> {
                messageArea.append(message + "\n");
                messageArea.setCaretPosition(messageArea.getDocument().getLength());
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        chatOptions = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        usersList = new javax.swing.JList<>();
        messageBox = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageArea = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        send = new javax.swing.JButton();
        message = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        quit = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        chatOptions.setBackground(new java.awt.Color(232, 232, 232));
        chatOptions.setPreferredSize(new java.awt.Dimension(108, 362));

        usersList.setVisibleRowCount(16);
        usersList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usersListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(usersList);

        javax.swing.GroupLayout chatOptionsLayout = new javax.swing.GroupLayout(chatOptions);
        chatOptions.setLayout(chatOptionsLayout);
        chatOptionsLayout.setHorizontalGroup(
            chatOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chatOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                .addContainerGap())
        );
        chatOptionsLayout.setVerticalGroup(
            chatOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chatOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );

        messageBox.setBackground(new java.awt.Color(244, 244, 244));

        messageArea.setEditable(false);
        messageArea.setColumns(20);
        messageArea.setLineWrap(true);
        messageArea.setRows(5);
        messageArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(messageArea);

        javax.swing.GroupLayout messageBoxLayout = new javax.swing.GroupLayout(messageBox);
        messageBox.setLayout(messageBoxLayout);
        messageBoxLayout.setHorizontalGroup(
            messageBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messageBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        messageBoxLayout.setVerticalGroup(
            messageBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messageBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );

        jPanel3.setBackground(new java.awt.Color(232, 232, 232));

        send.setText("Wyślij");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });

        message.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(message, javax.swing.GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(send)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(send))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(224, 224, 224));

        quit.setText("Opuść");
        quit.setMaximumSize(new java.awt.Dimension(96, 23));
        quit.setPreferredSize(new java.awt.Dimension(96, 23));
        quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(quit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(quit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chatOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(messageBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(messageBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chatOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void quitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitActionPerformed
        try {
            outStream.write(("/end\n").getBytes());
            inStream.close();
            outStream.close();
            socket.close();
//            this.dispose();
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_quitActionPerformed

    private void messageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            send();
        }
    }//GEN-LAST:event_messageKeyPressed

    private void sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendActionPerformed
        send();
    }//GEN-LAST:event_sendActionPerformed

    private void usersListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usersListMouseClicked
        if (evt.getClickCount() == 2) {
            String reciver = usersList.getSelectedValue();
            if (reciver != null && !reciver.trim().isEmpty()) {
                Private privateWindow = new Private(reciver);
                privateWindow.setTitle("Prywatna rozmowa z " + reciver);
                privateWindow.setVisible(true);
            }
        }
    }//GEN-LAST:event_usersListMouseClicked

    private void send(){
        if(!message.getText().trim().equals("")){
            try {
                String ops = message.getText();
                outStream.write((ops+"\n").getBytes());
                message.setText("");
            } catch (IOException ex) {
                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try{
            String username = JOptionPane.showInputDialog(null, "Enter username:", "Username", JOptionPane.PLAIN_MESSAGE);
            if (username != null) {
                username = username.replaceAll(" ", "");
            }
            if (username == null || username.isEmpty()) {
                username = "User" + socket.getPort();
            }

            socket = new Socket("localhost", 2011);

            inStream = socket.getInputStream();
            outStream = socket.getOutputStream();

            Chat.username = username;

            outStream.write(("/user " + username + "\n").getBytes(StandardCharsets.UTF_8));
            outStream.flush();

        } catch(IOException e){
        }
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chatOptions;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField message;
    private javax.swing.JTextArea messageArea;
    private javax.swing.JPanel messageBox;
    private javax.swing.JButton quit;
    private javax.swing.JButton send;
    private javax.swing.JList<String> usersList;
    // End of variables declaration//GEN-END:variables
}
