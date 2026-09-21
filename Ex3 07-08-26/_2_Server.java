import java.io.*;
import java.net.*;

public class _2_Server {
    //SocketProgrammingEchoClientServer
    public static void main(String[] args) throws IOException {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Echo Server listening on port " + port);
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("exit")) break;
                System.out.println("Received: " + message);
                out.println("Echo: " + message);
            }
        }
    }
}