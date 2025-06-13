package Server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import Shared.User;
public class Server {
    // Predefined users for authentication
    private static final User[] users = {
            new User("babak", "1234"),
            new User("mmdhossain", "1234"),
            new User("bamdad", "1234"),
            new User("reza", "1234"),
            new User("mmd", "1234"),
    };

    // List of currently connected clients
    public static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws Exception
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(5003);
            System.out.println("Server started");
            while (true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("new client connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static boolean authenticate(String username, String password)
    {
        for (User user : users)
        {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
            {
                return true;
            }
        }
        return false;
    }
}