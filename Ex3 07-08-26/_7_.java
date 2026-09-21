import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class _7_ {
    //Simple DNS lookup resolver
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Domain Name (e.g., google.com): ");
        String host = scanner.nextLine();

        try {
            InetAddress[] addresses = InetAddress.getAllByName(host);
            System.out.println("Host Name: " + host);
            for (InetAddress addr : addresses) {
                System.out.println("IP Address: " + addr.getHostAddress());
            }
        } catch (UnknownHostException e) {
            System.err.println("Could not resolve host: " + host);
        }
        scanner.close();
    }
}