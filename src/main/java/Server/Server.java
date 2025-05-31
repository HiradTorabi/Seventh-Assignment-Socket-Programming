package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private static final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5050)) {
            System.out.println("✅ Server is running on port 5050...");

            while (true) {
                Socket socket = serverSocket.accept();

                // Create new handler thread
                ClientHandler handler = new ClientHandler(socket, clients);
                clients.add(handler);
                handler.start();  // Because ClientHandler extends Thread
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
