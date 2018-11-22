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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
    public boolean consumeMetrics(String url) throws URISyntaxException, MalformedURLException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ContainerMetrics>> response = restTemplate.exchange(
                new URI(url+"/container/metrics"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ContainerMetrics>>(){});
        System.out.println("Container "+response.toString());
        List<ContainerMetrics> containerMetricsList= response.getBody();
        long time=System.currentTimeMillis();

        ResponseEntity<Double> response1 = restTemplate.getForEntity(url + "/container/temperature", Double.class);
        System.out.println("Container "+response.toString());
        Double temperature= response1.getBody();

        for (ContainerMetrics containerMetrics :
                containerMetricsList) {
            System.out.println(containerMetrics.toString());
            Point point = Point.measurement("Container")
                    .time(time, TimeUnit.MILLISECONDS)
                    .tag("containerId",containerMetrics.getContainer())
                    .addField("container",  containerMetrics.getContainer())
                    .addField("containerName",containerMetrics.getName())
                    .addField("threads",containerMetrics.getThreads())
                    .addField("networkIO",containerMetrics.getNetworkIO())
                    .addField("blockIO",containerMetrics.getBlockIO())
                    .addField("memory_raw",  containerMetrics.getMemory().getRaw())
                    .addField("memory_percent",  containerMetrics.getMemory().getPercent())
                    .addField("cpu",containerMetrics.getCpu())
                    .addField("cpuTemperature",temperature)
                    .build();
            metricsService.insertMetrics(point);
            System.out.println("point.......    "+point.toString());
        }
        return true;
    }
}
