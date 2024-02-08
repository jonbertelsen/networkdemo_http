package dk.cphbusiness.demo03_httprequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HttpClient
{
    private static final int PORT = 9090;
    private static final String IP = "127.0.0.1";

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String response = "";

    public static void main(String[] args)
    {
        HttpClient client = new HttpClient();
        client.startConnection(IP, PORT);
        client.sendMessage("GET /pages/index.html HTTP/1.1" + System.lineSeparator() +
                "Host: localhost");
        System.out.println(client.response);
        client.stopConnection();
    }

    public void startConnection(String ip, int port)
    {
        try
        {
            System.out.println("Starting client socket talking to server on IP: " +
                    IP + " and port number: " + PORT);
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String msg)
    {
        List<String> responseLines = new ArrayList<>();

        try
        {
            out.println(msg);
            String line;

            do
            {
                line = in.readLine();
                if (line != null && !line.isEmpty())
                {
                    responseLines.add(line);
                }
            } while (line != null && !line.isEmpty());
        }
        catch (IOException ignored)
        {
            System.out.println("IO exception  - but who cares? It means that the input stream is closed by the server");
        }

        //    Vi kunne lige så godt have appended hver linie til en StringBuilder, men hey, en ArrayList
        //    gør at vi evt. senere kan hente hver linie ud for sig selv. Det skal vi nemlig bruge senere

        response = String.join(System.lineSeparator(), responseLines);
    }

    public void stopConnection()
    {
        try
        {
            System.out.println("Closing down client socket for demo03");
            in.close();
            out.close();
            clientSocket.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public String getResponse()
    {
        return response;
    }
}
