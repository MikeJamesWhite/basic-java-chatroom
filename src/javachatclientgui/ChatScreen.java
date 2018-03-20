/**
 * ChatScreen.java
 * 
 * JFrame component which allows a user to send and receive text messages, as well as access other functionality, such as file transfers, through buttons.
 * 
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

package javachatclientgui;

import java.awt.Color;
import javachatclientlibrary.*;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ChatScreen extends javax.swing.JFrame {

    /**
     * Creates new form ChatScreen
     */
    public ChatScreen() {
        initComponents();
        new Thread(new MessageReceiver(JavaChatClientGUI.inputStream, this, false)).start();
        new Thread(new MessageReceiver(JavaChatClientGUI.fileInputStream, this, true)).start();
        getRootPane().setDefaultButton(SendText);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        FileTransferBtn = new javax.swing.JButton();
        SelectChatModeBox = new javax.swing.JComboBox<>();
        SendText = new javax.swing.JButton();
        privateMessageRecipientLabel = new javax.swing.JLabel();
        privateMessageRecipient = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        textMessage = new javax.swing.JTextField();
        disconnectBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatText = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("Chatroom");

        FileTransferBtn.setText("File Transfer");
        FileTransferBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FileTransferBtnActionPerformed(evt);
            }
        });

        SelectChatModeBox.setMaximumRowCount(2);
        SelectChatModeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Send to All", "Private Message" }));
        SelectChatModeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectChatModeBoxActionPerformed(evt);
            }
        });

        SendText.setText("Send Text");
        SendText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendTextActionPerformed(evt);
            }
        });

        privateMessageRecipientLabel.setText("Send to:");
        privateMessageRecipientLabel.setEnabled(false);

        privateMessageRecipient.setEnabled(false);

        jLabel3.setText("Message:");

        disconnectBtn.setText("Disconnect");
        disconnectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectBtnActionPerformed(evt);
            }
        });

        chatText.setEditable(false);
        jScrollPane1.setViewportView(chatText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(disconnectBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(SelectChatModeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(privateMessageRecipientLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(privateMessageRecipient, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textMessage)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SendText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FileTransferBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(disconnectBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SelectChatModeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FileTransferBtn)
                    .addComponent(privateMessageRecipientLabel)
                    .addComponent(privateMessageRecipient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SendText)
                    .addComponent(jLabel3)
                    .addComponent(textMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SelectChatModeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectChatModeBoxActionPerformed
        if (this.SelectChatModeBox.getSelectedItem().equals("Private Message")) {
            this.privateMessageRecipient.setEnabled(true);
            this.privateMessageRecipientLabel.setEnabled(true);
        }
        else {
            this.privateMessageRecipient.setEnabled(false);
            this.privateMessageRecipientLabel.setEnabled(false);
        }
    }//GEN-LAST:event_SelectChatModeBoxActionPerformed

    private void disconnectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectBtnActionPerformed
        JavaChatClientGUI.reset();
        LoginScreen login = new LoginScreen();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_disconnectBtnActionPerformed

    private void FileTransferBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FileTransferBtnActionPerformed
        SendFileDialog sendFile = new SendFileDialog(this, true);
        sendFile.setVisible(true);
    }//GEN-LAST:event_FileTransferBtnActionPerformed

    private void SendTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendTextActionPerformed
        if (textMessage.getText().equals(""))
            return;
        if (SelectChatModeBox.getSelectedItem().equals("Send to All")) {
            ChatClientLib.sendMessage("<sendtoall>|" + textMessage.getText(), false, this);
        }
        else {
            ChatClientLib.sendMessage("<sendprivate>|" + privateMessageRecipient.getText() + "|" + textMessage.getText(), true, this);
        }
        textMessage.setText("");
        textMessage.requestFocus();
    }//GEN-LAST:event_SendTextActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(ChatScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatScreen().setVisible(true);
            }
        });
    }
    
    public void appendToChat(String msg, Color c) {
        chatText.setEditable(true);
        appendToPane(chatText, msg + "\n", c);
        chatText.setEditable(false);
    }
    
    private void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton FileTransferBtn;
    private javax.swing.JComboBox<String> SelectChatModeBox;
    private javax.swing.JButton SendText;
    private javax.swing.JTextPane chatText;
    private javax.swing.JButton disconnectBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField privateMessageRecipient;
    private javax.swing.JLabel privateMessageRecipientLabel;
    private javax.swing.JTextField textMessage;
    // End of variables declaration//GEN-END:variables
}
