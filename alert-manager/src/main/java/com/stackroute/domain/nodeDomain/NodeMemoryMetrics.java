package com.stackroute.domain.nodeDomain;

import lombok.Data;
import org.influxdb.annotation.Measurement;

@Data
@Measurement(name = "nodeMemory")
public class NodeMemoryMetrics {
    private String memory;
    private String totalmemory;
}
