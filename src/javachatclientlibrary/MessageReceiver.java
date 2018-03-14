/**
 * MessageReceiver.java
 * 
 * A runnable class which listens for incoming messages from the server.
 * 
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

package javachatclientlibrary;

import java.io.*;
import javachatclientgui.ChatScreen;

public class MessageReceiver implements Runnable {
    DataInputStream input;
    ChatScreen chatScreen;

    public MessageReceiver(DataInputStream input, ChatScreen chatScreen) {
        this.input = input;
        this.chatScreen = chatScreen;
    }

    public void run() {
        ChatClientLib.listenForMessages(input, chatScreen);
    }
}