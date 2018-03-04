/**
 * ChatClient.java
 *
 * A client program for connecting to a chatroom server and allowing communication with other clients of the chatroom.
 *
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

package chatclient;

import java.util.*;
import java.net.*;
import java.io.*;

public class ChatClient {
    static Scanner scanIn = new Scanner(System.in);
    static final int DEFAULT_PORT = 50048;
    static final String DEFAULT_HOST = "kingkong.zapto.org";

    public static void main(String[] args) {
        boolean connected = false;
        Socket socket = null;
        while (!connected) {
            System.out.print("Enter the host: ");
            String host = scanIn.nextLine();
            System.out.print("Enter the port: ");
            int port = Integer.parseInt(scanIn.nextLine());
            System.out.println("Attempting connection to " + host + ":" + String.valueOf(port));
            try { 
                socket = new Socket(host, port);
                connected = true;
            }
            catch (UnknownHostException e) {
                System.err.println("Host not found. Try again...");
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        System.out.println("Connected!");
        try {
            new Thread(new MessageReceiver(new DataInputStream(socket.getInputStream()))).start();
            ChatClientLib.sendMessages(scanIn, new DataOutputStream(socket.getOutputStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}