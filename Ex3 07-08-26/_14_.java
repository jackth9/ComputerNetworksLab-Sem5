import java.io.*;
import java.net.*;

public class _14_ {
    //Simple Web Server (HTTP 1.0)
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("HTTP 1.0 Server running at http://localhost:8080/");

        while (true) {
            try (Socket client = server.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 OutputStream out = client.getOutputStream()) {

                String requestLine = in.readLine();
                System.out.println("Request: " + requestLine);

                String body = "<html><body><h1>Hello from Java HTTP Server</h1></body></html>";
                String response = "HTTP/1.0 200 OK\r\n" +
                                 "Content-Type: text/html\r\n" +
                                 "Content-Length: " + body.length() + "\r\n" +
                                 "Connection: close\r\n\r\n" + body;

                if (requestLine == null || requestLine.isEmpty()) {
                    break;
                 }
                out.write(response.getBytes("UTF-8"));
                out.flush();
            }
        }
        server.close();
    }
}