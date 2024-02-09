package dk.cphbusiness.parsers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestTest
{
    static String httpGetRequestString;
    static HttpRequest httpGetRequest;
    static String httpPostRequestString;
    static HttpRequest httpPostRequest;

    @BeforeAll
    static void initialize()
    {
        httpGetRequestString = "GET /pages/index.html?id=123&category=cars&year=2017 HTTP/1.1" + System.lineSeparator() +
                "Host: www.example.com" + System.lineSeparator() +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3" + System.lineSeparator() +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" + System.lineSeparator() +
                "Accept-Language: en-US,en;q=0.5" + System.lineSeparator() +
                "Accept-Encoding: gzip, deflate, br" + System.lineSeparator() +
                "Connection: keep-alive" + System.lineSeparator() +
                "Upgrade-Insecure-Requests: 1";

        httpGetRequest = new HttpRequest(httpGetRequestString);

        httpPostRequestString = "POST /pages/index.html HTTP/1.1" + System.lineSeparator() +
                "Host: www.example.com" + System.lineSeparator() +
                "Content-Type: application/x-www-form-urlencoded" + System.lineSeparator() +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3" + System.lineSeparator() +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" + System.lineSeparator() +
                "Accept-Language: en-US,en;q=0.5" + System.lineSeparator() +
                "Accept-Encoding: gzip, deflate, br" + System.lineSeparator() +
                "Connection: keep-alive" + System.lineSeparator() +
                "Upgrade-Insecure-Requests: 1" + System.lineSeparator() +
                System.lineSeparator() +
                "login=popeye&password=sailor&role=boss";

        httpPostRequest = new HttpRequest(httpPostRequestString);
    }

    @Test
    void getHttpMethod()
    {
        assertEquals("GET", httpGetRequest.getHttpMethod());
    }

    @Test
    void getUrl()
    {
        assertEquals("/pages/index.html?id=123&category=cars&year=2017", httpGetRequest.getUrl());
        assertEquals("/pages/index.html", httpPostRequest.getUrl());
    }

    @Test
    void getHttpVersion()
    {
        assertEquals("HTTP/1.1", httpGetRequest.getHttpVersion());
    }


    @Test
    void getHeaderMap()
    {
        Map<String, String> headerMap = httpGetRequest.getHeaders();
        assertEquals(7, headerMap.size());
    }

    @Test
    void getHost()
    {
        assertEquals("www.example.com", httpGetRequest.getHost());
    }

    @Test
    void getBodyMap()
    {
        Map<String, String> bodyMap = httpPostRequest.getFormParams();
        assertEquals(3, bodyMap.size());
    }

    @Test
    void getBody()
    {
        Map<String, String> bodyMap = httpPostRequest.getFormParams();
        assertEquals("popeye", bodyMap.get("login"));
        assertEquals("sailor", bodyMap.get("password"));
        assertEquals("boss", bodyMap.get("role"));
    }

    @Test
    void getBodyParam()
    {
        assertEquals("popeye", httpPostRequest.getBodyParam("login"));
        assertEquals("sailor", httpPostRequest.getBodyParam("password"));
        assertEquals("boss", httpPostRequest.getBodyParam("role"));
    }

    @Test
    void getQueryParam()
    {
        assertEquals("123", httpGetRequest.getQueryParam("id"));
        assertEquals("cars", httpGetRequest.getQueryParam("category"));
        assertEquals("2017", httpGetRequest.getQueryParam("year"));
        assertEquals("", httpGetRequest.getQueryParam("bennybadebold"));
    }

    @Test
    void getUrlPath()
    {
        assertEquals("/pages/index.html", httpGetRequest.getUrlPath());
        assertEquals("/pages/index.html", httpPostRequest.getUrlPath());
    }

    @Test
    void getQueryParams()
    {
        Map<String, String> queryParams = httpGetRequest.getQueryParams();
        assertEquals(3, queryParams.size());
    }
}