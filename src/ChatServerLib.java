/**
 * ChatServerLib.java
 * 
 * 
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
        while (true) {
            Socket s = ss.accept();
            ChatServerClient newClient = new ChatServerClient(s);
            synchronized (clients) {
                clients.append(newClient);
            }
        }
    }

    public static void listenTo(ChatServerClient client) {
        while (true) {

        }
    }

    public static void broadcastMessage(String msg) {
        for (ChatServerClient c : clients) {
            c.socket;
        }
    }
}