package com.stackroute.monitoringserver;

import com.stackroute.monitoringserver.consumer.*;
import com.stackroute.monitoringserver.service.MetricsService;
import com.stackroute.monitoringserver.service.PollingService;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class MonitoringServerApplication {

    public static void main(String[] args) throws IOException, JSONException, URISyntaxException {

        SpringApplication.run(MonitoringServerApplication.class, args);

        MetricsService metricsService=new MetricsService();

        //CPU Cores
        IConsumer cpuCoreConsumer=new CPUCoresConsumer(metricsService);
        cpuCoreConsumer.consumeMetrics("http://client:8082");

        //ram
        IConsumer ramConsumer=new RAMConsumer(metricsService);
        PollingService ramPoll=new PollingService();
        ramPoll.setTimerTask(ramConsumer,"http://client:8082");
        ramPoll.start(20000);

        //Http
        IConsumer httpConsumer=new HttpConsumer(metricsService);
        PollingService httpPoll=new PollingService();
        httpPoll.setTimerTask(httpConsumer,"http://client:8082");
        httpPoll.start(10000);

        //health
        IConsumer healthConsumer=new HealthConsumer(metricsService);
        PollingService healthPoll=new PollingService();
        healthPoll.setTimerTask(healthConsumer,"http://client:8082");
        healthPoll.start(1000);

        //disk
        IConsumer diskConsumer=new HardDiskConsumer(metricsService);
        PollingService diskPoll=new PollingService();
        diskPoll.setTimerTask(diskConsumer,"http://client:8082");
        diskPoll.start(20000);


        //CPU Usage
        IConsumer cpuUsageConsumer=new CPUUsageConsumer(metricsService);
        PollingService cpuUsagePoll=new PollingService();
        cpuUsagePoll.setTimerTask(cpuUsageConsumer,"http://client:8082");
        cpuUsagePoll.start(20000);

        //CPU Temp
        IConsumer cpuTempConsumer=new CPUTempConsumer(metricsService);
        PollingService cpuTempPoll=new PollingService();
        cpuTempPoll.setTimerTask(cpuTempConsumer,"http://client:8082");
        cpuTempPoll.start(20000);

//
//        //container
////        consumer=new ContainerConsumer(metricsService);
////        consumer.consumeMetrics();

        //thread
        IConsumer threadConsumer=new ThreadConsumer(metricsService);
        PollingService threadPoll=new PollingService();
        threadPoll.setTimerTask(threadConsumer,"http://client:8082");
        threadPoll.start(10000);

        //network
        IConsumer networkConsumer=new NetworkConsumer(metricsService);
        PollingService networkPoll=new PollingService();
        networkPoll.setTimerTask(networkConsumer,"http://client:8082");
        networkPoll.start(10000);
    }
}
