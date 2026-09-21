import java.net.InetAddress;

public class _8_ {
    //Ping Utility (ICMP-like simulation)
    public static void main(String[] args) throws Exception {
        String host = "google.com";
        int timeoutMs = 3000;

        System.out.println("Pinging " + host + "...");
        InetAddress address = InetAddress.getByName(host);

        for (int i = 1; i <= 4; i++) {
            long startTime = System.currentTimeMillis();
            boolean reachable = address.isReachable(timeoutMs);
            long endTime = System.currentTimeMillis();

            if (reachable) {
                System.out.println("Reply from " + address.getHostAddress() + 
                                   ": seq=" + i + " time=" + (endTime - startTime) + "ms");
            } else {
                System.out.println("Request timed out for seq=" + i);
            }
            Thread.sleep(1000);
        }
    }
}