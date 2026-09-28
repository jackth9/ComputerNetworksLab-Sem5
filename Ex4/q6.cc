#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"
#include "ns3/applications-module.h"
#include "ns3/traffic-control-module.h"
#include <iostream>

using namespace ns3;

int main(int argc, char *argv[]) {
    NodeContainer nodes;
    nodes.Create(2);

    // Create a bottleneck link
    PointToPointHelper p2p;
    p2p.SetDeviceAttribute("DataRate", StringValue("100Kbps"));
    p2p.SetChannelAttribute("Delay", StringValue("5ms"));
    p2p.SetQueue("ns3::DropTailQueue<Packet>", "MaxSize", StringValue("1p"));

    NetDeviceContainer devices = p2p.Install(nodes);
    InternetStackHelper stack;
    stack.Install(nodes);
    TrafficControlHelper trafficControl;
    trafficControl.SetRootQueueDisc("ns3::FifoQueueDisc", "MaxSize", StringValue("5p"));
    QueueDiscContainer bottleneckQueue = trafficControl.Install(devices.Get(0));
    Ipv4AddressHelper address("10.1.1.0", "255.255.255.0");
    Ipv4InterfaceContainer interfaces = address.Assign(devices);

    UdpServerHelper server(9);
    ApplicationContainer serverApp = server.Install(nodes.Get(1));
    serverApp.Start(Seconds(1.0));
    serverApp.Stop(Seconds(8.0));

    UdpClientHelper client(interfaces.GetAddress(1), 9);
    client.SetAttribute("MaxPackets", UintegerValue(1000));
    client.SetAttribute("Interval", TimeValue(Seconds(0.005))); // Fast transmission
    client.SetAttribute("PacketSize", UintegerValue(1024));

    ApplicationContainer clientApp = client.Install(nodes.Get(0));
    clientApp.Start(Seconds(2.0));

    Simulator::Stop(Seconds(8.0));
    Simulator::Run();
    Ptr<UdpServer> receiver = DynamicCast<UdpServer>(serverApp.Get(0));
    Ptr<QueueDisc> queueDisc = bottleneckQueue.Get(0);
    std::cout << "UDP packets sent: 1000, received: " << receiver->GetReceived()
              << ", lost: " << receiver->GetLost() << std::endl;
    std::cout << "Bottleneck queue-disc packets dropped: "
              << queueDisc->GetStats().nTotalDroppedPackets << std::endl;
    Simulator::Destroy();
    return 0;
}
