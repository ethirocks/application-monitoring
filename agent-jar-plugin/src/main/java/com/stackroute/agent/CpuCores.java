package com.stackroute.agent;

import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

@Component
public class CpuCores {

    public double cpucore(){
        SystemInfo si = new SystemInfo();
        CentralProcessor s = si.getHardware().getProcessor();
        double cpucores = s.getLogicalProcessorCount();
        return cpucores;
    }
}