package com.stackroute.monitoringserver.domain.warMetrics;


import lombok.Data;
import org.influxdb.annotation.Measurement;

@Data
@Measurement(name = "network")
public class WarNetworkMetricsMap {
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
