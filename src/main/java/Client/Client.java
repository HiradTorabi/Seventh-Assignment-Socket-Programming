
package Client;

import Shared.Message;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static String username;
    private static Socket socket;
    private static ObjectOutputStream objectOut;
    private static ObjectInputStream objectIn;

    public static void main(String[] args) throws Exception {
        try {
            socket = new Socket("localhost", 5050);
            objectOut = new ObjectOutputStream(socket.getOutputStream());
            objectIn = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            username = scanner.nextLine();

            // Send login message
            objectOut.writeObject(new Message(username, "LOGIN", ""));

            // Start thread to receive incoming messages
            new Thread(() -> {
                try {
                    while (true) {
                        Message msg = (Message) objectIn.readObject();
                        System.out.println(msg.getFrom() + ": " + msg.getContent());
                    }
                } catch (Exception e) {
                    System.out.println("❌ Disconnected from server.");
                }
            }).start();

            // Main send loop
            while (true) {
                String input = scanner.nextLine();

                if (input.equals("/exit")) {
                    objectOut.writeObject(new Message(username, "EXIT", ""));
                    break;
                } else {
                    objectOut.writeObject(new Message(username, "CHAT", input));
                }
            }

        } catch (Exception e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}
