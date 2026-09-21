import java.net.*;
import java.util.Scanner;

public class _4_ServerUDP {
    //UDPChatPeer
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java UDPChatPeer <localPort> <remotePort>");
            return;
        }
        int localPort = Integer.parseInt(args[0]);
        int remotePort = Integer.parseInt(args[1]);

        DatagramSocket socket = new DatagramSocket(localPort);
        Scanner scanner = new Scanner(System.in);

        // Receiver Thread
        new Thread(() -> {
            byte[] buf = new byte[1024];
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    String msg = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("\nPeer: " + msg);
                } catch (Exception e) { break; }
            }
        }).start();

        // Sender Loop
        while (true) {
            String msg = scanner.nextLine();
            if (msg.equalsIgnoreCase("exit")) {
                break;
            }
            byte[] buf = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), remotePort);
            socket.send(packet);
        }

        socket.close();
        scanner.close();
    }
}