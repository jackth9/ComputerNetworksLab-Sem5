import java.util.*;

public class _15_ {
    //Routing Table Simulation
    static class Route {
        String destinationNetwork;
        String subnetMask;
        String nextHop;
        String interfaceName;

        public Route(String dest, String mask, String nextHop, String iface) {
            this.destinationNetwork = dest;
            this.subnetMask = mask;
            this.nextHop = nextHop;
            this.interfaceName = iface;
        }
    }

    public static void main(String[] args) {
        List<Route> routingTable = new ArrayList<>();
        routingTable.add(new Route("192.168.1.0", "255.255.255.0", "0.0.0.0", "eth0"));
        routingTable.add(new Route("10.0.0.0", "255.0.0.0", "192.168.1.254", "eth0"));
        routingTable.add(new Route("0.0.0.0", "0.0.0.0", "192.168.1.1", "eth0")); // Default Gateway

        String targetIp = "10.0.5.22";
        System.out.println("Routing Lookup for Target IP: " + targetIp);

        Route matchedRoute = lookup(routingTable, targetIp);
        if (matchedRoute != null) {
            System.out.println("Match Found -> Next Hop: " + matchedRoute.nextHop + 
                               " via Interface: " + matchedRoute.interfaceName);
        } else {
            System.out.println("No route found!");
        }
    }

    private static Route lookup(List<Route> table, String ipStr) {
        // Simple demonstration of routing lookup algorithm logic
        for (Route route : table) {
            if (route.destinationNetwork.equals("0.0.0.0")) return route; // Default fallback
        }
        return null;
    }
}