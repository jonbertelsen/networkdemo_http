package dk.cphbusiness.parsers;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest
{
    private String httpRequest;
    private String httpMethod;
    private String url;
    private String urlPath;
    private String httpVersion;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> formParams = new HashMap<>();
    private Map<String, String> queryParams = new HashMap<>();

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
            if (httpMethod.toUpperCase().equals("POST") || httpMethod.toUpperCase().equals("PUT"))
            {
                if (getHeaderValueByKey("Content-Type").equals("application/x-www-form-urlencoded"))
                {
                    parseRequestBodyAsFormData(lines);
                }
            }
        }
    }

    private void parseHeaderLines(String[] lines)
    {
        for (int i = 1; i < lines.length && !lines[i].isEmpty(); i++)
        {
            String[] headerLine = lines[i].split(":", 2);
            if (headerLine.length == 2)
            {
                headers.put(headerLine[0].trim(), headerLine[1].trim());
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
            if (url.contains("?"))
                parseQueryParams();
            else
                urlPath = url;
        }
    }

    private void parseQueryParams()
    {
        String[] urlParts = url.split("\\?");
        urlPath = urlParts[0];
        String[] queryParamPairs = urlParts[1].split("&");
        for (int i = 0; i < queryParamPairs.length; i++)
        {
            String[] queryPair = queryParamPairs[i].split("=");
            queryParams.put(queryPair[0], queryPair[1]);
        }
    }

    private void parseRequestBodyAsFormData(String[] lines)
    {
        for (int i = 0; i < lines.length; i++)
        {
            if (lines[i].isEmpty())
            {
                parseFormParameters(lines[i + 1]);
            }
        }
    }

    private void parseFormParameters(String line)
    {
        String[] parameterPairs = line.split("&");
        if (parameterPairs.length > 0)
        {
            for (int i = 0; i < parameterPairs.length; i++)
            {
                String[] param = parameterPairs[i].split("=");
                if (param.length == 2)
                {
                    formParams.put(param[0], param[1]);
                }
            }
        }
    }

    public String getHttpRequest()
    {
        return httpRequest;
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

    public Map<String, String> getHeaders()
    {
        return headers;
    }

    public String getHost()
    {
        return headers.getOrDefault("Host", "");
    }

    public Map<String, String> getFormParams()
    {
        return formParams;
    }

    public String getHeaderValueByKey(String key)
    {
        return headers.getOrDefault(key, "");
    }

    public String getBodyParam(String key)
    {
        return formParams.getOrDefault(key, "");
    }

    public String getUrlPath()
    {
        return urlPath;
    }

    public Map<String, String> getQueryParams()
    {
        return queryParams;
    }

    public String getQueryParam(String key)
    {
        return queryParams.getOrDefault(key, "");
    }
}
