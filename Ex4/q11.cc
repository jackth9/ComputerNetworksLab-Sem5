#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"
#include "ns3/applications-module.h"
#include <iostream>
#include <string>

using namespace ns3;

int main(int argc, char *argv[]) {
    std::string tcpVariant = "ns3::TcpNewReno";
    CommandLine cmd(__FILE__);
    cmd.AddValue("tcpVariant", "TCP congestion-control TypeId to test", tcpVariant);
    cmd.Parse(argc, argv);
    Config::SetDefault("ns3::TcpL4Protocol::SocketType", StringValue(tcpVariant));

    NodeContainer nodes;
    nodes.Create(2);

    PointToPointHelper p2p;
    p2p.SetDeviceAttribute("DataRate", StringValue("2Mbps"));
    p2p.SetChannelAttribute("Delay", StringValue("30ms"));
    NetDeviceContainer devices = p2p.Install(nodes);

    InternetStackHelper stack;
    stack.Install(nodes);
    Ipv4AddressHelper address("10.1.1.0", "255.255.255.0");
    Ipv4InterfaceContainer interfaces = address.Assign(devices);

    PacketSinkHelper sink("ns3::TcpSocketFactory", InetSocketAddress(Ipv4Address::GetAny(), 8080));
    ApplicationContainer sinkApps = sink.Install(nodes.Get(1));
    sinkApps.Start(Seconds(0.0));

    BulkSendHelper source("ns3::TcpSocketFactory", InetSocketAddress(interfaces.GetAddress(1), 8080));
    source.SetAttribute("MaxBytes", UintegerValue(0));
    source.Install(nodes.Get(0)).Start(Seconds(1.0));

    Simulator::Stop(Seconds(10.0));
    Simulator::Run();
    Ptr<PacketSink> receiver = DynamicCast<PacketSink>(sinkApps.Get(0));
    std::cout << "TCP variant: " << tcpVariant << "\n"
              << "Received: " << receiver->GetTotalRx() << " bytes\n"
              << "Average throughput: " << receiver->GetTotalRx() * 8.0 / 9.0 / 1e6 << " Mbps\n";
    Simulator::Destroy();
    return 0;
}
