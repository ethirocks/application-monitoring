package com.stackroute.agent.service;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;

@Component
public class CpuUsage {
    public double cpuUse(){
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor centralProcessor = systemInfo.getHardware().getProcessor();
        double cpuUsage = centralProcessor.getSystemCpuLoad();
        cpuUsage *= 100;
        return cpuUsage;
    }
    public double cpuTemperature(){
        SystemInfo systemInfo = new SystemInfo();
        Sensors sensors = systemInfo.getHardware().getSensors();
        double cpuTemperature = sensors.getCpuTemperature();
        return cpuTemperature;
    }

    public double cpuCore(){
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor centralProcessor = systemInfo.getHardware().getProcessor();
        double cpucores = centralProcessor.getLogicalProcessorCount();
        return cpucores;
    }



}
