package com.stackroute.monitoringserver;

import com.stackroute.monitoringserver.consumer.*;
import com.stackroute.monitoringserver.service.MetricsService;
import com.stackroute.monitoringserver.service.PollingService;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
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


 //       HealthConsumer healthConsumer=new HealthConsumer();
 //       healthConsumer.start();
//        healthConsumer.stop();
//        ContainerConsumer containerConsumer=new ContainerConsumer();
//        containerConsumer.start();

        //health
        IConsumer healthConsumer=new HealthConsumer(metricsService);
        PollingService healthPoll=new PollingService();
        healthPoll.setTimerTask(healthConsumer,"http://172.23.239.80:8082");
        healthPoll.start(1000);

//        //disk
//        IConsumer diskConsumer=new HardDiskConsumer(metricsService);
//        PollingService diskPoll=new PollingService();
//        diskPoll.setTimerTask(diskConsumer,"http://172.23.239.80:8082");
//        diskPoll.start(20000);

        //CPU Usage
        IConsumer cpuUsageConsumer=new CPUUsageConsumer(metricsService);
        PollingService cpuUsagePoll=new PollingService();
        cpuUsagePoll.setTimerTask(cpuUsageConsumer,"http://172.23.239.80:8082");
        cpuUsagePoll.start(20000);

        //CPU Temp
        IConsumer cpuTempConsumer=new CPUTempConsumer(metricsService);
        PollingService cpuTempPoll=new PollingService();
        cpuTempPoll.setTimerTask(cpuTempConsumer,"http://172.23.239.80:8082");
        cpuTempPoll.start(20000);


//        IConsumer httpConsumer=new HttpConsumer(metricsService);
//        PollingService httpPoll=new PollingService();
//        httpPoll.setTimerTask(httpConsumer,"http://172.23.239.80:8082");
//        httpPoll.start(10000);


//        consumer=new HttpConsumer(metricsService);
// //       consumer.consumeMetrics();
//
//        //container
////        consumer=new ContainerConsumer(metricsService);
////        consumer.consumeMetrics();
//
        //thread
        IConsumer threadConsumer=new ThreadConsumer(metricsService);
        PollingService threadPoll=new PollingService();
        threadPoll.setTimerTask(threadConsumer,"http://172.23.239.80:8082");
        threadPoll.start(1000000);

    }
}
