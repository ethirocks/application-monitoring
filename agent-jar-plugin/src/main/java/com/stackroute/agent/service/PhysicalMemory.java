package com.stackroute.agent.service;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class PhysicalMemory {
    public ArrayList<Long> ramMemory(){
    OperatingSystemMXBean osmxb = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    long totalVirtualMemory = osmxb.getTotalPhysicalMemorySize();
    long freePhysicalMemory = osmxb.getFreePhysicalMemorySize();

    ArrayList<Long> memoryObejct = new ArrayList<Long>(Arrays.asList());
    memoryObejct.add(totalVirtualMemory);
    memoryObejct.add(freePhysicalMemory);
    return memoryObejct;
    }
}