import java.net.InetAddress;
import java.net.NetworkInterface;

public class _12_ {
    //MAC Address Retrieval using Java
    public static void main(String[] args) throws Exception {
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println("Current IP address: " + ip.getHostAddress());

        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        if (network != null) {
            byte[] mac = network.getHardwareAddress();
            if (mac != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                System.out.println("Current MAC address: " + sb.toString());
            } else {
                System.out.println("MAC address not found.");
            }
        }
    }
}