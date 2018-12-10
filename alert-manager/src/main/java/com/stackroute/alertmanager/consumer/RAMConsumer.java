package com.stackroute.alertmanager.consumer;

import com.stackroute.alertmanager.comparator.RamComparator;
import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.Alert;
import com.stackroute.domain.KafkaDomain;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
public class RAMConsumer {
    private static KafkaTemplate<String, Alert> kafkaTemplate;
    private static String TOPIC;
    private MetricsService metricsService;
    private RamComparator ramComparator;
    @Autowired
    public RAMConsumer(MetricsService metricsService, RamComparator ramComparator, KafkaTemplate<String, Alert> kafkaTemplate) {
        this.metricsService = metricsService;
        this.ramComparator = ramComparator;
        if (RAMConsumer.kafkaTemplate == null) {
            RAMConsumer.kafkaTemplate = kafkaTemplate;
        }
        RAMConsumer.TOPIC="alert";
    }

    int userId;
    int applicationId;
    KafkaDomain inputSample;
    KafkaDomain inputLive;
    long timeSample;
    long timeLive;

    @KafkaListener(topics = "ram", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics(@Payload KafkaDomain message){
        this.inputSample=message;
        this.userId= inputSample.getUserId();
        this.applicationId=inputSample.getApplicationId();
        this.timeSample=inputSample.getTime();

        ArrayList<Long> memory = (ArrayList<Long>)(inputSample.getMetrics());

        try {
            Point cpuCoresPoint = Point.measurement("memory")
                    .time(timeSample, TimeUnit.MILLISECONDS)
                    .tag("userID",String.valueOf(userId))
                    .tag("applicationID",String.valueOf(applicationId))
                    .addField("free_physical_memory", memory.get(1))
                    .addField("total_physical_memory", memory.get(0))
                    .build();
            metricsService.insertMetrics(cpuCoresPoint,"samplingMetrics");

            long ramTotalSample = memory.get(0);
            try{
//                    System.out.println("Inside try");
                Point totalRamSample = Point.measurement("ramTotalSampling")
                        .time(0,TimeUnit.MILLISECONDS)
                        .tag("userID",String.valueOf(userId))
                        .tag("applicationID",String.valueOf(applicationId))
                        .addField("ram_Total_Sample",ramTotalSample)
                        .build();
                metricsService.insertMetrics(totalRamSample,"threshold");

            }  catch (NullPointerException n){        }
        }
        catch (NullPointerException n){}
        return true;
    }

    @KafkaListener(topics = "ramLive1", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetricsLive ( KafkaDomain message) {
        this.inputLive = message;
        this.userId = inputLive.getUserId();
        this.applicationId = inputLive.getApplicationId();
        this.timeLive = inputLive.getTime();

        ArrayList<Long> memory = (ArrayList<Long>)(inputLive.getMetrics());
        long ramTotalLive = memory.get(0);
        long ramFreeLive = memory.get(1);
        int alertLevel;
        long ramTotalSample = (long)(metricsService.searchMetrics("ramTotalSampling",userId,applicationId,"threshold").getResults().get(0).getSeries().get(0).getValues().get(0).get(2));

        try {
            alertLevel = ramComparator.compareValues(ramTotalLive, ramFreeLive, ramTotalSample);

            System.out.println("AlertLevel = " + alertLevel);

            if (alertLevel>0){
                Alert alert = new Alert();
                alert.setApplicationId(applicationId);
                alert.setUserId(userId);
                alert.setTime(timeLive);
                alert.setMetricsName("ramUsage");
                alert.setAlertLevel(alertLevel);
                kafkaTemplate.send(TOPIC,alert);
            }

        }  catch (NullPointerException n){        }
        return true;

    }
}
