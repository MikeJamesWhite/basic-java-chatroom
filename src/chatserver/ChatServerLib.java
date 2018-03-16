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

    public static void acceptConnections(ServerSocket chatSocket, ServerSocket fileSocket) {
        Socket clientChat;
        Socket clientFile;

        // send exit signal when server shuts down
        Thread onShutdown = new Thread
        ( new Runnable () {
            public void run() {
                for (ChatServerClient c : clients) {
                    try {
                        c.output.writeUTF("<disconnect>");
                    }
                    catch (IOException e) {}
                }
            }            
        });        
        Runtime.getRuntime().addShutdownHook(onShutdown);

        // accept client connections
        while (ChatServer.running.get()) {
            try {
                clientChat = chatSocket.accept();
                clientFile = fileSocket.accept();
            }
            catch (IOException e) {
                continue;
            }
            ChatServerClient newClient = new ChatServerClient(clientChat);
            newClient.addFileSocket(clientFile);
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

        // start file listener thread
        client.fileListener = new Thread ( new Runnable () {
            public void run () {
                listenForFiles(client);
            }
        });
        client.fileListener.start();

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
            else if (splitMsg[0].equals("<disconnect>")) {
                msg = "<notification>|" + client.alias + " disconnected from the server.";
                synchronized(clients) {
                    clients.remove(client);
                }
                broadcastMessage(msg);
            } 
        }
    }

    public static void listenForFiles(ChatServerClient sender) {
        // get file transfers from client
        while (ChatServer.running.get()) {
            final String msg;
            try {
                if (sender.fileInput.available() < 1) {
                    continue;
                }
                msg = sender.fileInput.readUTF();
            }
            catch (IOException e) { return; }

            // parse message
            System.out.println(sender.alias +": " + msg);
            String[] splitMsg = msg.split("\\|");

            if (splitMsg[0].equals("<filetoall>") || splitMsg[0].equals("<fileprivate>")) {
                int fileLength = Integer.parseInt(splitMsg[1]);
                byte[] file = new byte [fileLength];

                try {
                    int success = sender.fileInput.read(file, 0, fileLength);
                    if (success == -1) {
                        // transfer unsuccessful
                        System.err.println("File transfer unsuccessful");
                        return;
                    }
                }
                catch (IOException e) {
                    System.err.println("File read unsuccessful.");
                    return;
                }

                if (splitMsg[0].equals("<filetoall>")) {
                    System.out.println("Writing to all clients");
                    for (ChatServerClient c : clients) {

                        if (c != sender) {
                            new Thread (new Runnable() {
                                public void run() {
                                    sendFile(c, file, msg + "|" + sender.alias);
                                }
                            }).start();
                        }
                    }
                }
                else if (splitMsg[0].equals("<fileprivate>")) {
                    boolean foundClient = false;
                    for (ChatServerClient c : clients) {
                        if (c.alias.equals(splitMsg[3])) {
                            foundClient = true;
                            msg.replace(splitMsg[3], sender.alias);
                            new Thread (new Runnable() {
                                public void run() {
                                    sendFile(c, file, msg);
                                }
                            }).start();
                        }
                    }
                    if (!foundClient) {
                        try {
                            sender.output.writeUTF("<clientdoesnotexist>");
                        }
                        catch (IOException e) {
                            String errorMsg = "<notification>|" + sender.alias + " disconnected from the server.";
                            synchronized(clients) {
                                clients.remove(sender);
                            }
                            broadcastMessage(errorMsg);
                        }
                    }
                }
            }
        }
    }

    public static void sendFile(ChatServerClient recipient, byte[] file, String headerMsg) {
        String msg;
        try {
            // send header message to client
            System.out.println("Writing header to recipient " + recipient.alias);
            recipient.fileOutput.writeUTF(headerMsg);

            // wait for their response
            msg = recipient.fileInput.readUTF();
            String[] splitMsg = msg.split("\\|");

            boolean affirmative = false;
            if (splitMsg[0].equals("<accept>")) {
                affirmative = true;
            }

            // if affirmative, write file to client.
            if (affirmative) {
                System.out.println("Writing file to recipient " + recipient.alias);
                recipient.fileOutput.write(file, 0, file.length);
                System.out.println("Finished writing file.");
            }
        }
        catch (IOException e) {
            msg = "<notification>|" + recipient.alias + " disconnected from the server.";
            synchronized(clients) {
                clients.remove(recipient);
            }
            broadcastMessage(msg);
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
                        sender.output.writeUTF("<sendprivate>|you|" + "[to " + recipient + "] " + message.split("\\|")[2]);
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

        // only executes if client with specified alias is not found
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