/**
 * ClientListener.java
 * 
 * Runnable class for monitoring incoming messages from existing clients.
 * 
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

package chatserver;

public class ClientListener implements Runnable {
    ChatServerClient client;

    public ClientListener(ChatServerClient client) {
        this.client = client;
    }

    public void run() {
        ChatServerLib.listenTo(client);
    }
}