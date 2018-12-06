package com.stackroute.domain;

import lombok.Data;
import org.influxdb.annotation.Measurement;

import java.util.HashMap;

@Data
@Measurement(name = "thread")
public class ThreadMetrics {
    private long total_Threads;
    private HashMap<Thread,String> type_Of_threads;
    // threadName, priority, group
}
