package com.stackroute.monitoringserver.consumer;

import com.stackroute.domain.ContainerMetrics;
import com.stackroute.domain.ContainerMetricsSystemUsage;
import com.stackroute.domain.GenericMetrics;
import com.stackroute.monitoringserver.service.KafkaService;
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
    public boolean consumeMetrics(String url, Integer userID, Integer applicationID) throws URISyntaxException, MalformedURLException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GenericMetrics<List<ContainerMetrics>>> response = restTemplate.exchange(
                new URI(url+"/container/metrics?userID="+userID+"&applicationID="+applicationID),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<GenericMetrics<List<ContainerMetrics>>>(){});
        System.out.println("Container "+response.toString());
        List<ContainerMetrics> containerMetricsList= response.getBody().getMetrics();

        KafkaService kafkaService=new KafkaService();
        kafkaService.produce(response.getBody().getMetrics(),"containerMetricsLive1",userID,applicationID);

        ResponseEntity<GenericMetrics<String>> response1 = restTemplate.exchange(url + "/container/temperature?userID="+userID+"&applicationID="+applicationID,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<String>>(){});
        System.out.println("Container "+response.toString());
        String temperature= response1.getBody().getMetrics();
        kafkaService.produce(response1.getBody().getMetrics(),"containerTemperatureLive1",userID,applicationID);
        if(temperature.equals(null)){
            temperature="0";
        }
        ResponseEntity<GenericMetrics<ContainerMetricsSystemUsage>> response2 = restTemplate.exchange(url + "/container/systemusage?userID="+userID+"&applicationID="+applicationID,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<ContainerMetricsSystemUsage>>(){});
        System.out.println("Container "+response.toString());
        ContainerMetricsSystemUsage systemUsage= response2.getBody().getMetrics();
        kafkaService.produce(response2.getBody().getMetrics(),"containerSystemUsageLive1",userID,applicationID);

        long time=System.currentTimeMillis();
        for (ContainerMetrics containerMetrics :
                containerMetricsList) {
            System.out.println(containerMetrics.toString());
            Point point = Point.measurement("Container")
                    .time(time, TimeUnit.MILLISECONDS)
                    .tag("userID",response.getBody().getUserID().toString())
                    .tag("applicationID",response.getBody().getApplicationID().toString())
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
                    .addField("systemMemory",systemUsage.getSystemmemory())
                    .addField("systemCPU",systemUsage.getSystemcpu())
                    .build();
            metricsService.insertMetrics(point);
            System.out.println("point.......    "+point.toString());
        }
        return true;
    }
}
