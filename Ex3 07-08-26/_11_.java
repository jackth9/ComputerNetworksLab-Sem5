import java.net.InetAddress;

public class _11_ {
    //IP Address Validator and Subnet Calculator
    public static void main(String[] args) throws Exception {
        String ipStr = "192.168.1.75";
        int prefix = 24; // CIDR /24

        int mask = 0xffffffff << (32 - prefix);
        int ip = ipToInt(InetAddress.getByName(ipStr).getAddress());

        int network = ip & mask;
        int broadcast = network | ~mask;

        System.out.println("IP Address:        " + intToIp(ip));
        System.out.println("Subnet Mask:       " + intToIp(mask));
        System.out.println("Network Address:   " + intToIp(network));
        System.out.println("Broadcast Address: " + intToIp(broadcast));
        System.out.println("Usable Host Range: " + intToIp(network + 1) + " - " + intToIp(broadcast - 1));
    }

    private static int ipToInt(byte[] bytes) {
        int result = 0;
        for (byte b : bytes) {
            result = (result << 8) | (b & 0xFF);
        }
        return result;
    }

    private static String intToIp(int ip) {
        return ((ip >> 24) & 0xFF) + "." +
               ((ip >> 16) & 0xFF) + "." +
               ((ip >> 8) & 0xFF) + "." +
               (ip & 0xFF);
    }
}