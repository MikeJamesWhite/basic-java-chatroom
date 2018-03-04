/**
 * ChatClient.java
 *
 * A client program for connecting to a chat server.
 *
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

import java.util.*;

import chatserver.ChatServerClient;

import java.net.*;
import java.io.*;

public class ChatClient {
    static Scanner scanIn = new Scanner(System.in);
    static final int DEFAULT_PORT = 50048;
    static final String DEFAULT_HOST = "kingkong.zapto.org";

    public static void main(String[] args) {
        System.out.print("Enter the host: ");
        String host = scanIn.nextLine();
        System.out.print("Enter the port: ");
        int port = Integer.parseInt(scanIn.nextLine());
        System.out.println("Attempting connection to " + host + ":" + String.valueOf(port));
    }
}

private class ClientListener implements Runnable {
    ChatServerClient client;

    public ClientListener(ChatServerClient client) {
        this.client = client;
    }

    public void run() {
        listenTo(client);
    }
}