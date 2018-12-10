package com.stackroute.domain.warMetrics;


import lombok.Data;

import java.util.HashMap;

@Data
public class WarNetworkMetrics {
///    String number;
//    WarNetworkMetricsMap networkMetricsMap;
    HashMap<String,WarNetworkMetricsMap> networkMetrics;
}
