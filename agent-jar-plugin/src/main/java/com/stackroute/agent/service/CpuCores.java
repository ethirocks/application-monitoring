package com.stackroute.agent.service;

import com.stackroute.agent.domain.Metrics;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

@Component
public class CpuCores {

    public Double cpucore(Integer userID,Integer applicationID){
        SystemInfo si = new SystemInfo();
        CentralProcessor s = si.getHardware().getProcessor();
        double cpucores = s.getLogicalProcessorCount();
        return cpucores;
    }
}