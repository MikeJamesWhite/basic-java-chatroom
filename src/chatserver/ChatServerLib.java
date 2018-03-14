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
        while (ChatServer.running.get()) {
            try {
                s = ss.accept();
            }
            catch (SocketTimeoutException e) {
                continue;
            }
            catch (IOException e) {
                System.err.println("IOException accepting new socket connection.");
                continue;
            }
            ChatServerClient newClient = new ChatServerClient(s);
            synchronized (clients) {
                clients.add(newClient);
            }
            System.out.println("Client connected.");
        }
    }

    public static void listenTo(ChatServerClient client) {
        String msg;
        boolean accepted = false;

        // get a valid alias from client
        while (!accepted) {
            try {
                client.alias = client.input.readUTF();
                accepted = true;
                for (ChatServerClient c : clients) {
                    if (client.alias.equals(c.alias) && client != c) {
                        accepted = false;
                        client.output.writeUTF("fail");
                        break;
                    }
                }
            }
            catch (IOException e) {
                System.err.println("Error receiving client alias. Cancelling listen attempt.");
                clients.remove(client);
                return;
            }
        }
        try {
            client.output.writeUTF("success");
            broadcastMessage("<notification>|" + client.alias + " connected to the server.");
        }
        catch (IOException e) {
            msg = "<notification>|" + client.alias + " disconnected from the server.";
            synchronized(clients) {
                clients.remove(client);
            }
            broadcastMessage(msg);
            return;
        }

        // get messages from client
        while (ChatServer.running.get()) {
            try {
                if (client.input.available() < 1) {
                    continue;
                }
                msg = client.input.readUTF();
            }
            catch (IOException e) {
                System.err.println("Client disconnected. Closing listen thread.");
                msg = "<notification>|" + client.alias + " disconnected from the server.";
                synchronized(clients) {
                    clients.remove(client);
                }
                broadcastMessage(msg);
                return;
            }

            // parse message
            System.out.println(client.alias +": " + msg);
            String[] splitMsg = msg.split("\\|");

            System.out.println(splitMsg[0]);
            if (splitMsg[0].equals("<sendtoall>")) {
                msg = splitMsg[0] + "|" + client.alias + "|" + splitMsg[1];
                broadcastMessage(msg);
            }
            else if (splitMsg[0].equals("<sendprivate>")) {
                msg = splitMsg[0] + "|" + client.alias + "|" + splitMsg[2];
                privateMessage(client, splitMsg[1], msg);
            }
            else if (splitMsg[0].equals("<filetoall>")) {
                continue;
            }
            else if (splitMsg[0].equals("<fileprivate>")) {
                continue;
            }
            else if (splitMsg[0].equals("exit()")) {
                msg = "<notification>|" + client.alias + " disconnected from the server.";
                synchronized(clients) {
                    clients.remove(client);
                }
                broadcastMessage(msg);
            } 
        }
    }

    public static void broadcastMessage(String msg) {
        for (ChatServerClient c : clients) {
            new Thread ( 
                new Runnable () {
                    public void run() {
                        try {
                            c.output.writeUTF(msg);
                        }
                        catch (IOException e) {
                            String msg = "<notification>|" + c.alias + " disconnected from the server.";
                            synchronized(clients) {
                                clients.remove(c);
                            }
                            broadcastMessage(msg);
                        }
                    }
                }).start();
        }
    }

    public static void privateMessage(ChatServerClient sender, String recipient, String message) {
        for (ChatServerClient c : clients) {
            if (c.alias.equals(recipient)) {
                new Thread ( new Runnable () {
                    public void run() {
                        try {
                            c.output.writeUTF(message);
                        }
                        catch (IOException e) {
                            String msg = "<notification>|" + c.alias + " disconnected from the server.";
                            synchronized(clients) {
                                clients.remove(c);
                            }
                            broadcastMessage(msg);
                        }
                    }}).start();
                if (clients.contains(c)) {
                    try {
                        sender.output.writeUTF("<sendprivate>|you|" + message.split("\\|")[2]);
                    }
                    catch (IOException e) {
                        String msg = "<notification>|" + sender.alias + " disconnected from the server.";
                        synchronized(clients) {
                            clients.remove(sender);
                        }
                        broadcastMessage(msg);
                    }
                }
                return;
            }
        }
        try {
            System.out.println("PM recipient not found. Sending reply.");
            sender.output.writeUTF("<notification>|" + recipient + " is not online.");
        }
        catch (IOException e) {
            String msg = "<notification>|" + sender.alias + " disconnected from the server.";
            synchronized(clients) {
                clients.remove(sender);
            }
            broadcastMessage(msg);
        }
    }
}