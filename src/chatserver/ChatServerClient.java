/**
 * ChatServerClient.java
 * 
 * Class to store the data field associated with an individual client who is connected to the server.
 * 
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

package chatserver;

import java.io.*;
import java.net.*;

public class ChatServerClient {
    public String alias;
    Socket socket;
    public DataInputStream input;
    public DataOutputStream output;

    public ChatServerClient(Socket socket) {
        this.socket = socket;
        try {
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
            new Thread(new ClientListener(this)).start();
        }
        catch (IOException e) {
            System.err.println("Error establishing new client. Exiting...");
            return;
        }
    }
}