/**
 * ChatServer.java
 *
 * A server program for hosting a chat server.
 *
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

package chatserver;

import java.net.*;
import java.io.*;

public class ChatServer {
    static final int DEFAULT_PORT = 50048;

    public static void main(String[] args) {
        ServerSocket ss = new ServerSocket(DEFAULT_PORT);
        new Thread(new AcceptClients(ss)).start(); // start listening for clients on a new thread
    }
}

private class AcceptClients implements Runnable {
    private ServerSocket ss;

    public AcceptClientThread(ServerSocket ss) {
        this.ss = ss;
    }

    @Override
    public void run() {
        ChatServerLib.acceptConnections(ss);
    }
}