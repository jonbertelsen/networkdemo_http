package dk.cphbusiness.demo02_multiplerequests;

import dk.cphbusiness.demo03_httprequest.HttpClient;
import dk.cphbusiness.demo03_httprequest.HttpServer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Demo03HttpClientTest
{

    private static final int PORT = 9090;
    private static final String IP = "127.0.0.1";

    private static HttpServer httpServer = new HttpServer();

    @BeforeAll
    public static void setup()
    {
        System.out.println("setup");
        new Thread(() -> httpServer.startConnection(PORT)).start();
    }

    @BeforeEach
    public void setupEach()
    {
        System.out.println("setupEach");
    }

    @AfterAll
    public static void tearDown()
    {
        System.out.println("tearDown");
        httpServer.stopConnection();
    }

    @Test
    @DisplayName("Test http server and client")
    public void testHttpServerAndClient()
    {

        HttpClient client = new HttpClient();
        client.startConnection("localhost", PORT);

        client.sendMessage("GET /pages/index.html HTTP/1.1\n" +
                "Host: localhost");

        String expected = "HTTP/1.1 200 OK" + System.lineSeparator() +
                "Date: Mon, 23 May 2022 22:38:34 GMT" + System.lineSeparator() +
                "Server: Apache/2.4.1 (Unix)" + System.lineSeparator() +
                "Content-Type: text/html; charset=UTF-8" + System.lineSeparator() +
                "Content-Length: 157" + System.lineSeparator() +
                "Connection: close" + System.lineSeparator() +
                System.lineSeparator() +
                "<html><head><title>PAUSE!!!</title></head><body><h1>Hello World</h1><p>Jeg hedder Jon</p><a href=\"https://dr.dk\">Danmarks Radios hjemmeside</a></body></html>";

        String actual = client.getResponse();
        assertEquals(expected, actual);
        client.stopConnection();
    }

}