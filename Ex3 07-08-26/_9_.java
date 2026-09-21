import java.io.*;
import java.net.*;

public class _9_ {
    //MultiThreadedServer 
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Multi-threaded Server listening on port 8000...");

        while (true) {
            Socket clientSocket = serverSocket.accept();

            if (clientSocket == null) {
                System.out.println("Server socket closed.");
                break;
            }

            System.out.println("New client connected: " + clientSocket.getRemoteSocketAddress());
            new Thread(new ClientHandler(clientSocket)).start();
        }
        serverSocket.close();
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                out.println("Connected to Multi-threaded Server. Type 'bye' to disconnect.");
                String msg;
                while ((msg = in.readLine()) != null) {
                    if (msg.equalsIgnoreCase("bye")) break;
                    out.println("Server Response: " + msg.toUpperCase());
                }
            } catch (IOException e) {
                System.err.println("Client handler exception: " + e.getMessage());
            }
        }
    }
}