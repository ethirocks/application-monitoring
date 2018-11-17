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
        cpuCoreConsumer.consumeMetrics("http://10.20.1.218:8081");

        //ram
        IConsumer ramConsumer=new RAMConsumer(metricsService);
        PollingService ramPoll=new PollingService();
        ramPoll.setTimerTask(ramConsumer,"http://10.20.1.218:8081");
        ramPoll.start(20000);

        //Http
        IConsumer httpConsumer=new HttpConsumer(metricsService);
        PollingService httpPoll=new PollingService();
        httpPoll.setTimerTask(httpConsumer,"http://10.20.1.218:8081");
        httpPoll.start(10000);

        //health
        IConsumer healthConsumer=new HealthConsumer(metricsService);
        PollingService healthPoll=new PollingService();
        healthPoll.setTimerTask(healthConsumer,"http://10.20.1.218:8081");
        healthPoll.start(1000);

        //disk
        IConsumer diskConsumer=new HardDiskConsumer(metricsService);
        PollingService diskPoll=new PollingService();
        diskPoll.setTimerTask(diskConsumer,"http://10.20.1.218:8081");
        diskPoll.start(20000);


        //CPU Usage
        IConsumer cpuUsageConsumer=new CPUUsageConsumer(metricsService);
        PollingService cpuUsagePoll=new PollingService();
        cpuUsagePoll.setTimerTask(cpuUsageConsumer,"http://10.20.1.218:8081");
        cpuUsagePoll.start(20000);

        //CPU Temp
        IConsumer cpuTempConsumer=new CPUTempConsumer(metricsService);
        PollingService cpuTempPoll=new PollingService();
        cpuTempPoll.setTimerTask(cpuTempConsumer,"http://10.20.1.218:8081");
        cpuTempPoll.start(20000);

//
//        //container
////        consumer=new ContainerConsumer(metricsService);
////        consumer.consumeMetrics();

        //thread
        IConsumer threadConsumer=new ThreadConsumer(metricsService);
        PollingService threadPoll=new PollingService();
        threadPoll.setTimerTask(threadConsumer,"http://10.20.1.218:8081");
        threadPoll.start(10000);

        //network
        IConsumer networkConsumer=new NetworkConsumer(metricsService);
        PollingService networkPoll=new PollingService();
        networkPoll.setTimerTask(networkConsumer,"http://10.20.1.218:8081");
        networkPoll.start(10000);
    }
}
