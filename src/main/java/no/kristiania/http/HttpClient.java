package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    private int statusCode;
    private Map<String, String> responseHeaders = new HashMap<>();
    private String responseBody;

    public HttpClient(final String hostname, int port, final String requestTarget) throws IOException {
        this(hostname, port, requestTarget, "GET", null);
    }

    public HttpClient(String hostname, int port, String requestTarget, String method, QueryString form) throws IOException{
        Socket socket = new Socket(hostname, port);

        String requestBody = form.getQueryString();

        HttpMessage requestMessage = new HttpMessage(socket);

    }

    public static void main(String[] args) throws IOException {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?status=404&Content-Type=text%2Fhtml&body=Hello+world");
        System.out.println(client.getResponseBody());
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseHeader(String headerName) {
        return responseHeaders.get(headerName);
    }

    public String getResponseBody() {
        return responseBody;
    }
}