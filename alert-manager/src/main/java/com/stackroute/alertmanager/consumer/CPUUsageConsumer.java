package com.stackroute.alertmanager.consumer;

import com.stackroute.alertmanager.comparator.UsageComparator;
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
public class CPUUsageConsumer  {
    private static KafkaTemplate<String, Alert> kafkaTemplate;
    private static String TOPIC;
    private MetricsService metricsService;
    private UsageComparator usageComparator;

    @Autowired
    public CPUUsageConsumer(MetricsService metricsService, UsageComparator usageComparator, KafkaTemplate<String, Alert> kafkaTemplate) {
        this.metricsService = metricsService;
        this.usageComparator = usageComparator;
        if (CPUUsageConsumer.kafkaTemplate == null) {
            CPUUsageConsumer.kafkaTemplate = kafkaTemplate;
        }
        CPUUsageConsumer.TOPIC="alert";

    }
    int userId;
    int applicationId;
    KafkaDomain inputSample;
    KafkaDomain inputLive;
    long timeSample;
    long timeLive;

    @KafkaListener(topics = "cpuUsage", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics(@Payload KafkaDomain message){
        this.inputSample=message;
        this.userId= inputSample.getUserId();
        this.applicationId=inputSample.getApplicationId();
        this.timeSample=inputSample.getTime();

        Double cpuUsage = (Double)(inputSample.getMetrics());


        try{
            Point cpuUsagePoint = Point.measurement("cpuusage")
                    .time(timeSample, TimeUnit.MILLISECONDS)
                    .addField("cpu_usage",cpuUsage)
                    .tag("userID",String.valueOf(userId))
                    .tag("applicationID",String.valueOf(applicationId))
                    .build();
            metricsService.insertMetrics(cpuUsagePoint,"samplingMetrics");

            Double meanCpuUsage =(Double)(metricsService.getMean("cpu_usage","cpuusage").getResults().get(0).getSeries().get(0).getValues().get(0).get(1));
            try{
//                    System.out.println("Inside try");
                Point cpuTempThreshold = Point.measurement("cpuusagethreshold")
                        .time(0,TimeUnit.MILLISECONDS)
                        .tag("userID",String.valueOf(userId))
                        .tag("applicationID",String.valueOf(applicationId))
                        .addField("cpu_usage_threshold",meanCpuUsage)
                        .build();
                metricsService.insertMetrics(cpuTempThreshold,"threshold");

            }  catch (NullPointerException n){        }

        }
        catch (NullPointerException n){ }
        Double usageThreshold = (Double)(metricsService.searchMetrics("cpuusagethreshold",userId,applicationId,"threshold").getResults().get(0).getSeries().get(0).getValues().get(0).get(2));
        System.out.println("usagethresh"+usageThreshold);
        return true;
    }
    @KafkaListener(topics = "cpuUsageLive1", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetricsLive ( KafkaDomain message) {
        this.inputLive = message;
        this.userId= inputLive.getUserId();
        this.applicationId=inputLive.getApplicationId();
        this.timeLive=inputLive.getTime();

        Double cpuUsageLive = (Double)(inputLive.getMetrics());
        System.out.println("CpuUsagelive = "+cpuUsageLive);

        Double usageThreshold = (Double)(metricsService.searchMetrics("cpuusagethreshold",userId,applicationId,"threshold").getResults().get(0).getSeries().get(0).getValues().get(0).get(2));
        System.out.println("usagethresh"+usageThreshold);
        try {
            int alertLevel = usageComparator.compareValues(cpuUsageLive, usageThreshold);

            System.out.println("AlertLevel = " + alertLevel);

            if (alertLevel>0){
                Alert alert = new Alert();
                alert.setApplicationId(applicationId);
                alert.setUserId(userId);
                alert.setTime(timeLive);
                alert.setMetricsName("cpuUsage");
                alert.setAlertLevel(alertLevel);
                kafkaTemplate.send(TOPIC,alert);
            }

        }  catch (NullPointerException n){        }
        return true;
    }

}
