/**
 * ChatClientLib.java
 * 
 * A library of useful methods which are used to support the chatroom client driver program.
 * 
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

package chatclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatClientLib {

    public static void listenForMessages(DataInputStream input) {
        String msg = "";
        while (ChatClient.running.get()) {
            try {
                if (input.available() < 1) {
                    continue;
                }
                msg = input.readUTF();
            }
            catch (IOException e) {
                System.out.println("Input from server disconnected. Exiting program.");
                ChatClient.running.set(false);
                return;
            }
            System.out.println(msg);
            if (msg.equals("Server Disconnected.")) {
                System.out.println("Closing program");
                ChatClient.running.set(false);
                System.exit(0);
            }
        }
    }

    public static void sendMessages(Scanner scanIn, DataOutputStream output, String alias) {
        while (ChatClient.running.get()) {
            System.out.print(alias + "> ");
            String msg = scanIn.nextLine();
            try {
                output.writeUTF(msg);
            }
            catch (IOException e) {
                System.out.println("Output to server disconnected.");
                ChatClient.running.set(false);
                return;
            }
            if (msg.equals("exit()")) {
                System.out.println("Disconnecting...");
                ChatClient.running.set(false);
                return;
            }
        }
    }
}