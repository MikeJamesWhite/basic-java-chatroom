/**
 * ChatServer.java
 *
 * A server program for hosting a chat server.
 *
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

package chatserver;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ChatServer {
    static final int DEFAULT_PORT = 50048;

    public static void main(String[] args) {
        Scanner scanIn = new Scanner(System.in);
        System.out.print("Enter the port to host: ");
        int port = Integer.parseInt(scanIn.nextLine());

        try {
            ServerSocket ss = new ServerSocket(port);
            new Thread(new ClientAccepter(ss)).start(); // start listening for clients on a new thread
            System.out.println("Hosting chat server on port " + String.valueOf(port));
        }
        catch (IOException e) {
            System.err.println("Error establishing server socket. Exiting...");
            return;
        }
    }
}