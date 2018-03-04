

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