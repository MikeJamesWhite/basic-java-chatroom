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

public class ChatClientLib {
    public static void listenForMessages(DataInputStream input) {
        String msg = "";
        while (true) {
            try { 
                msg = input.readUTF();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(msg);
        }
    }

    public static void sendMessages(Scanner scanIn, DataOutputStream output) {
        System.out.print("Enter your alias: ");
        String alias = scanIn.nextLine();
        try {
            output.writeUTF(alias);
        }
        catch (IOException e) {
            System.err.println("Error writing alias. Returning...");
            return;
        }
        while (true) {
            System.out.print(alias + "> ");
            String msg = scanIn.nextLine();
            try {
                output.writeUTF(msg);
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
            if (msg.equals("exit()")) {
                System.out.println("Disconnecting...");
                return;
            }
        }
    }
}