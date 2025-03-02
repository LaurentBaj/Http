package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {

    @Test
    void shouldReturnSuccessfullErrorCode() throws IOException {
        HttpServer server = new HttpServer(10001);
        HttpClient client = new HttpClient("localhost", 10001, "/echo");
        assertEquals(200, client.getStatusCode());
    }

    @Test
    void shouldReturnUnsuccessfulErrorCode() throws IOException {
        HttpServer server = new HttpServer(10002);
        HttpClient client = new HttpClient("localhost", 10002, "/echo?status=404");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldReturnHttpHeaders() throws IOException {
        HttpServer server = new HttpServer(10003);
        HttpClient client = new HttpClient("localhost", 10003, "/echo?body=HelloWorld");
        assertEquals("10", client.getResponseHeader("Content-Length"));
    }

    @Test
    void shouldReturnFileContent() throws IOException {
        HttpServer server = new HttpServer(10005);
        File documentRoot = new File("target");
        server.setContentRoot(documentRoot);
        String fileContent = "Hello " + new Date();
        Files.writeString(new File(documentRoot, "index.html").toPath(), fileContent);
        HttpClient client = new HttpClient("localhost", 10005, "/index.html");
        assertEquals(fileContent, client.getResponseBody());
    }

    @Test
    void shouldReturn404MissingFile() throws IOException {
        HttpServer server = new HttpServer(10006);
        server.setContentRoot(new File("target"));
        HttpClient client = new HttpClient("localhost", 10006, "/communistManifesto");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldReturnCorrectContentType() throws IOException {
        HttpServer server = new HttpServer(10007);
        File documentRoot = new File("target");
        server.setContentRoot(documentRoot);
        Files.writeString(new File(documentRoot, "Example.txt").toPath(), "Tyger Tyger burning bright");
        HttpClient client = new HttpClient("localhost", 10007, "/Example.txt");
        assertEquals("text/plain", client.getResponseHeader("Content-Type"));
    }


    @Test
    void shouldPostNewProduct() throws IOException {
        HttpServer server = new HttpServer(10008);
        String requestBody = "memberName=John&memberEmail=John@hotmail.com";
        HttpClient client = new HttpClient("localhost", 10008, "/api/newMember", "POST", requestBody);
        assertEquals(200, client.getStatusCode());
        assertEquals(List.of("John"), server.getMemberNames());
    }

    @Test
    void shouldReturnExistingProducts() throws IOException {
        HttpServer server = new HttpServer(10009);
        server.getMemberNames().add("Daniel");
        HttpClient client = new HttpClient("localhost", 10009, "/api/members");
        assertEquals("<ul><li>Daniel</li></ul>", client.getResponseBody());
    }

}
