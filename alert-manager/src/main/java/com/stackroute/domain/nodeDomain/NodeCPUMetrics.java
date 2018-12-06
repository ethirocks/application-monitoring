package com.stackroute.domain.nodeDomain;

import lombok.Data;
import org.influxdb.annotation.Measurement;

@Data
@Measurement(name="nodeCPU")
public class NodeCPUMetrics {
    private String cpu;
    private String systemcpu;
}
