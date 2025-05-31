package Client;

import Shared.Message;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceiver extends Thread
{
    private final ObjectInputStream in;
    public ClientReceiver(Socket socket) throws Exception
    {
        this.in = new ObjectInputStream(socket.getInputStream());
    }
    public void run()
    {
        try
        {
            Message msg;
            while ((msg = (Message) in.readObject()) != null)
            {
                System.out.println(msg.getFrom() + ": " + msg.getContent());
            }
        }
        catch (Exception e)
        {
            System.out.println("Disconnected from server.");
        }
    }
}
