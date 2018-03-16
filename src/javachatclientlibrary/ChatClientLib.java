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
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    
    public static void listenForFiles(DataInputStream fileInput, ChatScreen chatScreen) {
        String msg = "";
        while (JavaChatClientGUI.running.get()) {
            // receive header message            
            try {
                synchronized (fileInput) {
                    if (fileInput.available() < 1) {
                        continue;
                    }
                    msg = fileInput.readUTF();
                }
            }
            catch (IOException e) {
                System.out.println("Input from server disconnected.");
                JavaChatClientGUI.running.set(false);
                ServerDisconnectDialog sdd = new ServerDisconnectDialog(chatScreen, true);
                sdd.setVisible(true);
                return;
            }
            System.out.println("Received a file header!");
            
            //parse message
            String[] splitMsg = msg.split("\\|");
            
            ReceiveFileDialog rfd = new ReceiveFileDialog(chatScreen, true, splitMsg[3], splitMsg[2], Integer.parseInt(splitMsg[1]));
            rfd.setVisible(true);
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
    
    public static void sendFileAll(String filepath, ChatScreen chatScreen) {
        String filename;
        File f = new File(filepath);
        long size = f.length();
        
        try {
            FileInputStream fileIn = new FileInputStream(f);
            if (filepath.lastIndexOf("/") != -1)
                filename = filepath.substring(filepath.lastIndexOf("/") + 1);
            else
                filename = filepath;
                
            String headerMsg = "<filetoall>|" + String.valueOf(size) + "|" + filename;
            System.out.println("Sending header file to server");
            JavaChatClientGUI.fileOutputStream.writeUTF(headerMsg);

            int count;
            byte[] buffer = new byte[4096];
            while ((count = fileIn.read(buffer)) > 0) {
                System.out.println("Sending a chunk of bytes.");
                JavaChatClientGUI.fileOutputStream.write(buffer, 0, count);
            }
            System.out.println("Finished sending bytes.");
            fileIn.close();
            
        }
        catch (IOException e) {
            System.out.println("error reading file");
            return;
        }
    }
    
    public static void sendFilePrivate(String filepath, String recipient, ChatScreen chatScreen) {
        String filename;
        File f = new File(filepath);
        long size = f.length();
        
        try {
            FileInputStream fileIn = new FileInputStream(f);
            if (filepath.lastIndexOf("/") != -1)
                filename = filepath.substring(filepath.lastIndexOf("/") + 1);
            else
                filename = filepath;
            String headerMsg = "<fileprivate>|" + String.valueOf(size) + "|" + filename + "|" + recipient;
            System.out.println("Sending header file to server");
            JavaChatClientGUI.fileOutputStream.writeUTF(headerMsg);

            int count;
            byte[] buffer = new byte[4096];
            while ((count = fileIn.read(buffer)) > 0) {
                System.out.println("Sending a chunk of bytes.");
                JavaChatClientGUI.fileOutputStream.write(buffer, 0, count);
            }
            System.out.println("Finished sending bytes.");
            fileIn.close();
            
        }
        catch (IOException e) {
            System.out.println("error reading file");
            return;
        }
    }
}