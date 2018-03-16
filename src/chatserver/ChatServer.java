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
    static final int DEFAULT_PORT = 50047;
    static AtomicBoolean running = new AtomicBoolean(true);
    

    public static void main(String[] args) {
        Thread clientAccepter = null;
        Scanner scanIn = new Scanner(System.in);
        int port, filePort;

        while (true) {
            System.out.print("Use default settings (y/n): ");
            String in = scanIn.nextLine();
            if (in.equals("y")) {
                port = DEFAULT_PORT;
                filePort = DEFAULT_PORT + 1;
                break;
            }
            else if (in.equals("n")) {
                System.out.print("Enter the port to host chat: ");
                port = Integer.parseInt(scanIn.nextLine());
                System.out.print("Enter the port to host file transfer: ");
                filePort = Integer.parseInt(scanIn.nextLine());
                break;
            }
            else
                System.out.println("Unrecognized command...");
        }

        try {
            ServerSocket chatSocket = new ServerSocket(port);
            ServerSocket fileSocket = new ServerSocket(filePort);
            chatSocket.setSoTimeout(1000);
            clientAccepter = new Thread(new ClientAccepter(chatSocket, fileSocket));
            clientAccepter.start(); // start listening for clients on a new thread
            System.out.println("Hosting chat server on port " + String.valueOf(port) + " with file transfer on port " + String.valueOf(filePort) + ".");
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