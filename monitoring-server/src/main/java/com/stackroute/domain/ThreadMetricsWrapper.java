package com.stackroute.domain;

import lombok.Data;

@Data
public class ThreadMetricsWrapper {
    Integer userID;
    Integer applicationID;
    ThreadMetrics metrics;
}
