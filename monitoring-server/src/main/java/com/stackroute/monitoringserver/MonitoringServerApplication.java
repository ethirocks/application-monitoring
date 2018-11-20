package com.stackroute.monitoringserver;

import com.stackroute.monitoringserver.consumer.*;
import com.stackroute.monitoringserver.controller.PollingController;
import com.stackroute.monitoringserver.service.MetricsService;
import com.stackroute.monitoringserver.service.PollingService;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class MonitoringServerApplication {

    public static void main(String[] args){

        SpringApplication.run(MonitoringServerApplication.class, args);

    }
}
