#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"
#include "ns3/applications-module.h"
#include "ns3/netanim-module.h"

using namespace ns3;

int main(int argc, char *argv[]) {
    NodeContainer nodes;
    nodes.Create(2);

    PointToPointHelper p2p;
    p2p.SetDeviceAttribute("DataRate", StringValue("5Mbps"));
    p2p.SetChannelAttribute("Delay", StringValue("2ms"));
    NetDeviceContainer devices = p2p.Install(nodes);

    InternetStackHelper stack;
    stack.Install(nodes);
    Ipv4AddressHelper address("10.1.1.0", "255.255.255.0");
    Ipv4InterfaceContainer interfaces = address.Assign(devices);

    UdpEchoServerHelper server(9);
    server.Install(nodes.Get(1)).Start(Seconds(1.0));

    UdpEchoClientHelper client(interfaces.GetAddress(1), 9);
    client.SetAttribute("MaxPackets", UintegerValue(3));
    client.Install(nodes.Get(0)).Start(Seconds(2.0));

    // NetAnim Configuration
    AnimationInterface anim("network-animation.xml");
    anim.SetConstantPosition(nodes.Get(0), 10.0, 20.0);
    anim.SetConstantPosition(nodes.Get(1), 40.0, 20.0);
    anim.UpdateNodeDescription(nodes.Get(0), "Client");
    anim.UpdateNodeDescription(nodes.Get(1), "Server");
    anim.UpdateNodeColor(nodes.Get(0), 255, 0, 0); // Red
    anim.UpdateNodeColor(nodes.Get(1), 0, 255, 0); // Green

    Simulator::Stop(Seconds(10.0));
    Simulator::Run();
    Simulator::Destroy();
    return 0;
}
