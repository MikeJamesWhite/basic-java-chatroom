/**
 * ChatServerClient.java
 * 
 * 
 * 
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

package chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

public class ChatServerClient {
    public String alias;
    Socket socket;
    public DataInputStream input;
    public DataOutputStream output;

    public ChatServerClient(Socket socket) {
        this.socket = socket;
        this.input = socket.DataInputStream();
        this.output = socket.DataOutputStream();
    }
}