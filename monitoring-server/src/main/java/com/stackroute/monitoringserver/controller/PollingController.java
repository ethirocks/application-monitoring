package com.stackroute.monitoringserver.controller;

import com.stackroute.monitoringserver.consumer.*;
import com.stackroute.monitoringserver.consumer.nodeConsumer.NodeCPUConsumer;
import com.stackroute.monitoringserver.consumer.nodeConsumer.NodeMemoryConsumer;
import com.stackroute.monitoringserver.consumer.nodeConsumer.NodeTemperatureConsumer;
import com.stackroute.monitoringserver.consumer.warConsumer.*;
import com.stackroute.domain.Application;
import com.stackroute.monitoringserver.service.MetricsService;
import com.stackroute.monitoringserver.service.PollingService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@CrossOrigin("*")
@RestController
public class PollingController {

    @Value("${cpuCoresInterval}")
    String cpuCores;

    @Value("${cpuTempInterval}")
    String cpuTemp;

    @Value("${cpuUsageInterval}")
    String cpuUsage;

    @Value("${networkInterval}")
    String network;

    @Value("${threadInterval}")
    String thread;

    @Value("${diskInterval}")
    String disk;

    @Value("${ramInterval}")
    String ram;

    @Value("${healthInterval}")
    String health;

    @Value("${httpInterval}")
    String http;

    @Value("${containerInterval}")
    String container;

    @Value("${warCpuCoresInterval}")
    String warCpuCores;

    @Value("${warCpuTempInterval}")
    String warCpuTemp;

    @Value("${warCpuUsageInterval}")
    String warCpuUsage;

    @Value("${warNetworkInterval}")
    String warNetwork;

    @Value("${warThreadInterval}")
    String warThread;

    @Value("${warRamInterval}")
    String warRam;

    @Value("${nodeCPUInterval}")
    String nodeCPU;

    @Value("${nodeMemoryInterval}")
    String nodeMemory;

    @Value("${nodeTemperatureInterval}")
    String nodeTemperature;


    @Autowired
    private MetricsService metricsService;

    public PollingController() throws IOException, JSONException, URISyntaxException {   }

