package com.stackroute.monitoringserver.consumer;

import com.stackroute.monitoringserver.domain.GenericMetrics;
import com.stackroute.monitoringserver.domain.ThreadMetrics;
import com.stackroute.monitoringserver.service.KafkaService;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
    public boolean consumeMetrics(String url, Integer userID, Integer applicationID) throws IOException, JSONException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GenericMetrics<Double>> response
                = restTemplate.exchange(url+"/cputemp?userID=0&applicationID=0",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<Double>>(){});
        Double cpuTemp=response.getBody().getMetrics();
        KafkaService kafkaService=new KafkaService();
        kafkaService.produce(response.getBody().getMetrics(),"cpuTempLive",userID,applicationID);
        try{
            Point cpuTempPoint = Point.measurement("cputemp")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag("userID",String.valueOf(userID))
                    .tag("applicationID",String.valueOf(applicationID))
                    .addField("cpu_temp",cpuTemp)
                    .build();
            metricsService.insertMetrics(cpuTempPoint);
        }
        catch (NullPointerException n){        }
        return true;
    }
}
