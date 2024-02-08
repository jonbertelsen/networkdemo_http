package dk.cphbusiness.parsers;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest
{
    private String httpRequest;
    private String httpMethod;
    private String url;
    private String httpVersion;
    private Map<String, String> headerMap = new HashMap<>();

    public HttpRequest(String httpRequest)
    {
        this.httpRequest = httpRequest;
        parse();
    }

    private void parse()
    {
        String[] lines = httpRequest.split(System.lineSeparator());
        if (lines.length > 0)
        {
            parseRequestLine(lines[0]);
            parseHeaderLines(lines);
        }

    }

    private void parseHeaderLines(String[] lines)
    {
        for (int i = 1; i < lines.length; i++)
        {
            String[] headerLine = lines[i].split(":", 2);
            if (headerLine.length == 2)
            {
                headerMap.put(headerLine[0].trim(), headerLine[1].trim());
            }
        }
    }

    private void parseRequestLine(String line)
    {
        String[] requestLine = line.split(" ");
        if (requestLine.length == 3)
        {
            httpMethod = requestLine[0];
            url = requestLine[1];
            httpVersion = requestLine[2];
        }
    }

    public String getHttpMethod()
    {
        return httpMethod;
    }

    public String getUrl()
    {
        return url;
    }

    public String getHttpVersion()
    {
        return httpVersion;
    }

    public Map<String, String> getHeaderMap()
    {
        return headerMap;
    }

    public String getHost()
    {
        return headerMap.getOrDefault("Host", "");
    }
}
