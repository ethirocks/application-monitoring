package com.stackroute.monitoringserver;

import com.stackroute.monitoringserver.consumer.HardDiskConsumer;
import com.stackroute.monitoringserver.consumer.IConsumer;
import com.stackroute.monitoringserver.service.MetricsService;
import com.stackroute.monitoringserver.service.PollingService;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.io.IOException;
import java.net.URISyntaxException;

@EnableEurekaClient
@SpringBootApplication
public class MonitoringServerApplication {

    public static void main(String[] args) throws JSONException, IOException, URISyntaxException {

        SpringApplication.run(MonitoringServerApplication.class, args);

    }
}
