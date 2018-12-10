package com.stackroute.domain.warMetrics;

import lombok.Data;

import java.util.Map;

@Data
public class WarThreadMetrics {
    private Integer total_Threads;
    private Map<Thread, Thread.State> type_Of_threads;
}
