package com.stackroute.alertmanager.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.stackroute.alertmanager.comparator.DiskComparator;
import com.stackroute.domain.Alert;
import com.stackroute.domain.HealthMetrics;
import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.HealthMetricsDetails;
import com.stackroute.domain.KafkaDomain;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class HardDiskConsumer {
    private static KafkaTemplate<String, Alert> kafkaTemplate;
    private static String TOPIC;
    private MetricsService metricsService;
    private DiskComparator diskComparator;
    @Autowired
    public HardDiskConsumer(MetricsService metricsService, DiskComparator diskComparator, KafkaTemplate<String, Alert> kafkaTemplate) {
        this.metricsService = metricsService;
        this.diskComparator = diskComparator;
        if (HardDiskConsumer.kafkaTemplate == null) {
            HardDiskConsumer.kafkaTemplate = kafkaTemplate;
        }
        HardDiskConsumer.TOPIC="alert";
    }

    int userId;
    int applicationId;
    KafkaDomain inputSample;
    KafkaDomain inputLive;
    long timeSample;
    long timeLive;

    @KafkaListener(topics = "hardDisk", containerFactory = "kafkaListenerContainerFactory")
    public String consumeMetrics(@Payload KafkaDomain message){
        this.inputSample=message;
        this.userId= inputSample.getUserId();
        this.applicationId=inputSample.getApplicationId();
        this.timeSample=inputSample.getTime();
        System.out.println(inputSample);
//        System.out.println("Outside try");

        Gson gson=new Gson();
        String json=gson.toJson(inputSample.getMetrics());
        HealthMetrics healthMetrics=gson.fromJson(json,HealthMetrics.class);

        try{
//             System.out.println("Inside try");
             Point diskPoint = Point.measurement("disk_utilization")
                    .time(timeSample, TimeUnit.MILLISECONDS)
                    .tag("userID",String.valueOf(userId))
                    .tag("applicationID",String.valueOf(applicationId))
                    .addField("details_diskFree",  Long.parseLong(healthMetrics.getDetails().getDiskFree()))
                    .addField("details_diskTotal",  healthMetrics.getDetails().getDiskTotal())
                    .build();
            metricsService.insertMetrics(diskPoint,"samplingMetrics");

            String diskTotalSample = healthMetrics.getDetails().getDiskTotal();

            try{
                    System.out.println("Inside try");
                Point totalDiskSample = Point.measurement("diskTotalSampling")
                        .time(0,TimeUnit.MILLISECONDS)
                        .tag("userID",String.valueOf(userId))
                        .tag("applicationID",String.valueOf(applicationId))
                        .addField("disk_Total_Sample",diskTotalSample)
                        .build();
                metricsService.insertMetrics(totalDiskSample,"threshold");

            }  catch (NullPointerException n){        }
        }

        catch (NullPointerException n){
            System.out.println("disk null.... "+healthMetrics);
        }

        return "";
    }

    @KafkaListener(topics = "hardDiskLive1", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetricsLive ( KafkaDomain message) {
        this.inputLive = message;
        this.userId = inputLive.getUserId();
        this.applicationId = inputLive.getApplicationId();
        this.timeLive = inputLive.getTime();

        Gson gson=new Gson();
        String json=gson.toJson(inputLive.getMetrics());
        HealthMetrics healthMetricsLive = gson.fromJson(json,HealthMetrics.class);
//        ObjectMapper mapper=new ObjectMapper();
//        HealthMetrics healthMetricsLive = mapper.convertValue(inputLive,HealthMetrics.class);
        long diskTotalLive = Long.parseLong(healthMetricsLive.getDetails().getDiskTotal());
        long diskFreeLive = Long.parseLong(healthMetricsLive.getDetails().getDiskFree());
        int alertLevel;
        long diskTotalSample = (Long)(metricsService.searchMetrics("diskTotalSampling",userId,applicationId,"threshold").getResults().get(0).getSeries().get(0).getValues().get(0).get(2));

        try {
            alertLevel = diskComparator.compareValues(diskFreeLive, diskTotalLive, diskTotalSample);

            System.out.println("AlertLevel = " + alertLevel);

            if (alertLevel>0){
                Alert alert = new Alert();
                alert.setApplicationId(applicationId);
                alert.setUserId(userId);
                alert.setTime(timeLive);
                alert.setMetricsName("diskUsage");
                alert.setAlertLevel(alertLevel);
                kafkaTemplate.send(TOPIC,alert);
            }

        }  catch (NullPointerException n){        }
        return true;

    }
}
