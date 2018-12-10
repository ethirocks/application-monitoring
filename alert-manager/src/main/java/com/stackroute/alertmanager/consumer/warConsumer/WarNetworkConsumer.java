package com.stackroute.alertmanager.consumer.warConsumer;

import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.KafkaDomain;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WarNetworkConsumer {
    private MetricsService metricsService;
    @Autowired
    public WarNetworkConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }
    int userId;
    int applicationId;
    KafkaDomain inputSample;
    long timeSample;
    @KafkaListener(topics = "warNetwork", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics(@Payload KafkaDomain message){
        this.inputSample=message;
        this.userId= inputSample.getUserId();
        this.applicationId=inputSample.getApplicationId();
        this.timeSample=inputSample.getTime();

        try {
            LinkedHashMap<String,LinkedHashMap<String,Object>> networkMetrics=  (LinkedHashMap<String,LinkedHashMap<String,Object>>)(inputSample.getMetrics());
            Iterator it = networkMetrics.entrySet().iterator();
            int i=1;
            while (it.hasNext()) {
                Map.Entry networkMetricEntry = (Map.Entry)it.next();
                LinkedHashMap<String,Object> metric=networkMetrics.get((String.valueOf(i)));
                System.out.println();
                long time = System.currentTimeMillis();
                Point point = Point.measurement("warNetworkMetricsSample")
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
                metricsService.insertMetrics(point, "samplingMetrics");
                i++;
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        catch (NullPointerException n){
        }
        return true;
    }
}
