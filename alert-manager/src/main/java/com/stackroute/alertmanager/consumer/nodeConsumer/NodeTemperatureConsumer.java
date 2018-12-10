package com.stackroute.alertmanager.consumer.nodeConsumer;

import com.google.gson.Gson;
import com.stackroute.alertmanager.comparator.TempComparator;
import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.Alert;
import com.stackroute.domain.KafkaDomain;
import com.stackroute.domain.nodeDomain.NodeTemperatureMetrics;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class NodeTemperatureConsumer{

    private static KafkaTemplate<String, Alert> kafkaTemplate;
    private static String TOPIC;
    private TempComparator tempComparator;
    private MetricsService metricsService;
    @Autowired
    public NodeTemperatureConsumer(MetricsService metricsService, TempComparator tempComparator, KafkaTemplate<String, Alert> kafkaTemplate) {
        this.metricsService = metricsService;
        this.tempComparator =tempComparator;
        if (NodeTemperatureConsumer.kafkaTemplate == null) {
            NodeTemperatureConsumer.kafkaTemplate = kafkaTemplate;
        }
        NodeTemperatureConsumer.TOPIC="alert";
    }

    int userId;
    int applicationId;
    KafkaDomain inputSample;
    KafkaDomain inputLive;
    long timeSample;
    long timeLive;
    @KafkaListener(topics = "nodeTemperature", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics(@Payload KafkaDomain message)  {
        this.inputSample=message;
        this.userId= inputSample.getUserId();
        this.applicationId=inputSample.getApplicationId();
        this.timeSample=inputSample.getTime();

        Gson gson=new Gson();
        String json=gson.toJson(inputSample.getMetrics());
        NodeTemperatureMetrics nodeTemperature=gson.fromJson(json,NodeTemperatureMetrics.class);

//        NodeTemperatureMetrics nodeTemperature = (NodeTemperatureMetrics) inputSample.getMetrics();


        try{
            Point nodeTemperaturePoint = Point.measurement("nodeTemperature")
                    .time(timeSample, TimeUnit.MILLISECONDS)
                    .tag("userID",String.valueOf(userId))
                    .tag("applicationID",String.valueOf(applicationId))
                    .addField("temp",nodeTemperature.getTemperature())
                    .build();
            metricsService.insertMetrics(nodeTemperaturePoint,"samplingMetrics");
            Double meanTemp =(Double)(metricsService.getMean("temp","nodeTemperature").getResults().get(0).getSeries().get(0).getValues().get(0).get(1));
            try{
                System.out.println("meantemp... "+meanTemp);
                Point nodeTemperatureThreshold = Point.measurement("nodeTemperaturethreshold")
                        .time(0,TimeUnit.MILLISECONDS)
                        .tag("userID",String.valueOf(userId))
                        .tag("applicationID",String.valueOf(applicationId))
                        .addField("node_temp_threshold",meanTemp)
                        .build();
                metricsService.insertMetrics(nodeTemperatureThreshold,"threshold");

            }  catch (NullPointerException n){        }
        }

        catch (NullPointerException n){        }

        return true;
    }
    @KafkaListener(topics = "nodeTemperatureLive1", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetricsLive ( KafkaDomain message) {
        this.inputLive = message;
        this.userId= inputLive.getUserId();
        this.applicationId=inputLive.getApplicationId();
        this.timeLive=inputLive.getTime();

        Gson gson=new Gson();
        String json=gson.toJson(inputLive.getMetrics());
        NodeTemperatureMetrics nodeTemperatureLive=gson.fromJson(json,NodeTemperatureMetrics.class);

//        NodeTemperatureMetrics nodeTemperatureLive = (NodeTemperatureMetrics) inputLive.getMetrics();
        System.out.println("NodeTemperaturelive = "+nodeTemperatureLive);
        System.out.println(applicationId+"...."+userId+"...result "+metricsService.searchMetrics("nodeTemperaturethreshold",userId,applicationId,"threshold"));
        Double tempThreshold = (Double)(metricsService.searchMetrics("nodeTemperaturethreshold",applicationId,userId,"threshold").getResults().get(0).getSeries().get(0).getValues().get(0).get(2));
        System.out.println("tempthresh"+tempThreshold);
        try {
            int alertLevel = tempComparator.compareValues(Double.valueOf(nodeTemperatureLive.getTemperature()), tempThreshold);
            System.out.println("AlertLevel = " + alertLevel);
            if (alertLevel>0){
                Alert alert = new Alert();
                alert.setApplicationId(applicationId);
                alert.setUserId(userId);
                alert.setTime(timeLive);
                alert.setMetricsName("nodeTemperature");
                alert.setAlertLevel(alertLevel);
                kafkaTemplate.send(TOPIC,alert);
            }

        }  catch (NullPointerException n){        }
        return true;
    }

}
