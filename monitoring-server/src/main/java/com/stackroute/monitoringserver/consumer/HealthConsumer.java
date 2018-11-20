package com.stackroute.monitoringserver.consumer;

import com.stackroute.monitoringserver.domain.HealthMetrics;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class HealthConsumer implements IConsumer{
    private MetricsService metricsService;
    @Autowired
    public HealthConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public boolean consumeMetrics(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HealthMetrics> response
                = restTemplate.getForEntity(url+"/health", HealthMetrics.class);
        Logger.getLogger("health "+response.toString());
        HealthMetrics healthMetrics= response.getBody();
        try{
            Point healthPoint = Point.measurement("health")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("status",  healthMetrics.getStatus())
                    .build();
            metricsService.insertMetrics(healthPoint);
        }
        catch (NullPointerException n){ }
        return true;
    }
}
