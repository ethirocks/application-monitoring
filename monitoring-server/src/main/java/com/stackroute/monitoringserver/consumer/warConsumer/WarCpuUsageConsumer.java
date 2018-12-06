package com.stackroute.monitoringserver.consumer.warConsumer;

import com.stackroute.monitoringserver.consumer.IConsumer;
import com.stackroute.domain.GenericMetrics;
import com.stackroute.monitoringserver.service.KafkaService;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class WarCpuUsageConsumer implements IConsumer {
    private MetricsService metricsService;

    @Autowired
    public WarCpuUsageConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public boolean consumeMetrics(String url, Integer userID, Integer applicationID) throws IOException, JSONException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GenericMetrics<Double>> response
                = restTemplate.exchange(url+"/cpuusage?userID="+userID+"&applicationID="+applicationID,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<Double>>(){});
        Logger.getLogger("health "+response.toString());
        Double cpuUsage= response.getBody().getMetrics();
        KafkaService kafkaService=new KafkaService();
        kafkaService.produce(cpuUsage,"warCpuUsageLive",userID,applicationID);
        try{
            Point cpuUsagePoint = Point.measurement("warCpuUsage")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag("userID",response.getBody().getUserID().toString())
                    .tag("applicationID",response.getBody().getApplicationID().toString())
                    .addField("cpu_usage",cpuUsage)
                    .build();
            metricsService.insertMetrics(cpuUsagePoint);
        }
        catch (NullPointerException n){ }
        return true;
    }
}
