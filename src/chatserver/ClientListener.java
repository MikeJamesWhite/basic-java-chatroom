

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