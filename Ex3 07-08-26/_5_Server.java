import java.io.*;
import java.net.*;

public class _5_Server {
    //File transfer using TCP
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7000);
        System.out.println("Waiting for client to send file request...");
        
        try (Socket socket = serverSocket.accept();
             FileInputStream fileIn = new FileInputStream("sample.txt");
             OutputStream out = socket.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileIn.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("File transfer complete.");
        }

        serverSocket.close();
    }
}