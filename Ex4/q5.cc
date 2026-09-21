#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"
#include "ns3/applications-module.h"

using namespace ns3;

int main(int argc, char *argv[]) {
    NodeContainer nodes;
    nodes.Create(3); // Node 0 (TCP), Node 1 (UDP) -> Node 2 (Sink)

    PointToPointHelper p2p;
    p2p.SetDeviceAttribute("DataRate", StringValue("5Mbps"));
    p2p.SetChannelAttribute("Delay", StringValue("2ms"));

    NetDeviceContainer dev02 = p2p.Install(nodes.Get(0), nodes.Get(2));
    NetDeviceContainer dev12 = p2p.Install(nodes.Get(1), nodes.Get(2));

    InternetStackHelper stack;
    stack.Install(nodes);

    Ipv4AddressHelper addr;
    addr.SetBase("10.1.1.0", "255.255.255.0");
    Ipv4InterfaceContainer int02 = addr.Assign(dev02);
    addr.SetBase("10.1.2.0", "255.255.255.0");
    Ipv4InterfaceContainer int12 = addr.Assign(dev12);

    // TCP Setup
    PacketSinkHelper tcpSink("ns3::TcpSocketFactory", InetSocketAddress(Ipv4Address::GetAny(), 8080));
    ApplicationContainer tcpSinkApp = tcpSink.Install(nodes.Get(2));
    tcpSinkApp.Start(Seconds(0.0));

    BulkSendHelper tcpSource("ns3::TcpSocketFactory", InetSocketAddress(int02.GetAddress(1), 8080));
    tcpSource.SetAttribute("MaxBytes", UintegerValue(0)); // Unlimited
    ApplicationContainer tcpApp = tcpSource.Install(nodes.Get(0));
    tcpApp.Start(Seconds(1.0));

    // UDP Setup
    PacketSinkHelper udpSink("ns3::UdpSocketFactory", InetSocketAddress(Ipv4Address::GetAny(), 9090));
    ApplicationContainer udpSinkApp = udpSink.Install(nodes.Get(2));
    udpSinkApp.Start(Seconds(0.0));

    OnOffHelper udpSource("ns3::UdpSocketFactory", InetSocketAddress(int12.GetAddress(1), 9090));
    udpSource.SetAttribute("DataRate", StringValue("3Mbps"));
    udpSource.SetAttribute("PacketSize", UintegerValue(1024));
    ApplicationContainer udpApp = udpSource.Install(nodes.Get(1));
    udpApp.Start(Seconds(1.0));

    Simulator::Stop(Seconds(10.0));
    Simulator::Run();
    Simulator::Destroy();
    return 0;
}
