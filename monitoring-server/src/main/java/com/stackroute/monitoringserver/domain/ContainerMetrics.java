package com.stackroute.monitoringserver.domain;

import lombok.Data;
import org.influxdb.annotation.Measurement;

@Data
@Measurement(name = "container")
public class ContainerMetrics {
    private String container;
    private String cpu;
    private ContainerMetricsMemory memory;

}
