/**
 * ChatServer.java
 *
 * Driver program for hosting a chat server on a specified port.
 *
 * @author Michael White (WHTMIC023)
 * @version 04/03/2018
 */

package chatserver;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatServer {
    static final int DEFAULT_PORT = 50048;
    static AtomicBoolean running = new AtomicBoolean(true);

    public static void main(String[] args) {
        Scanner scanIn = new Scanner(System.in);
        System.out.print("Enter the port to host: ");
        int port = Integer.parseInt(scanIn.nextLine());
        Thread clientAccepter = null;

        try {
            ServerSocket ss = new ServerSocket(port);
            ss.setSoTimeout(1000);
            clientAccepter = new Thread(new ClientAccepter(ss));
            clientAccepter.start(); // start listening for clients on a new thread
            System.out.println("Hosting chat server on port " + String.valueOf(port));
        }
        catch (IOException e) {
            System.err.println("Error establishing server socket. Exiting...");
            return;
        }

        while (running.get()) {
            String in = scanIn.nextLine();
            if (in.equals("exit()")) {
                System.out.println("Closing client connections.");
                ChatServerLib.broadcastMessage("<disconnect>");
                System.out.println("Terminating program.");
                running.set(false);
            }
        }
    }
}