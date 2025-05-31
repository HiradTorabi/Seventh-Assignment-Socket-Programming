package Client;


import java.io.DataInputStream;
import java.io.IOException;

public class ClientReceiver implements Runnable
{
    private DataInputStream dis;
    public ClientReceiver(DataInputStream dis)
    {
        this.dis = dis;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                // Listen for new messages from server
                String message = dis.readUTF();
                System.out.println(message);
            }
        }
        catch (IOException e)
        {
            System.out.println("Disconnected from chat or server closed.");
        }
    }

}