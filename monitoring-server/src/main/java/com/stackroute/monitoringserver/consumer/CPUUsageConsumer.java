package com.stackroute.monitoringserver.consumer;

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

@Service
public class CPUUsageConsumer implements IConsumer {
    private MetricsService metricsService;

    @Autowired
    public CPUUsageConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public boolean consumeMetrics(String url, Integer userID, Integer applicationID) throws IOException, JSONException, URISyntaxException {
        try{
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GenericMetrics<Double>> response
                = restTemplate.exchange(url+"/cpuusage?userID="+userID+"&applicationID="+applicationID,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<Double>>(){});
    //    System.out.println("cpu usage "+response.toString());
        Double cpuUsage= response.getBody().getMetrics();
        KafkaService kafkaService=new KafkaService();
        kafkaService.produce(response.getBody().getMetrics(),"cpuUsageLive1",userID,applicationID);

            Point cpuUsagePoint = Point.measurement("cpuusage")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag("userID",response.getBody().getUserID().toString())
                    .tag("applicationID",response.getBody().getApplicationID().toString())
                    .addField("cpu_usage",cpuUsage)
                    .build();
            metricsService.insertMetrics(cpuUsagePoint);
        }
        catch (NullPointerException n){
            System.out.println("null cpu usage");
        }
        return true;
    }
}
