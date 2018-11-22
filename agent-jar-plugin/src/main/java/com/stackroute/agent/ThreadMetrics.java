package com.stackroute.agent;

import java.util.Map;

public class ThreadMetrics {

    Integer total_Threads;
    Map<Thread, Thread.State> type_Of_threads;

    public ThreadMetrics(Integer total_Threads, Map<Thread, Thread.State> type_Of_threads) {
        this.total_Threads = total_Threads;
        this.type_Of_threads = type_Of_threads;
    }

    public Integer getTotal_Threads() {
        return total_Threads;
    }

    public void setTotal_Threads(Integer total_Threads) {
        this.total_Threads = total_Threads;
    }

    public Map<Thread, Thread.State> getType_Of_threads() {
        return type_Of_threads;
    }

    public void setType_Of_threads(Map<Thread, Thread.State> type_Of_threads) {
        this.type_Of_threads = type_Of_threads;
    }

    @Override
    public String toString() {
        return "ThreadMetrics{" +
                "total_Threads=" + total_Threads +
                ", type_Of_threads=" + type_Of_threads +
                '}';
    }
}
