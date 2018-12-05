package com.stackroute.alertmanager.consumer;

import com.stackroute.alertmanager.comparator.TempComparator;
import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.Alert;
import com.stackroute.domain.KafkaDomain;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.concurrent.TimeUnit;

public class ContainerTemperatureConsumer {
    private static KafkaTemplate<String, Alert> kafkaTemplate;
    private static String TOPIC;
    private TempComparator tempComparator;
    private MetricsService metricsService;
    @Autowired
    public ContainerTemperatureConsumer(MetricsService metricsService, TempComparator tempComparator, KafkaTemplate<String, Alert> kafkaTemplate) {
        this.metricsService = metricsService;
        this.tempComparator =tempComparator;
        if (ContainerTemperatureConsumer.kafkaTemplate == null) {
            ContainerTemperatureConsumer.kafkaTemplate = kafkaTemplate;
        }
        ContainerTemperatureConsumer.TOPIC = "alert";
    }

    int userId;
    int applicationId;
    KafkaDomain inputSample;
    KafkaDomain inputLive;
    long timeSample;
    long timeLive;
    @KafkaListener(topics = "containerTemperature", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics(@Payload KafkaDomain message)  {
        this.inputSample=message;
        this.userId= inputSample.getUserId();
        this.applicationId=inputSample.getApplicationId();
        this.timeSample=inputSample.getTime();

        Double containerTemp = (Double)(inputSample.getMetrics());

        try {
            Point containerTempPoint = Point.measurement("containertemp")
                    .time(timeSample, TimeUnit.MILLISECONDS)
                    .tag("userID", String.valueOf(userId))
                    .tag("applicationID", String.valueOf(applicationId))
                    .addField("container_temp", containerTemp)
                    .build();
            metricsService.insertMetrics(containerTempPoint, "samplingMetrics");
            Double meanTemp = (Double) (metricsService.getMean("container_temp", "containertemp").getResults().get(0).getSeries().get(0).getValues().get(0).get(1));
//            System.out.println(meanTemp);
            try {
//                    System.out.println("Inside try");
                Point containerTempThreshold = Point.measurement("containertempthreshold")
                        .time(0, TimeUnit.MILLISECONDS)
                        .tag("userID", String.valueOf(userId))
                        .tag("applicationID", String.valueOf(applicationId))
                        .addField("container_temp_threshold", meanTemp)
                        .build();
                metricsService.insertMetrics(containerTempThreshold, "threshold");

            } catch (NullPointerException n) {
            }

        } catch (NullPointerException n){        }

            return true;
    }

    @KafkaListener(topics = "containerTemperatureLive", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetricsLive ( KafkaDomain message) {
        this.inputLive = message;
        this.userId= inputLive.getUserId();
        this.applicationId=inputLive.getApplicationId();
        this.timeLive=inputLive.getTime();

        Double containerTempLive = (Double)(inputLive.getMetrics());
        System.out.println("containertemplive = "+containerTempLive);


        Double tempThreshold = (Double)(metricsService.searchMetrics("containertempthreshold",userId,applicationId,"threshold").getResults().get(0).getSeries().get(0).getValues().get(0).get(2));
        System.out.println("tempthresh"+tempThreshold);
        try {
            int alertLevel = tempComparator.compareValues(containerTempLive, tempThreshold);
            System.out.println("AlertLevel = " + alertLevel);

            Alert alert = new Alert();
            alert.setApplicationId(applicationId);
            alert.setUserId(userId);
            alert.setTime(timeLive);
            alert.setMetricsName("containerTemperature");
            alert.setAlertLevel(alertLevel);
            kafkaTemplate.send(TOPIC,alert);

        }  catch (NullPointerException n){        }
        return true;
    }

}
