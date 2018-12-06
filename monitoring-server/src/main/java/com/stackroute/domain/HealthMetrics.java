package com.stackroute.domain;

import lombok.Data;
import org.influxdb.annotation.Measurement;

@Data
@Measurement(name = "health")
public class HealthMetrics {
    private String status;
    private HealthMetricsDetails details;

}

