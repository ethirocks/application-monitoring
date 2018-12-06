package com.stackroute.domain;

import lombok.Data;
import org.influxdb.annotation.Measurement;

@Data
@Measurement(name = "container")
public class ContainerMetrics {
    private String container;
    private String name;
    private String containerId;
    private String networkIO;
    private String blockIO;
    private String threads;
    private ContainerMetricsMemory memory;
    private String cpu;
//"container": "acc58a04d788",
//        "name": "agentcontainerdocker_agenttodocker_1",
//        "containerId": "acc58a04d788",
//        "networkIO": "0B / 0B",
//        "blockIO": "34.4MB / 0B",
//        "threads": "54",
//        "memory": {
//        "raw": "311.9MiB / 7.698GiB",
//                "percent": "3.96%"
//    },
//            "cpu": "0.26%"


//    jarUrl = http://52.66.184.4:8082
//    containerUrl = "http://172.23.239.222:9001"
//    cpuCoresInterval = 10000
//    cpuTempInterval = 10000
//    cpuUsageInterval = 10000
//    networkInterval = 10000
//    threadInterval = 10000
//    diskInterval = 10000
//    ramInterval = 10000
//    healthInterval = 1000
//    httpInterval = 10000
//    containerInterval = 10000


}
