/**
 * ChatClientLib.java
 * 
 * A library of useful methods which are used to support the chatroom client driver program.
 * 
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

package javachatclientlibrary;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import javachatclientgui.*;
import javax.swing.SwingUtilities;

public class ChatClientLib {

    public static void listenForMessages(DataInputStream input, ChatScreen chatScreen) {
        String msg = "";
        while (JavaChatClientGUI.running.get()) {

            // receive message            
            try {
                synchronized (input) {
                    if (input.available() < 1) {
                        continue;
                    }
                    msg = input.readUTF();
                }
            }
            catch (IOException e) {
                System.out.println("Input from server disconnected.");
                JavaChatClientGUI.running.set(false);
                ServerDisconnectDialog sdd = new ServerDisconnectDialog(chatScreen, true);
                sdd.setVisible(true);
                return;
            }
            System.out.println("Received a message!");
            
            //parse message
            String[] splitMsg = msg.split("\\|");
            String toPrint = "";
            Color color = Color.BLACK;
            if (splitMsg[0].equals("<sendtoall>")) {
                toPrint = splitMsg[1] + ": " + splitMsg[2];
            }
            else if (splitMsg[0].equals("<sendprivate>")) {
                color = Color.BLUE;
                if (splitMsg[1].equals("you")) 
                    toPrint = "you whisper: " + splitMsg[2];
                else
                    toPrint = splitMsg[1] + " whispers: " + splitMsg[2];
            }
            else if (splitMsg[0].equals("<filetoall>")) {
                continue;
            }
            else if (splitMsg[0].equals("<fileprivate>")) {
                continue;
            }
            else if (splitMsg[0].equals("<disconnect>")) {
                System.out.println("Input from server disconnected.");
                JavaChatClientGUI.running.set(false);
                ServerDisconnectDialog sdd = new ServerDisconnectDialog(chatScreen, true);
                sdd.setVisible(true);
                return;
            }
            else if (splitMsg[0].equals("<notification>")) {
                toPrint = splitMsg[1];
                color = Color.RED;
            }            
            chatScreen.appendToChat(toPrint, color);
        }
    }

    public static void sendMessage(String msg, boolean privateMessage, ChatScreen chatScreen) {
        try {
            System.out.println(msg);
            JavaChatClientGUI.outputStream.writeUTF(msg);
            return;
        }
        catch (IOException e) {
            System.out.println("Output to server disconnected.");
            JavaChatClientGUI.running.set(false);
        } 
    }
    
    public static void sendFile(File f, int numBytes) {
        
    }
}