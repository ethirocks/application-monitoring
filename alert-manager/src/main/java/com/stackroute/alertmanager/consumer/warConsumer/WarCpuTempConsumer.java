package com.stackroute.alertmanager.consumer.warConsumer;

import com.stackroute.alertmanager.comparator.TempComparator;
import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.Alert;
import com.stackroute.domain.KafkaDomain;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class WarCpuTempConsumer {
    private MetricsService metricsService;
    private static KafkaTemplate<String, Alert> kafkaTemplate;
    private static String TOPIC;
    private TempComparator tempComparator;
    @Autowired
    public WarCpuTempConsumer(MetricsService metricsService, TempComparator tempComparator, KafkaTemplate<String, Alert> kafkaTemplate) {
        this.metricsService = metricsService;
        this.metricsService = metricsService;
        this.tempComparator =tempComparator;
        if (WarCpuTempConsumer.kafkaTemplate == null) {
            WarCpuTempConsumer.kafkaTemplate = kafkaTemplate;
        }
        WarCpuTempConsumer.TOPIC="alert";
    }

    int userId;
    int applicationId;
    KafkaDomain inputSample;
    KafkaDomain inputLive;
    long timeSample;
    long timeLive;
    @KafkaListener(topics = "warCpuTemp", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics(@Payload KafkaDomain message)  {
        this.inputSample=message;
        this.userId= inputSample.getUserId();
        this.applicationId=inputSample.getApplicationId();
        this.timeSample=inputSample.getTime();

        Double cpuTemp = (Double)(inputSample.getMetrics());

        try{
            Point cpuTempPoint = Point.measurement("warCpuTemp")
                    .time(timeSample, TimeUnit.MILLISECONDS)
                    .tag("userID",String.valueOf(userId))
                    .tag("applicationID",String.valueOf(applicationId))
                    .addField("cpu_temp",cpuTemp)
                    .build();
            metricsService.insertMetrics(cpuTempPoint,"samplingMetrics");
            Double meanTemp =(Double)(metricsService.getMean("cpu_temp","cputemp").getResults().get(0).getSeries().get(0).getValues().get(0).get(1));
//            System.out.println(meanTemp);
            try{
//                    System.out.println("Inside try");
                Point cpuTempThreshold = Point.measurement("warCputempthreshold")
                        .time(0,TimeUnit.MILLISECONDS)
                        .tag("userID",String.valueOf(userId))
                        .tag("applicationID",String.valueOf(applicationId))
                        .addField("war_cpu_temp_threshold",meanTemp)
                        .build();
                metricsService.insertMetrics(cpuTempThreshold,"threshold");

            }  catch (NullPointerException n){        }
        }

        catch (NullPointerException n){        }
        return true;
    }

    @KafkaListener(topics = "warCpuTempLive", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetricsLive ( KafkaDomain message) {
        this.inputLive = message;
        this.userId= inputLive.getUserId();
        this.applicationId=inputLive.getApplicationId();
        this.timeLive=inputLive.getTime();

        Double cpuTempLive = (Double)(inputLive.getMetrics());
        System.out.println("Cputemplive = "+cpuTempLive);

        Double tempThreshold = (Double)(metricsService.searchMetrics("warCputempthreshold",userId,applicationId,"threshold").getResults().get(0).getSeries().get(0).getValues().get(0).get(2));
        System.out.println("tempthresh"+tempThreshold);
        try {
            int alertLevel = tempComparator.compareValues(cpuTempLive, tempThreshold);
            System.out.println("AlertLevel = " + alertLevel);

            Alert alert = new Alert();
            alert.setApplicationId(applicationId);
            alert.setUserId(userId);
            alert.setTime(timeLive);
            alert.setMetricsName("warCpuTemperature");
            alert.setAlertLevel(alertLevel);
            kafkaTemplate.send(TOPIC,alert);

        }  catch (NullPointerException n){        }
        return true;
    }
}

