package com.stackroute.monitoringserver.consumer;

import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class CPUUsageConsumer implements IConsumer {
    private MetricsService metricsService;

    @Autowired
    public CPUUsageConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public void consumeMetrics(String url) throws IOException, JSONException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Double> response
                = restTemplate.getForEntity(url+"/cpuusage", Double.class);
        Logger.getLogger("health "+response.toString());
        Double cpuUsage= response.getBody();
        Point cpuUsagePoint = Point.measurement("cpuusage")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("cpu_usage",cpuUsage)
                .build();
        metricsService.insertMetrics(cpuUsagePoint);
    }
}
