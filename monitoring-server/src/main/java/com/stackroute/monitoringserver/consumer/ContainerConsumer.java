package com.stackroute.monitoringserver.consumer;

import com.stackroute.monitoringserver.domain.ContainerMetrics;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ContainerConsumer implements IConsumer{
    private MetricsService metricsService;
    @Autowired
    public ContainerConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public void consumeMetrics(String url) {
        RestTemplate restTemplate = new RestTemplate();
        //String url = "http://172.23.239.222:8082/Container";
        ResponseEntity<List<ContainerMetrics>> response = restTemplate.exchange(
                url+"/metr",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ContainerMetrics>>(){});
        System.out.println("Container "+response.toString());
        List<ContainerMetrics> containerMetricsList= response.getBody();
        long time=System.currentTimeMillis();
        for (ContainerMetrics containerMetrics :
                containerMetricsList) {
            System.out.println(containerMetrics.toString());
            Point point = Point.measurement("Container")
                    .time(time, TimeUnit.MILLISECONDS)
                    .tag("containerId",containerMetrics.getContainer())
                    .addField("container",  containerMetrics.getContainer())
                    .addField("memory_raw",  containerMetrics.getMemory().getRaw())
                    .addField("memory_percent",  containerMetrics.getMemory().getPercent())
                    .addField("cpu",containerMetrics.getCpu())
                    .build();
            metricsService.insertMetrics(point);
            System.out.println("point.......    "+point.toString());
        }
    }
}
