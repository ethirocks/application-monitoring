package com.stackroute.monitoringserver.consumer.warConsumer;

import com.stackroute.monitoringserver.consumer.IConsumer;
import com.stackroute.domain.GenericMetrics;
import com.stackroute.monitoringserver.service.KafkaService;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WarNetworkConsumer implements IConsumer{
    private MetricsService metricsService;
    @Autowired
    public WarNetworkConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public boolean consumeMetrics(String url, Integer userID, Integer applicationID) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GenericMetrics<LinkedHashMap<String, LinkedHashMap<String, Object>>>> response = restTemplate.exchange(
                url+"/network?userID="+userID+"&applicationID="+applicationID,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<LinkedHashMap<String,LinkedHashMap<String,Object>>>>(){});
        KafkaService kafkaService=new KafkaService();
        kafkaService.produce(response.getBody().getMetrics(),"warNetworkLive",userID,applicationID);

        try {
            LinkedHashMap<String,LinkedHashMap<String,Object>> networkMetrics= response.getBody().getMetrics();
            Iterator it = networkMetrics.entrySet().iterator();
            int i=1;
            while (it.hasNext()) {
                Map.Entry networkMetricEntry = (Map.Entry)it.next();
                LinkedHashMap<String,Object> metric=networkMetrics.get((String.valueOf(i)));
                System.out.println();
                long time = System.currentTimeMillis();
                Point point = Point.measurement("warNetworkMetrics")
                        .time(time, TimeUnit.MILLISECONDS)
                        .tag("userID",response.getBody().getUserID().toString())
                        .tag("applicationID",response.getBody().getApplicationID().toString())
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
            System.out.println("war network null... ");
        }
        return true;
    }
}
