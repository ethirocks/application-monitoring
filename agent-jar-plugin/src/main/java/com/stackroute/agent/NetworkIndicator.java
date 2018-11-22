package com.stackroute.agent;

import org.springframework.stereotype.Component;
import oshi.hardware.NetworkIF;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

@Component
public class NetworkIndicator {


    public Map getNetworkInfo() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        LinkedHashMap<String,LinkedHashMap<String, Object>> linkedHashMap1 = new LinkedHashMap<>();
        int count=0;
        for (NetworkInterface netint : Collections.list(interfaces)) {
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            if (!netint.isLoopback() && netint.getHardwareAddress() != null) {
                NetworkIF netIF = new NetworkIF();
                netIF.setNetworkInterface(netint);
                netIF.updateNetworkStats();
                long download1 = netIF.getBytesRecv();
                long upload1 = netIF.getBytesSent();
                long packetin1 = netIF.getPacketsRecv();
                long packetout1 = netIF.getPacketsSent();
                long errorin1 = netIF.getInErrors();
                long errorout1 = netIF.getOutErrors();
                long timestamp1 = netIF.getTimeStamp();

                try {
                    Thread.sleep(2000); //Sleep for a bit longer, 2s should cover almost every possible problem
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                netIF.updateNetworkStats(); //Updating network stats
                long download2 = netIF.getBytesRecv();
                long upload2 = netIF.getBytesSent();
                long packetin2 = netIF.getPacketsRecv();
                long packetout2 = netIF.getPacketsSent();
                long errorin2 = netIF.getInErrors();
                long errorout2 = netIF.getOutErrors();
                long timestamp2 = netIF.getTimeStamp();

                long download_Speed = ((download2-download1)*1000/(timestamp2-timestamp1));
                long upload_Speed = ((upload2-upload1)*1000/(timestamp2-timestamp1));
                long data_In_Rate = ((packetin2-packetin1)*1000/(timestamp2-timestamp1));
                long data_Out_Rate = ((packetout2-packetout1)*1000/(timestamp2-timestamp1));
                long error_In_Rate = ((errorin2-errorin1)*1000/(timestamp2-timestamp1));
                long error_Out_Rate = ((errorout2-errorout1)*1000/(timestamp2-timestamp1));


                linkedHashMap.put("IPv4addr",netIF.getIPv4addr());
                linkedHashMap.put("IPv6addr",netIF.getIPv6addr());
                linkedHashMap.put("Interface_Name",netIF.getName());
                linkedHashMap.put("Macaddr",netIF.getMacaddr());
                linkedHashMap.put("Maximum_Transmission_Unit",netIF.getMTU());
                linkedHashMap.put("DownLoad_Speed",download_Speed);
                linkedHashMap.put("Upload_Speed",upload_Speed);
                linkedHashMap.put("Data_In_Rate",data_In_Rate);
                linkedHashMap.put("Data_Out_Rate",data_Out_Rate);
                linkedHashMap.put("Error_In_Rate",error_In_Rate);
                linkedHashMap.put("Error_Out_Rate",error_Out_Rate);
                System.out.println(linkedHashMap);

                linkedHashMap1.put(Integer.toString(++count),linkedHashMap);
            }
        }
        return linkedHashMap1;
    }
}

