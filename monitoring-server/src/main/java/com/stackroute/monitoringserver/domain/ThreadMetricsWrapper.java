package com.stackroute.monitoringserver.domain;

import lombok.Data;

@Data
public class ThreadMetricsWrapper {
    Integer userID;
    Integer applicationID;
    ThreadMetrics metrics;
}
