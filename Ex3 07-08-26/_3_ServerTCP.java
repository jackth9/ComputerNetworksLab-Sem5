import java.io.*;
import java.net.*;
import java.util.Date;

public class _3_ServerTCP {
    //TCPDaytimeServer
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(1300)) {
            System.out.println("TCP Daytime Server running on port 1300...");
            while (true) {
                try (Socket client = server.accept()) {
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    out.println(new Date().toString());
                }
            }
        }
    }
}