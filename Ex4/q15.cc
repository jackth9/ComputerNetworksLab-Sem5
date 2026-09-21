#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"
#include "ns3/applications-module.h"

using namespace ns3;

int main(int argc, char *argv[]) {
    // Topology: Node 0 --- Node 1 --- Node 2
    NodeContainer nodes;
    nodes.Create(3);

    PointToPointHelper p2p;
    p2p.SetDeviceAttribute("DataRate", StringValue("5Mbps"));
    p2p.SetChannelAttribute("Delay", StringValue("2ms"));

    NetDeviceContainer dev01 = p2p.Install(nodes.Get(0), nodes.Get(1));
    NetDeviceContainer dev12 = p2p.Install(nodes.Get(1), nodes.Get(2));

    InternetStackHelper stack;
    stack.Install(nodes);

    Ipv4AddressHelper address;
    address.SetBase("10.1.1.0", "255.255.255.0");
    Ipv4InterfaceContainer int01 = address.Assign(dev01);

    address.SetBase("10.1.2.0", "255.255.255.0");
    Ipv4InterfaceContainer int12 = address.Assign(dev12);

    // Populate Routing Tables across hops
    Ipv4GlobalRoutingHelper::PopulateRoutingTables();

    UdpEchoServerHelper server(9);
    server.Install(nodes.Get(2)).Start(Seconds(1.0));

    // Ping Node 2 (10.1.2.2) from Node 0 (10.1.1.1)
    UdpEchoClientHelper client(int12.GetAddress(1), 9);
    client.SetAttribute("MaxPackets", UintegerValue(3));
    client.SetAttribute("Interval", TimeValue(Seconds(1.0)));
    client.Install(nodes.Get(0)).Start(Seconds(2.0));

    // Output routing tables to console at 1.5 seconds
    Ptr<OutputStreamWrapper> routingStream = Create<OutputStreamWrapper>(&std::cout);
    Ipv4RoutingHelper::PrintRoutingTableAllAt(Seconds(1.5), routingStream);

    Simulator::Stop(Seconds(6.0));
    Simulator::Run();
    Simulator::Destroy();
    return 0;
}
