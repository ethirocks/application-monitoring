package com.stackroute.monitoringserver.domain;


import lombok.Data;
import org.influxdb.annotation.Measurement;

import java.util.HashMap;

@Data
@Measurement(name = "networkMetrics")
public class NetworkMetrics {
///    String number;
//    WarNetworkMetricsMap networkMetricsMap;
    HashMap<String,NetworkMetricsMap> networkMetrics;
}
