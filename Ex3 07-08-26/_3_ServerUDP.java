import java.net.*;
import java.util.Date;

public class _3_ServerUDP {
    //UDPDaytimeServer
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(1300);
        System.out.println("UDP Daytime Server running on port 1300...");
        byte[] buffer = new byte[256];

        while (true) {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);
            
            if (new String(request.getData(), 0, request.getLength()).equalsIgnoreCase("exit")) {
                break;
            }

            String timeStr = new Date().toString();
            byte[] timeData = timeStr.getBytes();

            DatagramPacket response = new DatagramPacket(
                timeData, timeData.length, request.getAddress(), request.getPort()
            );
            socket.send(response);
        }
        socket.close();
    }
}