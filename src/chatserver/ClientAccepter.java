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
    private ServerSocket ss;

    public ClientAccepter(ServerSocket ss) {
        this.ss = ss;
    }

    @Override
    public void run() {
        ChatServerLib.acceptConnections(ss);
    }
}