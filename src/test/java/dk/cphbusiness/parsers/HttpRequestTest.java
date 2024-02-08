package dk.cphbusiness.parsers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest
{
    static String httpRequestString;
    static HttpRequest httpRequest;

    @BeforeAll
    static void initialize()
    {
        httpRequestString = "GET /pages/index.html HTTP/1.1" + System.lineSeparator() +
                "Host: www.example.com" + System.lineSeparator() +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3" + System.lineSeparator() +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" + System.lineSeparator() +
                "Accept-Language: en-US,en;q=0.5" + System.lineSeparator() +
                "Accept-Encoding: gzip, deflate, br" + System.lineSeparator() +
                "Connection: keep-alive" + System.lineSeparator() +
                "Upgrade-Insecure-Requests: 1";

        httpRequest = new HttpRequest(httpRequestString);

    }

    @Test
    void getHttpMethod()
    {
        assertEquals("GET", httpRequest.getHttpMethod());
    }

    @Test
    void getUrl()
    {
        assertEquals("/pages/index.html", httpRequest.getUrl());
    }

    @Test
    void getHttpVersion()
    {
        assertEquals("HTTP/1.1", httpRequest.getHttpVersion());
    }


    @Test
    void getHeaderMap()
    {
        Map<String, String> headerMap = httpRequest.getHeaderMap();
        assertEquals(7, headerMap.size());
    }


    @Test
    void getHost()
    {
        assertEquals("www.example.com", httpRequest.getHost());
    }
}