package com.stackroute.alertmanager.consumer.warConsumer;

import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.KafkaDomain;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class WarCpuCoresConsumer  {

    private MetricsService metricsService;
    @Autowired
    public WarCpuCoresConsumer(MetricsService metricsService) {
            this.metricsService = metricsService;
        }

    int userId;
    int applicationId;
    KafkaDomain inputSample;
    long timeSample;

    @KafkaListener(topics = "warCpuCores", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics(@Payload KafkaDomain message){
        this.inputSample=message;
        this.userId= inputSample.getUserId();
        this.applicationId=inputSample.getApplicationId();
        this.timeSample=inputSample.getTime();

        Double cpuCores = (Double)(inputSample.getMetrics());

        try {
            Point cpuCoresPoint = Point.measurement("warCpuCoresSample")
                    .time(timeSample, TimeUnit.MILLISECONDS)
                    .tag("userID",String.valueOf(userId))
                    .tag("applicationID",String.valueOf(applicationId))
                    .addField("cpu_cores", cpuCores)
                    .build();
            metricsService.insertMetrics(cpuCoresPoint,"samplingMetrics");
        }
        catch (NullPointerException n){}
        return true;
    }
}

