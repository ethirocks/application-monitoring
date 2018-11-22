package com.stackroute.monitoringserver.domain;

import lombok.Data;
import org.influxdb.annotation.Measurement;

@Data
@Measurement(name = "disk_utilization")
public class HealthMetricsDetails {
    private String diskFree;
    private String diskTotal;
}
