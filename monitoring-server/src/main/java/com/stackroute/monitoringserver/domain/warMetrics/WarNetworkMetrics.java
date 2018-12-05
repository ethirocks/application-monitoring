package com.stackroute.monitoringserver.domain.warMetrics;


import com.stackroute.monitoringserver.domain.NetworkMetricsMap;
import lombok.Data;
import org.influxdb.annotation.Measurement;

import java.util.HashMap;

@Data
@Measurement(name = "warNetworkMetrics")
public class WarNetworkMetrics {
///    String number;
//    WarNetworkMetricsMap networkMetricsMap;
    HashMap<String,NetworkMetricsMap> networkMetrics;
}
