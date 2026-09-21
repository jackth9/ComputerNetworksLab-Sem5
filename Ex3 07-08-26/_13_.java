import java.net.*;

public class _13_ {
    //Broadcast Message Using UDP
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);

        String message = "DISCOVERY_REQUEST_FROM_CLIENT";
        byte[] buffer = message.getBytes();

        DatagramPacket packet = new DatagramPacket(
            buffer, 
            buffer.length, 
            InetAddress.getByName("255.255.255.255"), 
            8888
        );

        socket.send(packet);
        System.out.println("Broadcast message sent: " + message);
        socket.close();
    }
}