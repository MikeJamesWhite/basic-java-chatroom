/**
 * AliasSelect.java
 * 
 * JFrame which allows a user to select an alias and checks with the sever that this alias is not already in use.
 * 
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

package javachatclientgui;

import java.io.IOException;

public class AliasSelect extends javax.swing.JFrame {

    /**
     * Creates new form AliasSelect
     */
    public AliasSelect() {
        initComponents();
        aliasAlreadyInUseLabel.setVisible(false);
        getRootPane().setDefaultButton(loginBtn);
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
        jLabel2 = new javax.swing.JLabel();
        aliasField = new javax.swing.JTextField();
        loginBtn = new javax.swing.JButton();
        aliasAlreadyInUseLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("Select Alias");

        jLabel2.setText("Alias:");

        aliasField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aliasFieldActionPerformed(evt);
            }
        });

        loginBtn.setText("Login");
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        aliasAlreadyInUseLabel.setForeground(new java.awt.Color(255, 24, 0));
        aliasAlreadyInUseLabel.setText("Alias already in use... Pick again.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(aliasAlreadyInUseLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(aliasField, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(aliasField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(aliasAlreadyInUseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void aliasFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aliasFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aliasFieldActionPerformed

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        aliasAlreadyInUseLabel.setVisible(false);
        String alias = aliasField.getText();
        if (alias.equals("")) {
            aliasAlreadyInUseLabel.setText("Please fill in an alias.");
            aliasAlreadyInUseLabel.setVisible(true);
            return;
        }
        try {
            JavaChatClientGUI.outputStream.writeUTF(alias);
            String serverResponse = JavaChatClientGUI.inputStream.readUTF();
            if (serverResponse.equals("<disconnect>")) {
                System.out.println("Server disconnected.");
                JavaChatClientGUI.running.set(false);
                ServerDisconnectDialog sdd = new ServerDisconnectDialog(this, true);
                sdd.setVisible(true);
                return;
            }
            
            if (serverResponse.equals("success")) {
                Thread onShutdown = new Thread
                ( new Runnable () {
                    public void run() {
                        try {
                            JavaChatClientGUI.outputStream.writeUTF("<disconnect>");
                        }
                        catch (IOException e) {}
                    }
                    
                });
                
                Runtime.getRuntime().addShutdownHook(onShutdown);
                ChatScreen chat = new ChatScreen();
                chat.setVisible(true);
                this.dispose();
            }
            else {
                aliasAlreadyInUseLabel.setText("Alias already in use... Pick again.");
                aliasAlreadyInUseLabel.setVisible(true);
            }
        }
        catch (IOException e) {
            System.err.println("Error while confirming alias. Exiting...");
            return;
        }
    }//GEN-LAST:event_loginBtnActionPerformed

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
            java.util.logging.Logger.getLogger(AliasSelect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AliasSelect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AliasSelect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AliasSelect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AliasSelect().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aliasAlreadyInUseLabel;
    private javax.swing.JTextField aliasField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton loginBtn;
    // End of variables declaration//GEN-END:variables
}
