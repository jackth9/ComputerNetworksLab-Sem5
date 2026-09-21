import java.io.*;
import java.net.*;

public class _2_Client {
    //SocketProgrammingEchoClientServer
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to Echo Server. Type messages (type 'exit' to quit):");
            String userMsg;
            while ((userMsg = console.readLine()) != null) {
                out.println(userMsg);
                if (userMsg.equalsIgnoreCase("exit")) break;
                System.out.println(in.readLine());
            }
        }
    }
}