import java.io.*;
import java.net.*;

public class _5_Client {
    //File Transfer using TCP
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 7000);
             InputStream in = socket.getInputStream();
             FileOutputStream fileOut = new FileOutputStream("received_sample.txt")) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fileOut.write(buffer, 0, bytesRead);
            }
            System.out.println("File received successfully.");
        }
    }
}