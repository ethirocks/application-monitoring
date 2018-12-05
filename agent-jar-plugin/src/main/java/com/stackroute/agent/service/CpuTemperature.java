package com.stackroute.agent.service;

import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.Sensors;

@Component
public class CpuTemperature {

    public double temperature(){
        SystemInfo si = new SystemInfo();
        Sensors s = si.getHardware().getSensors();
        double cpuTemperature = s.getCpuTemperature();
        return cpuTemperature;
    }
}