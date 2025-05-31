
package Server;

import Shared.Message;

import java.io.*;
import java.net.Socket;
import java.util.Set;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final ObjectOutputStream objectOut;
    private final ObjectInputStream objectIn;
    private final Set<ClientHandler> allClients;
    private String username;

    public ClientHandler(Socket socket, Set<ClientHandler> allClients) throws IOException {
        this.socket = socket;
        this.allClients = allClients;
        this.objectOut = new ObjectOutputStream(socket.getOutputStream());
        this.objectIn = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message msg = (Message) objectIn.readObject();

                // Handle login
                if (msg.getType().equals("LOGIN")) {
                    username = msg.getFrom();
                    System.out.println(username + " connected.");
                    broadcast(new Message("Server", "CHAT", username + " has joined."));
                }

                // Handle chat
                else if (msg.getType().equals("CHAT")) {
                    broadcast(msg);
                }

                // Handle exit
                else if (msg.getType().equals("EXIT")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("❌ " + username + " disconnected.");
        } finally {
            try {
                socket.close();
                allClients.remove(this);
                broadcast(new Message("Server", "CHAT", username + " has left."));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcast(Message message) {
        for (ClientHandler client : allClients) {
            try {
                client.objectOut.writeObject(message);
            } catch (IOException ignored) {}
        }
    }
}
