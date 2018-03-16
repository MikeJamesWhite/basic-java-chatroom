/**
 * ClientAccepter.java
 * 
 * Runnable class for monitoring incoming connections and accepting new clients.
 * 
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

package chatserver;

import java.net.*;

public class ClientAccepter implements Runnable {
    private ServerSocket chatSocket;
    private ServerSocket fileSocket;

    public ClientAccepter(ServerSocket chatSocket, ServerSocket fileSocket) {
        this.chatSocket = chatSocket;
        this.fileSocket = fileSocket;
    }

    @Override
    public void run() {
        ChatServerLib.acceptConnections(chatSocket, fileSocket);
    }
}