package com.stackroute.alertmanager.consumer;

import com.google.gson.Gson;
import com.stackroute.domain.Alert;
import com.stackroute.domain.ContainerMetricsSystemUsage;
import com.stackroute.domain.HealthMetrics;
import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.KafkaDomain;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class HealthConsumer {
    private MetricsService metricsService;
    private static KafkaTemplate<String, Alert> kafkaTemplate;
    private static String TOPIC;
    @Autowired
    public HealthConsumer(MetricsService metricsService, KafkaTemplate<String, Alert> kafkaTemplate) {
        this.metricsService = metricsService;
        if (HealthConsumer.kafkaTemplate == null) {
            HealthConsumer.kafkaTemplate = kafkaTemplate;
        }
        HealthConsumer.TOPIC="alert";
    }

    int userId;
    int applicationId;
    KafkaDomain inputSample;
    KafkaDomain inputLive;
    long timeSample;
    long timeLive;

    @KafkaListener(topics = "health", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics(@Payload KafkaDomain message){
        this.inputSample=message;
        this.userId= inputSample.getUserId();
        this.applicationId=inputSample.getApplicationId();
        this.timeSample=inputSample.getTime();

        Gson gson=new Gson();
        String json=gson.toJson(inputSample.getMetrics());
        HealthMetrics healthMetrics = gson.fromJson(json, HealthMetrics.class);

        try{
            Point healthPoint = Point.measurement("health")
                    .time(timeSample, TimeUnit.MILLISECONDS)
                    .addField("status",  healthMetrics.getStatus())
                    .tag("userID",String.valueOf(userId))
                    .tag("applicationID",String.valueOf(applicationId))
                    .build();
            metricsService.insertMetrics(healthPoint,"samplingMetrics");

        }
        catch (NullPointerException n){ }
        return true;
    }

    @KafkaListener(topics = "healthLive", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetricsLive ( KafkaDomain message) {
        this.inputLive = message;
        this.userId = inputLive.getUserId();
        this.applicationId = inputLive.getApplicationId();
        this.timeLive = inputLive.getTime();

        Gson gson=new Gson();
        String json=gson.toJson(inputLive.getMetrics());
        HealthMetrics healthMetricsLive = gson.fromJson(json, HealthMetrics.class);
//        HealthMetrics healthMetricsLive = (HealthMetrics) (inputLive.getMetrics());
        int alertLevel;
        try {
            if(healthMetricsLive.getStatus()=="DOWN"){
                alertLevel = 4;
            }else{
                alertLevel = 0;
            }
            System.out.println("AlertLevel = " + alertLevel);

            if(alertLevel>0) {
                Alert alert = new Alert();
                alert.setApplicationId(applicationId);
                alert.setUserId(userId);
                alert.setTime(timeLive);
                alert.setMetricsName("health");
                alert.setAlertLevel(alertLevel);
                kafkaTemplate.send(TOPIC, alert);
            }
        }  catch (NullPointerException n){        }
        return true;
    }
}