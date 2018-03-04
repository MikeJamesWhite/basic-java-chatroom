/**
 * ChatServerLib.java
 * 
 * Library of useful methods which provide the core functionality for the chatroom server program.
 * 
 * @author Michael White (WHTMIC023)
 * @version 04/05/2018
 */

package chatserver;

import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServerLib {
    public static ArrayList<ChatServerClient> clients = new ArrayList<ChatServerClient>();

    public static void acceptConnections(ServerSocket ss) {
        Socket s;
        while (true) {
            try {
                s = ss.accept();
            }
            catch (IOException e) {
                System.err.println("Error accepting new socket connection. Exiting...");
                return;
            }
            ChatServerClient newClient = new ChatServerClient(s);
            synchronized (clients) {
                clients.add(newClient);
            }
            System.out.println("Client connected.");
        }
    }

    public static void listenTo(ChatServerClient client) {
        String in;
        try {
            client.alias = client.input.readUTF();
        }
        catch (IOException e) {
            System.err.println("Error receiving client alias. Cancelling listen attempt.");
            return;
        }
        while (true) {

            try {
                in = client.input.readUTF();
            }
            catch (IOException e) {
                System.err.println("Error receiving client message. Returning.");
                return;
            }
            synchronized (clients) {
                broadcastMessage(client.alias + "> " + in);
            }
            System.out.println(client.alias + "> " + in);
        }
    }

    public static void broadcastMessage(String msg) {
        for (ChatServerClient c : clients) {
            try {
                c.output.writeUTF(msg);
            }
            catch (IOException e) {
                System.err.println("Error writing output to client.");
            }
        }
    }
}