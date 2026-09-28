#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/csma-module.h"
#include "ns3/internet-module.h"
#include "ns3/applications-module.h"

using namespace ns3;

int main(int argc, char *argv[]) {
    NodeContainer nodes;
    nodes.Create(3);

    CsmaHelper csma;
    csma.SetChannelAttribute("DataRate", StringValue("100Mbps"));
    csma.SetChannelAttribute("Delay", TimeValue(NanoSeconds(6560)));
    NetDeviceContainer devices = csma.Install(nodes);

    InternetStackHelper stack;
    stack.Install(nodes);
    Ipv4AddressHelper address("10.1.1.0", "255.255.255.0");
    address.Assign(devices);

    Ipv4Address multicastSource("10.1.1.1");
    Ipv4Address multicastGroup("225.1.2.4");

    // Static Multicast Route Configuration
    Ipv4StaticRoutingHelper multicast;
    Ptr<Node> sourceNode = nodes.Get(0);
    Ptr<NetDevice> sourceDevice = devices.Get(0);
    multicast.AddMulticastRoute(sourceNode, multicastSource, multicastGroup, sourceDevice, NetDeviceContainer(sourceDevice));

    uint16_t port = 9;
    PacketSinkHelper sink("ns3::UdpSocketFactory", InetSocketAddress(multicastGroup, port));
    ApplicationContainer sinkApps = sink.Install(NodeContainer(nodes.Get(1), nodes.Get(2)));
    sinkApps.Start(Seconds(1.0));

    OnOffHelper onoff("ns3::UdpSocketFactory", Address(InetSocketAddress(multicastGroup, port)));
    onoff.SetAttribute("DataRate", StringValue("1Mbps"));
    onoff.SetAttribute("PacketSize", UintegerValue(1024));
    ApplicationContainer sourceApp = onoff.Install(nodes.Get(0));
    sourceApp.Start(Seconds(2.0));

    Simulator::Stop(Seconds(10.0));
    Simulator::Run();
    Simulator::Destroy();
    return 0;
}
