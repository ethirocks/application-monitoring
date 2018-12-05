package com.stackroute.alertmanager.consumer;

import com.stackroute.alertmanager.comparator.TempComparator;
import com.stackroute.domain.Alert;
import com.stackroute.domain.KafkaDomain;
import com.stackroute.alertmanager.service.MetricsService;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CPUTempConsumer {

    private static KafkaTemplate<String, Alert> kafkaTemplate;
    private static String TOPIC;
    private TempComparator tempComparator;
//    private RedisService redisService;
    private MetricsService metricsService;
    @Autowired
    public CPUTempConsumer(MetricsService metricsService, TempComparator tempComparator, KafkaTemplate<String, Alert> kafkaTemplate) {
        this.metricsService = metricsService;
//        this.redisService = redisService;
        this.tempComparator =tempComparator;
        if (CPUTempConsumer.kafkaTemplate == null) {
            CPUTempConsumer.kafkaTemplate = kafkaTemplate;
        }
        CPUTempConsumer.TOPIC="alert";
    }

    int userId;
    int applicationId;
    KafkaDomain inputSample;
    KafkaDomain inputLive;
    long timeSample;
    long timeLive;
    @KafkaListener(topics = "cpuTemp", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics(@Payload KafkaDomain message)  {
        this.inputSample=message;

//        for(String key: inputSample.keySet()){
//            timeSample = key;
//            System.out.println(time);
//        }

        this.userId= inputSample.getUserId();
        this.applicationId=inputSample.getApplicationId();
        this.timeSample=inputSample.getTime();

        Double cpuTemp = (Double)(inputSample.getMetrics());

//        System.out.println(timeSample);
//        System.out.println(cpuTemp);
//        System.out.println("12");


        try{
            Point cpuTempPoint = Point.measurement("cputemp")
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
                    Point cpuTempThreshold = Point.measurement("cputempthreshold")
                            .time(0,TimeUnit.MILLISECONDS)
                            .tag("userID",String.valueOf(userId))
                            .tag("applicationID",String.valueOf(applicationId))
                            .addField("cpu_temp_threshold",meanTemp)
                            .build();
                    metricsService.insertMetrics(cpuTempThreshold,"threshold");

                }  catch (NullPointerException n){        }

//        if((thresholdValue.getApplicationId().isEmpty() && thresholdValue.getMetricName().isEmpty())){
//                System.out.println("007");
//                thresholdValue.setApplicationId(applicationId);
//            System.out.println(thresholdValue.getThresholdValue());
//
//            thresholdValue.setMetricName("cputemp");
//                thresholdValue.setUserId(userId);
//                thresholdValue.setThresholdValue(meanTemp.toString());
//            System.out.println("Chal jaa bhai");
//            System.out.println(thresholdValue);
//                redisService.saveThresholdValue(thresholdValue);
//                System.out.println("Chal bhi jaa bhai");
//
//            }else{
//                redisService.updateThresholdValue(applicationId,userId,"cputemp",meanTemp.toString());
//            }
        }

        catch (NullPointerException n){        }
//        Double tempThreshold = (Double)(metricsService.searchMetrics("cputempthreshold",userId,applicationId,"threshold").getResults().get(0).getSeries().get(0).getValues().get(0).get(2));
//        System.out.println("tempthresh"+tempThreshold);

        return true;
    }
    @KafkaListener(topics = "cpuTempLive", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetricsLive ( KafkaDomain message) {
        this.inputLive = message;
//        for(String key: inputLive.keySet()){
//            timeLive = key;
//            System.out.println(time);
//        }
        this.userId= inputLive.getUserId();
        this.applicationId=inputLive.getApplicationId();
        this.timeLive=inputLive.getTime();

        Double cpuTempLive = (Double)(inputLive.getMetrics());
        System.out.println("Cputemplive = "+cpuTempLive);

//        Double cpuTempLive = inputLive.get(timeLive);

//        thresholdValue=redisService.getThresholdValueById(applicationId);

//        Double tempThreshold = Double.parseDouble(thresholdValue.getThresholdValue());

        Double tempThreshold = (Double)(metricsService.searchMetrics("cputempthreshold",userId,applicationId,"threshold").getResults().get(0).getSeries().get(0).getValues().get(0).get(2));
        System.out.println("tempthresh"+tempThreshold);
        try {
            int alertLevel = tempComparator.compareValues(cpuTempLive, tempThreshold);
            System.out.println("AlertLevel = " + alertLevel);

            Alert alert = new Alert();
            alert.setApplicationId(applicationId);
            alert.setUserId(userId);
            alert.setTime(timeLive);
            alert.setMetricsName("cpuTemperature");
            alert.setAlertLevel(alertLevel);
            kafkaTemplate.send(TOPIC,alert);

        }  catch (NullPointerException n){        }
        return true;
    }
}
