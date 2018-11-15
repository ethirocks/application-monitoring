package com.stackroute.monitoringserver.consumer;

import com.stackroute.monitoringserver.domain.HealthMetrics;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class CPUTempConsumer implements IConsumer {
    private MetricsService metricsService;
    @Autowired
    public CPUTempConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public void consumeMetrics(String url) throws IOException, JSONException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        //String url = "http://172.23.239.197:8889/health";
        ResponseEntity<Double> response
                = restTemplate.getForEntity(url+"/cputemp", Double.class);
        Logger.getLogger("cpuTemp "+response.toString());
        Double cpuTemp= response.getBody();
        Point cpuTempPoint = Point.measurement("cputemp")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("cpu_temp",cpuTemp)
                .build();
        metricsService.insertMetrics(cpuTempPoint);
    }
}
