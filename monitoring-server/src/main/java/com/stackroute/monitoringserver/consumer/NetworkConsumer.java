package com.stackroute.monitoringserver.consumer;

import com.stackroute.monitoringserver.domain.ContainerMetrics;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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
    public void consumeMetrics(String url) {
        RestTemplate restTemplate = new RestTemplate();
        //String url = "http://172.23.239.222:8082/Container";
        ResponseEntity<LinkedHashMap<String,Object>> response = restTemplate.exchange(
                url+"/network",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<LinkedHashMap<String,Object>>(){});
        System.out.println("Container "+response.toString());
        LinkedHashMap<String,Object> networkMetrics= response.getBody();
        try {
            long time = System.currentTimeMillis();
            Point point = Point.measurement("networkMetrics")
                    .time(time, TimeUnit.MILLISECONDS)
                    .addField("IPv4addr", networkMetrics.get("IPv4addr").toString())
                    .addField("IPv6addr", networkMetrics.get("IPv6addr").toString())
                    .addField("Interface_Name", networkMetrics.get("Interface_Name").toString())
                    .addField("Macaddr", networkMetrics.get("Macaddr").toString())
                    .addField("Maximum_Transmission_Unit", networkMetrics.get("Maximum_Transmission_Unit").toString())
                    .addField("DownLoad_Speed", networkMetrics.get("DownLoad_Speed").toString())
                    .addField("Upload_Speed", networkMetrics.get("Upload_Speed").toString())
                    .addField("Data_In_Rate", networkMetrics.get("Data_In_Rate").toString())
                    .addField("Data_Out_Rate", networkMetrics.get("Data_Out_Rate").toString())
                    .addField("Error_In_Rate", networkMetrics.get("Error_In_Rate").toString())
                    .addField("Error_Out_Rate", networkMetrics.get("Error_Out_Rate").toString())
                    .build();
            metricsService.insertMetrics(point);
            System.out.println("point.......    " + point.toString());
        }
        catch (NullPointerException n){ }
    }
}
