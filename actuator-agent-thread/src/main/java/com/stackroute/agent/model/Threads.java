package com.stackroute.agent.model;

import java.util.Map;

public class Threads {

    Integer total_Threads;
    Map<Thread, Thread.State> type_Of_threads;

    public Threads(Integer total_Threads, Map<Thread, Thread.State> type_Of_threads) {
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
        return "Threads{" +
                "total_Threads=" + total_Threads +
                ", type_Of_threads=" + type_Of_threads +
                '}';
    }
}
