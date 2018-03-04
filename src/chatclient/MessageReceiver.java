

package chatclient;

import java.io.*;

public class MessageReceiver implements Runnable {
    DataInputStream input;

    public MessageReceiver(DataInputStream input) {
        this.input = input;
    }

    public void run() {
        ChatClientLib.listenForMessages(input);
    }
}