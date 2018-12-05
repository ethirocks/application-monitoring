package com.stackroute.monitoringserver.domain;


import lombok.Data;
import org.influxdb.annotation.Measurement;

@Data
@Measurement(name = "network")
public class NetworkMetricsMap {
//    "IPv4addr : ": [
//            "172.17.0.1"
//            ],
//            "IPv6addr": [
//            "fe80:0:0:0:42:7dff:fe41:2f76"
//            ],
//            "Interface_Name": "docker0",
//            "Macaddr": "02:42:7d:41:2f:76",
//            "Maximum_Transmission_Unit": 1500,
//            "DownLoad_Speed": 0,
//            "Upload_Speed": 0,
//            "Data_In_Rate": 0,
//            "Data_Out_Rate": 0,
//            "Error_In_Rate": 0,
//            "Error_Out_Rate": 0
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
