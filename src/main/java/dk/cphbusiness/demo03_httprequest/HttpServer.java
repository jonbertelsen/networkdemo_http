package dk.cphbusiness.demo03_httprequest;

import dk.cphbusiness.parsers.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The purpose of this demo is to show a simple http request and response
 * The request should be sent from a browser
 * Author: Jon Bertelsen
 */

public class HttpServer
{
    private static final int PORT = 9090;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private HttpRequest httpRequest;

    public static void main(String[] args)
    {
        HttpServer server = new HttpServer();
        System.out.println("Now get this webpage from a browser, tiger!");
        System.out.println("On http://localhost:" + PORT);
        server.startConnection(PORT);
    }

    public void startConnection(int port)
    {
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            clientSocket = serverSocket.accept(); // wait for client request
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Read all lines from client in a string
            StringBuilder httpRequestString = new StringBuilder();

            String inputLine = "";

            while (in.ready())
            {
                inputLine = in.readLine();
                if (inputLine == null || inputLine.isEmpty())
                {
                    break;
                }
                httpRequestString.append(inputLine).append(System.lineSeparator());
            }

            HttpRequest httpRequest = new HttpRequest(httpRequestString.toString());

            String responseBody = "<html><head><title>PAUSE!!!</title></head>" +
                    "<body><h1>Hello World</h1>" +
                    "<p>Jeg hedder Jon</p>" +
                    "<p>Her er et nyt link til der hvor vi kom fra: _host_</p>" +
                    "<p>Her er vores url: _url_</p>" +
                    "<a href=\"https://dr.dk\">Danmarks Radios hjemmeside</a>" +
                    "</body></html>";

            String host = httpRequest.getHost();

            responseBody = responseBody.replace("_host_", host);
            responseBody = responseBody.replace("_url_", httpRequest.getUrl());

            String responseHeader = "HTTP/1.1 200 OK" + System.lineSeparator() +
                    "Date: Mon, 23 May 2022 22:38:34 GMT" + System.lineSeparator() +
                    "Server: Apache/2.4.1 (Unix)" + System.lineSeparator() +
                    "Content-Type: text/html; charset=UTF-8" + System.lineSeparator() +
                    "Content-Length: " + responseBody.length() + System.lineSeparator() +
                    "Connection: close" + System.lineSeparator();

            out.println(responseHeader);
            out.println(responseBody);
        }
        catch (IOException e)
        {
            System.out.println("An error has occured during network I/O");
            throw new RuntimeException(e);
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
            System.out.println("Closing down client socket on server");
            out.close();
            clientSocket.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
