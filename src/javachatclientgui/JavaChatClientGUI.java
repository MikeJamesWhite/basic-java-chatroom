/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachatclientgui;

import java.net.Socket;
import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author mike
 */
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
