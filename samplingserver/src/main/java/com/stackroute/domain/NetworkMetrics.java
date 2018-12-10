package com.stackroute.domain;


import lombok.Data;

import java.util.HashMap;

@Data
public class NetworkMetrics {
///    String number;
//    NetworkMetricsMap networkMetricsMap;
    HashMap<String,NetworkMetricsMap> networkMetrics;
}
