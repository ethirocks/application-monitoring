package com.stackroute.alertmanager.consumer.nodeConsumer;

import com.google.gson.Gson;
import com.stackroute.alertmanager.comparator.RamComparator;
import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.Alert;
import com.stackroute.domain.HealthMetrics;
import com.stackroute.domain.KafkaDomain;
import com.stackroute.domain.nodeDomain.NodeMemoryMetrics;
import org.influxdb.dto.Point;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
public class NodeMemoryConsumer{
    private static KafkaTemplate<String, Alert> kafkaTemplate;
    private static String TOPIC;
    private MetricsService metricsService;
    private RamComparator nodeMemoryComparator;

    @Autowired
    public NodeMemoryConsumer(MetricsService metricsService, RamComparator nodeMemoryComparator, KafkaTemplate<String, Alert> kafkaTemplate) {
        this.metricsService = metricsService;
        this.nodeMemoryComparator = nodeMemoryComparator;
        if (NodeMemoryConsumer.kafkaTemplate == null) {
            NodeMemoryConsumer.kafkaTemplate = kafkaTemplate;
        }
        NodeMemoryConsumer.TOPIC = "alert";
    }

    int userId;
    int applicationId;
    KafkaDomain inputSample;
    KafkaDomain inputLive;
    long timeSample;
    long timeLive;

    @KafkaListener(topics = "nodeMemory", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics(@Payload KafkaDomain message) {
        this.inputSample = message;
        this.userId = inputSample.getUserId();
        this.applicationId = inputSample.getApplicationId();
        this.timeSample = inputSample.getTime();

        Gson gson=new Gson();
        String json=gson.toJson(inputSample.getMetrics());
        NodeMemoryMetrics memory=gson.fromJson(json,NodeMemoryMetrics.class);

//        NodeMemoryMetrics memory = (NodeMemoryMetrics) inputSample.getMetrics();

        try {
            Point cpuCoresPoint = Point.measurement("nodeMemory")
                    .time(timeSample, TimeUnit.MILLISECONDS)
                    .tag("userID", String.valueOf(userId))
                    .tag("applicationID", String.valueOf(applicationId))
                    .addField("free_memory", Double.parseDouble(memory.getMemory()))
                    .addField("total_memory", Double.parseDouble(memory.getTotalmemory()))
                    .build();
            metricsService.insertMetrics(cpuCoresPoint, "samplingMetrics");

            double nodeMemoryTotalSample = Double.parseDouble(memory.getTotalmemory());
            try {
//                    System.out.println("Inside try");
                Point totalNodeMemorySample = Point.measurement("nodeMemoryTotalSampling")
                        .time(0, TimeUnit.MILLISECONDS)
                        .tag("userID", String.valueOf(userId))
                        .tag("applicationID", String.valueOf(applicationId))
                        .addField("nodeMemory_Total_Sample", nodeMemoryTotalSample)
                        .build();
                metricsService.insertMetrics(totalNodeMemorySample, "threshold");

            } catch (NullPointerException n) {
            }
        } catch (NullPointerException n) {
        }
        return true;
    }

    @KafkaListener(topics = "nodeMemoryLive1", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetricsLive(KafkaDomain message) {
        this.inputLive = message;
        this.userId = inputLive.getUserId();
        this.applicationId = inputLive.getApplicationId();
        this.timeLive = inputLive.getTime();

        System.out.println("\n............................"+inputLive+"\n");

        Gson gson=new Gson();
        String json=gson.toJson(inputLive.getMetrics());
        NodeMemoryMetrics memory=gson.fromJson(json,NodeMemoryMetrics.class);

//        NodeMemoryMetrics memory = (NodeMemoryMetrics) inputLive.getMetrics();
        double nodeMemoryTotalLive = Double.parseDouble(memory.getTotalmemory());
        double nodeMemoryFreeLive = Double.parseDouble(memory.getMemory());
        int alertLevel;
        double nodeMemoryTotalSample = (double) (metricsService.searchMetrics("nodeMemoryTotalSampling",  applicationId,userId, "threshold").getResults().get(0).getSeries().get(0).getValues().get(0).get(2));
        System.out.println(nodeMemoryTotalSample+"   ///////");
        try {
            alertLevel = nodeMemoryComparator.compareValues(nodeMemoryTotalLive, nodeMemoryFreeLive, nodeMemoryTotalSample);

            System.out.println("AlertLevel  mem = " + alertLevel);
            if(alertLevel>0) {
                Alert alert = new Alert();
                alert.setApplicationId(applicationId);
                alert.setUserId(userId);
                alert.setTime(timeLive);
                alert.setMetricsName("nodeMemoryUsage");
                alert.setAlertLevel(alertLevel);
                kafkaTemplate.send(TOPIC, alert);
                System.out.println(alert+"........"+TOPIC);
            }
        } catch (NullPointerException n) {
        }
        return true;

    }
    
}
