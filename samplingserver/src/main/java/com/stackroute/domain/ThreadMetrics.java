package com.stackroute.domain;

import lombok.Data;

import java.util.HashMap;

@Data
public class ThreadMetrics {
    private long total_Threads;
    private HashMap<Thread,String> type_Of_threads;
    // threadName, priority, group
}