    @PostMapping("poll")
    public void pollStart(@RequestBody Application application) {

        String url=application.getAddress();

        if (application.getDependency().equals("jar")){

            long cpuCoresInterval = Long.parseLong(cpuCores);
            long cpuTempInterval = Long.parseLong(cpuTemp);
            long cpuUsageInterval = Long.parseLong(cpuUsage);
            long networkInterval = Long.parseLong(network);
            long threadInterval = Long.parseLong(thread);
            long diskInterval = Long.parseLong(disk);
            long ramInterval = Long.parseLong(ram);
            long healthInterval = Long.parseLong(health);
            long httpInterval = Long.parseLong(http);

            //CPU Cores
            IConsumer cpuCoresConsumer = new CPUCoresConsumer(metricsService);
            PollingService cpuCoresPoll = new PollingService();
            cpuCoresPoll.setTimerTask(cpuCoresConsumer, url, application.getUserId(), application.getId());
            cpuCoresPoll.start(cpuCoresInterval);

            //ram
            IConsumer ramConsumer = new RAMConsumer(metricsService);
            PollingService ramPoll = new PollingService();
            ramPoll.setTimerTask(ramConsumer, url, application.getUserId(), application.getId());
            ramPoll.start(ramInterval);

            //Http
            IConsumer httpConsumer = new HttpConsumer(metricsService);
            PollingService httpPoll = new PollingService();
            httpPoll.setTimerTask(httpConsumer, url, application.getUserId(), application.getId());
            httpPoll.start(httpInterval);

            //health
            IConsumer healthConsumer = new HealthConsumer(metricsService);
            PollingService healthPoll = new PollingService();
            healthPoll.setTimerTask(healthConsumer, url, application.getUserId(), application.getId());
            healthPoll.start(healthInterval);

            //disk
            IConsumer diskConsumer = new HardDiskConsumer(metricsService);
            PollingService diskPoll = new PollingService();
            diskPoll.setTimerTask(diskConsumer, url, application.getUserId(), application.getId());
            diskPoll.start(diskInterval);

            //CPU Usage
            IConsumer cpuUsageConsumer = new CPUUsageConsumer(metricsService);
            PollingService cpuUsagePoll = new PollingService();
            cpuUsagePoll.setTimerTask(cpuUsageConsumer, url, application.getUserId(), application.getId());
            cpuUsagePoll.start(cpuUsageInterval);

            //CPU Temp
            IConsumer cpuTempConsumer = new CPUTempConsumer(metricsService);
            PollingService cpuTempPoll = new PollingService();
            cpuTempPoll.setTimerTask(cpuTempConsumer, url, application.getUserId(), application.getId());
            cpuTempPoll.start(cpuTempInterval);

            //thread
            IConsumer threadConsumer = new ThreadConsumer(metricsService);
            PollingService threadPoll = new PollingService();
            threadPoll.setTimerTask(threadConsumer, url, application.getUserId(), application.getId());
            threadPoll.start(threadInterval);

            //network
            IConsumer networkConsumer = new NetworkConsumer(metricsService);
            PollingService networkPoll = new PollingService();
            networkPoll.setTimerTask(networkConsumer, url, application.getUserId(), application.getId());
            networkPoll.start(networkInterval);

        }
        else if (application.getDependency().equals("war")){

            long warCpuCoresInterval=Long.parseLong(warCpuCores);
            long warCpuTempInterval=Long.parseLong(warCpuTemp);
            long warCpuUsageInterval=Long.parseLong(warCpuUsage);
            long warNetworkInterval=Long.parseLong(warNetwork);
            long warThreadInterval=Long.parseLong(warThread);
            long warRamInterval=Long.parseLong(warRam);

            ////war agent metrics

            //WarCpu Cores
            IConsumer warCpuCoreConsumer=new WarCpuCoresConsumer(metricsService);
            PollingService warcpuCoresPoll=new PollingService();
            warcpuCoresPoll.setTimerTask(warCpuCoreConsumer,url,application.getUserId(),application.getId());
            warcpuCoresPoll.start(warCpuCoresInterval);

            //warRam
            IConsumer warramConsumer=new WarRAMConsumer(metricsService);
            PollingService warramPoll=new PollingService();
            warramPoll.setTimerTask(warramConsumer,url,application.getUserId(),application.getId());
            warramPoll.start(warRamInterval);

            //WarCpu Usage
            IConsumer warcpuUsageConsumer=new WarCpuUsageConsumer(metricsService);
            PollingService warcpuUsagePoll=new PollingService();
            warcpuUsagePoll.setTimerTask(warcpuUsageConsumer,url,application.getUserId(),application.getId());
            warcpuUsagePoll.start(warCpuUsageInterval);

            //WarCpu Temp
            IConsumer warcpuTempConsumer=new WarCpuTempConsumer(metricsService);
            PollingService warcpuTempPoll=new PollingService();
            warcpuTempPoll.setTimerTask(warcpuTempConsumer,url,application.getUserId(),application.getId());
            warcpuTempPoll.start(warCpuTempInterval);

            //WarThread
            IConsumer warthreadConsumer=new WarThreadConsumer(metricsService);
            PollingService warthreadPoll=new PollingService();
            warthreadPoll.setTimerTask(warthreadConsumer,url,application.getUserId(),application.getId());
            warthreadPoll.start(warThreadInterval);

            //WarNetwork
            IConsumer warnetworkConsumer=new WarNetworkConsumer(metricsService);
            PollingService warnetworkPoll=new PollingService();
            warnetworkPoll.setTimerTask(warnetworkConsumer,url,application.getUserId(),application.getId());
            warnetworkPoll.start(warNetworkInterval);

        }
        else if (application.getDependency().equals("docker")){

            long containerInterval=Long.parseLong(container);
            ////container
            IConsumer containerConsumer=new ContainerConsumer(metricsService);
            PollingService containerPoll=new PollingService();
            containerPoll.setTimerTask(containerConsumer,url, application.getUserId(), application.getId());
            containerPoll.start(containerInterval);
        }
        else if (application.getDependency().equals("nodeJs")){
            long nodeCPUInterval=Long.parseLong(nodeCPU);
            long nodeMemoryInterval=Long.parseLong(nodeMemory);
            long nodeTemperatureInterval=Long.parseLong(nodeTemperature);
            ////nodeCPU
            IConsumer nodeCPUConsumer=new NodeCPUConsumer(metricsService);
            PollingService nodeCPUPoll=new PollingService();
            nodeCPUPoll.setTimerTask(nodeCPUConsumer,url, application.getUserId(), application.getId());
            nodeCPUPoll.start(nodeCPUInterval);

            ////nodeMemory
            IConsumer nodeMemoryConsumer=new NodeMemoryConsumer(metricsService);
            PollingService nodeMemoryPoll=new PollingService();
            nodeMemoryPoll.setTimerTask(nodeMemoryConsumer,url, application.getUserId(), application.getId());
            nodeMemoryPoll.start(nodeMemoryInterval);

            ////nodeTemperature
            IConsumer nodeTemperatureConsumer=new NodeTemperatureConsumer(metricsService);
            PollingService nodeTemperaturePoll=new PollingService();
            nodeTemperaturePoll.setTimerTask(nodeTemperatureConsumer,url, application.getUserId(), application.getId());
            nodeTemperaturePoll.start(nodeTemperatureInterval);
        }
    }
}