package com.stackroute.agent.service;

import com.stackroute.agent.model.Threads;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ThreadsIndicator {
    public Threads getThreads(){


        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        Map<Thread, Thread.State> hm = new HashMap<>();
        


        for ( Thread t : threadSet){
            hm.put(t,t.getState());
        }

        Threads threads = new Threads(threadSet.size(),hm);
        return threads;
    }
}
