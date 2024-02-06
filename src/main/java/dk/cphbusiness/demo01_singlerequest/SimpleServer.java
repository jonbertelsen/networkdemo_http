package dk.cphbusiness.demo01_singlerequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;

/*
 * Purpose of this demo is to show the most basic use of sockets with inspiration
 * from: https://www.baeldung.com/a-guide-to-java-sockets
 * The server only accepts one client and only one message from the client before
 * closing the connection
 * Author: Thomas Hartmann and Jon Bertelsen
 */
public class SimpleServer
{

    private static final int PORT = 9090;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args)
    {
        SimpleServer server = new SimpleServer();
        server.startConnection(PORT);
    }

    public void startConnection(int port)
    {
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            clientSocket = serverSocket.accept(); // blocking call
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String greeting = in.readLine();
            System.out.println(greeting);
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            //out.println("Hello SimpleClient, Greetings from SimpleServer");
            String text = String.format("Dags dato er %s og klokken er %s", date, time);
            String text2 = "Dags dato er %1 og klokken er %2";
            text2 = text2.replace("%1", date.toString());
            text2 = text2.replace("%2", time.toString());
            out.print(text);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            stopConnection();
        }
    }

    public void stopConnection()
    {
        try
        {
            System.out.println("Closing down socket ...");
            in.close();
            out.close();
            clientSocket.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
