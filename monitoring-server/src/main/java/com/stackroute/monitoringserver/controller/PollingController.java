package com.stackroute.monitoringserver.controller;

import com.stackroute.monitoringserver.consumer.*;
import com.stackroute.monitoringserver.consumer.warConsumer.*;
import com.stackroute.monitoringserver.service.MetricsService;
import com.stackroute.monitoringserver.service.PollingService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class PollingController {

    @Value("${clientUrl}")
    String clientUrl;

    @Value("${containerUrl}")
    String containerUrl;

    @Value("${warUrl}")
    String warUrl;

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


    @Autowired
    private MetricsService metricsService;

    public PollingController() throws IOException, JSONException, URISyntaxException {
    }

    @GetMapping("poll")
    public void pollStart(@RequestParam Integer userID,
                          @RequestParam Integer applicationID
//                          @DefaultValue("") @QueryParam("url") String url,
//                          @DefaultValue("0") @QueryParam("userID") Integer userID,
//                          @DefaultValue("0") @QueryParam("applicationID") Integer applicationID,
//                          @DefaultValue("jar") @QueryParam("dependency") String dependency
                            ) {

        //    if (dependency.equals("jar")){

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
        cpuCoresPoll.setTimerTask(cpuCoresConsumer, clientUrl, userID, applicationID);
        cpuCoresPoll.start(cpuCoresInterval);

        //ram
        IConsumer ramConsumer = new RAMConsumer(metricsService);
        PollingService ramPoll = new PollingService();
        ramPoll.setTimerTask(ramConsumer, clientUrl, userID, applicationID);
        ramPoll.start(ramInterval);

        //Http
        IConsumer httpConsumer = new HttpConsumer(metricsService);
        PollingService httpPoll = new PollingService();
        httpPoll.setTimerTask(httpConsumer, clientUrl, userID, applicationID);
        httpPoll.start(httpInterval);

        //health
        IConsumer healthConsumer = new HealthConsumer(metricsService);
        PollingService healthPoll = new PollingService();
        healthPoll.setTimerTask(healthConsumer, clientUrl, userID, applicationID);
        healthPoll.start(healthInterval);

        //disk
        IConsumer diskConsumer = new HardDiskConsumer(metricsService);
        PollingService diskPoll = new PollingService();
        diskPoll.setTimerTask(diskConsumer, clientUrl, userID, applicationID);
        diskPoll.start(diskInterval);

        //CPU Usage
        IConsumer cpuUsageConsumer = new CPUUsageConsumer(metricsService);
        PollingService cpuUsagePoll = new PollingService();
        cpuUsagePoll.setTimerTask(cpuUsageConsumer, clientUrl, userID, applicationID);
        cpuUsagePoll.start(cpuUsageInterval);

        //CPU Temp
        IConsumer cpuTempConsumer = new CPUTempConsumer(metricsService);
        PollingService cpuTempPoll = new PollingService();
        cpuTempPoll.setTimerTask(cpuTempConsumer, clientUrl, userID, applicationID);
        cpuTempPoll.start(cpuTempInterval);

        //thread
        IConsumer threadConsumer = new ThreadConsumer(metricsService);
        PollingService threadPoll = new PollingService();
        threadPoll.setTimerTask(threadConsumer, clientUrl, userID, applicationID);
        threadPoll.start(threadInterval);

        //network
        IConsumer networkConsumer = new NetworkConsumer(metricsService);
        PollingService networkPoll = new PollingService();
        networkPoll.setTimerTask(networkConsumer, clientUrl, userID, applicationID);
        networkPoll.start(networkInterval);

//        }
//        else if (dependency.equals("war")){
//
            long warCpuCoresInterval=Long.parseLong(warCpuCores);
            long warCpuTempInterval=Long.parseLong(warCpuTemp);
            long warCpuUsageInterval=Long.parseLong(warCpuUsage);
            long warNetworkInterval=Long.parseLong(warNetwork);
            long warThreadInterval=Long.parseLong(warThread);
            long warRamInterval=Long.parseLong(warRam);

            ////war agent metrics
//
            //WarCpu Cores
            IConsumer warCpuCoreConsumer=new WarCpuCoresConsumer(metricsService);
            PollingService warcpuCoresPoll=new PollingService();
            warcpuCoresPoll.setTimerTask(warCpuCoreConsumer,warUrl,userID,applicationID);
            warcpuCoresPoll.start(warCpuCoresInterval);

            //warRam
            IConsumer warramConsumer=new WarRAMConsumer(metricsService);
            PollingService warramPoll=new PollingService();
            warramPoll.setTimerTask(warramConsumer,warUrl,userID,applicationID);
            warramPoll.start(warRamInterval);

            //WarCpu Usage
            IConsumer warcpuUsageConsumer=new WarCpuUsageConsumer(metricsService);
            PollingService warcpuUsagePoll=new PollingService();
            warcpuUsagePoll.setTimerTask(warcpuUsageConsumer,warUrl,userID,applicationID);
            warcpuUsagePoll.start(warCpuUsageInterval);

            //WarCpu Temp
            IConsumer warcpuTempConsumer=new WarCpuTempConsumer(metricsService);
            PollingService warcpuTempPoll=new PollingService();
            warcpuTempPoll.setTimerTask(warcpuTempConsumer,warUrl,userID,applicationID);
            warcpuTempPoll.start(warCpuTempInterval);

            //WarThread
            IConsumer warthreadConsumer=new WarThreadConsumer(metricsService);
            PollingService warthreadPoll=new PollingService();
            warthreadPoll.setTimerTask(warthreadConsumer,warUrl,userID,applicationID);
            warthreadPoll.start(warThreadInterval);

            //WarNetwork
            IConsumer warnetworkConsumer=new WarNetworkConsumer(metricsService);
            PollingService warnetworkPoll=new PollingService();
            warnetworkPoll.setTimerTask(warnetworkConsumer,warUrl,userID,applicationID);
            warnetworkPoll.start(warNetworkInterval);
//
//        }
//
        long containerInterval=Long.parseLong(container);
//
        ////container
        IConsumer containerConsumer=new ContainerConsumer(metricsService);
        PollingService containerPoll=new PollingService();
        containerPoll.setTimerTask(containerConsumer,containerUrl, userID, applicationID);
        containerPoll.start(containerInterval);
//
//    }
//
//        @GetMapping("poll/stop")
//        public void pollStop () {
//
//        }
    }
}
