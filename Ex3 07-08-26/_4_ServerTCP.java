import java.io.*;
import java.net.*;

public class _4_ServerTCP {
    //TCPChatServer
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(6000);
        System.out.println("TCP Chat Server running on port 6000...");
        Socket socket = server.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));

        String rx;
        while (true) {
            if ((rx = in.readLine()) != null) {
                System.out.println("Client: " + rx);
            }
            if (rx != null && rx.equalsIgnoreCase("exit")) {
                System.out.println("Client has disconnected.");
                break;
            }
            System.out.print("Server: ");
            String tx = kb.readLine();
            out.println(tx);
        }
        server.close();
        socket.close();
    }
}