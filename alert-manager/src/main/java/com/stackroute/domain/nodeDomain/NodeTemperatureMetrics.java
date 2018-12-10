package com.stackroute.domain.nodeDomain;

import lombok.Data;
import org.influxdb.annotation.Measurement;

@Data
@Measurement(name = "nodeTemperature")
public class NodeTemperatureMetrics {
    private Double temperature;
}
