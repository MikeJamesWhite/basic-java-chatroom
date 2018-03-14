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
import java.util.concurrent.atomic.AtomicBoolean;
import java.net.*;
import java.io.*;

public class ChatClient {
    static Scanner scanIn = new Scanner(System.in);
    static AtomicBoolean running = new AtomicBoolean(true);
    static final int DEFAULT_PORT = 50048;
    static final String DEFAULT_HOST = "kingkong.zapto.org";

    public static void main(String[] args) {
        boolean connected = false;
        Socket socket = null;

        // connect to the server
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
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            boolean accepted = false;
            String alias = "";
            String serverResponse;

            // send alias
            while (!accepted) {
                System.out.print("Enter your alias: ");
                alias = scanIn.nextLine();
                try {
                    outputStream.writeUTF(alias);
                    serverResponse = inputStream.readUTF();
                    if (serverResponse.equals("success"))
                        accepted = true;
                    else
                        System.out.println("Alias already in use... Pick again.");
                }
                catch (IOException e) {
                    System.err.println("Error while confirming alias. Exiting...");
                    return;
                }
            }

            // start threads and full functionality
            Thread receiver = new Thread(new MessageReceiver(inputStream));
            receiver.start();
            ChatClientLib.sendMessages(scanIn, new DataOutputStream(outputStream), alias);
            System.out.println("Closed receiving connection.");
        }

        catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}