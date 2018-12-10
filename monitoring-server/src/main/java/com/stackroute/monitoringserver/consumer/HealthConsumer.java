package com.stackroute.monitoringserver.consumer;

import com.stackroute.domain.GenericMetrics;
import com.stackroute.domain.HealthMetrics;
import com.stackroute.monitoringserver.service.KafkaService;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public boolean consumeMetrics(String url, Integer userID, Integer applicationID) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GenericMetrics<HealthMetrics>> response
                = restTemplate.exchange(url+"/health?userID="+userID+"&applicationID="+applicationID,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<HealthMetrics>>(){});
        Logger.getLogger("health "+response.toString());
        HealthMetrics healthMetrics= response.getBody().getMetrics();
        KafkaService kafkaService=new KafkaService();
        kafkaService.produce(response.getBody().getMetrics(),"healthLive1",userID,applicationID);
        try{
            Point healthPoint = Point.measurement("health")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag("userID",response.getBody().getUserID().toString())
                    .tag("applicationID",response.getBody().getApplicationID().toString())
                    .addField("status",  healthMetrics.getStatus())
                    .build();
            metricsService.insertMetrics(healthPoint);
        }
        catch (NullPointerException n){ }
        return true;
    }
}
