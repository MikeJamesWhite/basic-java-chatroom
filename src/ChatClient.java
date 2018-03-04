/**
 * ChatClient.java
 *
 * A client program for connecting to a chat server.
 *
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

import java.util.*;
import java.net.*;
import java.io.*;

public class ChatClient {
    static Scanner scanIn = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter the host: ");
        String host = scanIn.nextLine();
        System.out.print("Enter the port: ");
        int port = Integer.parseInt(scanIn.nextLine());
        System.out.println("Attempting connection to " + host + ":" + String.valueOf(port));
    }
}