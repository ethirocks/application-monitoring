package com.stackroute.domain;

import lombok.Data;

@Data
public class HealthMetrics {
    private String status;
    private HealthMetricsDetails details;

}

