package com.stackroute.agent.service;

import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

@Component
public class CpuUsage {

    public double cpuuse(){
        SystemInfo si = new SystemInfo();
        CentralProcessor s = si.getHardware().getProcessor();
        double cpuusage = s.getSystemCpuLoad();
        cpuusage=cpuusage * 100;
        return cpuusage;
    }
}