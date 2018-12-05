package com.stackroute.monitoringserver.domain.warMetrics;

import lombok.Data;
import org.influxdb.annotation.Measurement;

import java.util.Map;

@Data
@Measurement(name = "warThread")
public class WarThreadMetrics {
    private Integer total_Threads;
    private Map<Thread, Thread.State> type_Of_threads;
}
