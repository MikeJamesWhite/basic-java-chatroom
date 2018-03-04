/**
 * MessageReceiver.java
 * 
 * A runnable class which listens for incoming messages from the server.
 * 
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

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