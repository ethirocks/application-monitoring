package com.stackroute.monitoringserver.controller;

import com.stackroute.monitoringserver.consumer.*;
import com.stackroute.monitoringserver.service.MetricsService;
import com.stackroute.monitoringserver.service.PollingService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class PollingController {

    @Value("${clientUrl}")
    String clientUrl;

    @Value("${containerUrl}")
            String containerUrl;

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

    @Autowired
    private MetricsService metricsService;

    public PollingController() throws IOException, JSONException, URISyntaxException {    }

    @GetMapping("poll")
    public void poll() {

        long cpuCoresInterval=Long.parseLong(cpuCores);
        long cpuTempInterval=Long.parseLong(cpuTemp);
        long cpuUsageInterval=Long.parseLong(cpuUsage);
        long networkInterval=Long.parseLong(network);
        long threadInterval=Long.parseLong(thread);
        long diskInterval=Long.parseLong(disk);
        long ramInterval=Long.parseLong(ram);
        long healthInterval=Long.parseLong(health);
        long httpInterval=Long.parseLong(http);
        long containerInterval=Long.parseLong(container);

        //CPU Cores
        IConsumer cpuCoresConsumer=new CPUCoresConsumer(metricsService);
        PollingService cpuCoresPoll=new PollingService();
        cpuCoresPoll.setTimerTask(cpuCoresConsumer,clientUrl);
        cpuCoresPoll.start(cpuCoresInterval);

        //ram
        IConsumer ramConsumer=new RAMConsumer(metricsService);
        PollingService ramPoll=new PollingService();
        ramPoll.setTimerTask(ramConsumer,clientUrl);
        ramPoll.start(ramInterval);

        //Http
        IConsumer httpConsumer=new HttpConsumer(metricsService);
        PollingService httpPoll=new PollingService();
        httpPoll.setTimerTask(httpConsumer,clientUrl);
        httpPoll.start(httpInterval);

        //health
        IConsumer healthConsumer=new HealthConsumer(metricsService);
        PollingService healthPoll=new PollingService();
        healthPoll.setTimerTask(healthConsumer,clientUrl);
        healthPoll.start(healthInterval);

        //disk
        IConsumer diskConsumer=new HardDiskConsumer(metricsService);
        PollingService diskPoll=new PollingService();
        diskPoll.setTimerTask(diskConsumer,clientUrl);
        diskPoll.start(diskInterval);

        //CPU Usage
        IConsumer cpuUsageConsumer=new CPUUsageConsumer(metricsService);
        PollingService cpuUsagePoll=new PollingService();
        cpuUsagePoll.setTimerTask(cpuUsageConsumer,clientUrl);
        cpuUsagePoll.start(cpuUsageInterval);

        //CPU Temp
        IConsumer cpuTempConsumer=new CPUTempConsumer(metricsService);
        PollingService cpuTempPoll=new PollingService();
        cpuTempPoll.setTimerTask(cpuTempConsumer,clientUrl);
        cpuTempPoll.start(cpuTempInterval);

//        //container
//        IConsumer containerConsumer=new ContainerConsumer(metricsService);
//        PollingService containerPoll=new PollingService();
//        containerPoll.setTimerTask(containerConsumer,containerUrl);
//        containerPoll.start(containerInterval);

        //thread
        IConsumer threadConsumer=new ThreadConsumer(metricsService);
        PollingService threadPoll=new PollingService();
        threadPoll.setTimerTask(threadConsumer,clientUrl);
        threadPoll.start(threadInterval);

        //network
        IConsumer networkConsumer=new NetworkConsumer(metricsService);
        PollingService networkPoll=new PollingService();
        networkPoll.setTimerTask(networkConsumer,clientUrl);
        networkPoll.start(networkInterval);
    }
}
