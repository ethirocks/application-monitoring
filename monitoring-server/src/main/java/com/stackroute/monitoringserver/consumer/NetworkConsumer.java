package com.stackroute.monitoringserver.consumer;

import com.stackroute.monitoringserver.domain.ContainerMetrics;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NetworkConsumer implements IConsumer{
    private MetricsService metricsService;
    @Autowired
    public NetworkConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public boolean consumeMetrics(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LinkedHashMap<String,LinkedHashMap<String,Object>>> response = restTemplate.exchange(
                url+"/network",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<LinkedHashMap<String,LinkedHashMap<String,Object>>>(){});

        try {
            LinkedHashMap<String,LinkedHashMap<String,Object>> networkMetrics= response.getBody();
            Iterator it = networkMetrics.entrySet().iterator();
            int i=1;
            while (it.hasNext()) {
                Map.Entry networkMetricEntry = (Map.Entry)it.next();
                LinkedHashMap<String,Object> metric=networkMetrics.get((String.valueOf(i)));

                long time = System.currentTimeMillis();
                Point point = Point.measurement("networkMetrics")
                        .time(time, TimeUnit.MILLISECONDS)
                        .addField("IPv4addr", metric.get("IPv4addr").toString())
                        .addField("IPv6addr", metric.get("IPv6addr").toString())
                        .addField("Interface_Name", metric.get("Interface_Name").toString())
                        .addField("Macaddr", metric.get("Macaddr").toString())
                        .addField("Maximum_Transmission_Unit", metric.get("Maximum_Transmission_Unit").toString())
                        .addField("DownLoad_Speed", metric.get("DownLoad_Speed").toString())
                        .addField("Upload_Speed", metric.get("Upload_Speed").toString())
                        .addField("Data_In_Rate", metric.get("Data_In_Rate").toString())
                        .addField("Data_Out_Rate", metric.get("Data_Out_Rate").toString())
                        .addField("Error_In_Rate", metric.get("Error_In_Rate").toString())
                        .addField("Error_Out_Rate", metric.get("Error_Out_Rate").toString())
                        .build();
                metricsService.insertMetrics(point);
                i++;
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        catch (NullPointerException n){
            System.out.println("network null... ");
        }
        return true;
    }
}
