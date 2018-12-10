package com.stackroute.domain.warMetrics;


import com.stackroute.domain.NetworkMetricsMap;
import lombok.Data;
import org.influxdb.annotation.Measurement;

import java.util.HashMap;

@Data
@Measurement(name = "warNetworkMetrics")
public class WarNetworkMetrics {
///    String number;
//    WarNetworkMetricsMap networkMetricsMap;
    HashMap<String, NetworkMetricsMap> networkMetrics;
}
