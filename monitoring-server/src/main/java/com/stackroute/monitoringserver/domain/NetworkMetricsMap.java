package com.stackroute.monitoringserver.domain;


import lombok.Data;
import org.influxdb.annotation.Measurement;

@Data
@Measurement(name = "network")
public class NetworkMetricsMap {
    //            linkedHashMap.put("IPv4addr : ",netIF.getIPv4addr());
//            linkedHashMap.put("IPv6addr",netIF.getIPv6addr());
//            linkedHashMap.put("Interface_Name",netIF.getName());
//            linkedHashMap.put("Macaddr",netIF.getMacaddr());
//            linkedHashMap.put("Maximum_Transmission_Unit",netIF.getMTU());
//            linkedHashMap.put("DownLoad_Speed",download_Speed);
//            linkedHashMap.put("Upload_Speed",upload_Speed);
//            linkedHashMap.put("Data_In_Rate",data_In_Rate);
//            linkedHashMap.put("Data_Out_Rate",data_Out_Rate);
//            linkedHashMap.put("Error_In_Rate",error_In_Rate);
//            linkedHashMap.put("Error_Out_Rate",error_Out_Rate);
    private String IPv4addr;
    private String IPv6addr;
    private String Interface_Name;
    private String Macaddr;
    private String Maximum_Transmission_Unit;
    private String DownLoad_Speed;
    private String Upload_Speed;
    private String Data_In_Rate;
    private String Data_Out_Rate;
    private String Error_In_Rate;
    private String Error_Out_Rate;
}
