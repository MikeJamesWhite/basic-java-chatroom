/**
 * JavaChatClientGUI.java
 * 
 * Driver program which stores some static information, such as the sockets used for communication with server, and is responsible for launcing the GUI.
 * 
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

package javachatclientgui;

import java.net.Socket;
import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class JavaChatClientGUI {
    
    public static Socket socket;
    public static DataInputStream inputStream;
    public static DataOutputStream outputStream;
    
    public static Socket fileSocket;
    public static DataInputStream fileInputStream;
    public static DataOutputStream fileOutputStream;
    
    public static AtomicBoolean running = new AtomicBoolean(false);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LoginScreen login = new LoginScreen();
        login.setVisible(true);
    }
    
    public static void reset() {
        running.set(false);
        try {
            outputStream.writeUTF("<disconnect>");
        }
        catch (IOException e) { }
        socket = null;
        inputStream = null;
        outputStream = null;
        fileSocket = null;
        fileInputStream = null;
        fileOutputStream = null;
    }
    
}
