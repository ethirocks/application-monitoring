package com.stackroute.agent;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ThreadsIndicator {



    public ThreadMetrics getThreads(){


        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        Map<Thread, Thread.State> hm = new HashMap<>();
        


        for ( Thread t : threadSet){
            //System.out.println("Thread :"+t+":"+"state:"+t.getState());
            hm.put(t,t.getState());
        }

        ThreadMetrics threads = new ThreadMetrics(threadSet.size(),hm);

        //System.out.println(threads);

        //threads.setHm(hm);
        //threads.setInteger(threadSet.size());
        
        
        
        return threads;
    }
}
