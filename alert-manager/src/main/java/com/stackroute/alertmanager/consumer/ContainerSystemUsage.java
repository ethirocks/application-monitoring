package com.stackroute.alertmanager.consumer;

import com.google.gson.Gson;
import com.stackroute.alertmanager.comparator.CpuComparatorContainer;
import com.stackroute.alertmanager.comparator.RamComparatorContainer;
import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.Alert;
import com.stackroute.domain.ContainerMetricsSystemUsage;
import com.stackroute.domain.KafkaDomain;
import com.stackroute.domain.warMetrics.WarThreadMetrics;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ContainerSystemUsage {
    private MetricsService metricsService;
    private static KafkaTemplate<String, Alert> kafkaTemplate;
    private RamComparatorContainer ramComparatorContainer;
    private CpuComparatorContainer cpuComparatorContainer;
    private static String TOPIC;

    @Autowired
    public ContainerSystemUsage(MetricsService metricsService, CpuComparatorContainer cpuComparatorContainer, RamComparatorContainer ramComparatorContainer, KafkaTemplate<String, Alert> kafkaTemplate) {
        this.metricsService = metricsService;
        this.cpuComparatorContainer = cpuComparatorContainer;
        this.ramComparatorContainer = ramComparatorContainer;
        if (ContainerSystemUsage.kafkaTemplate == null) {
            ContainerSystemUsage.kafkaTemplate = kafkaTemplate;
        }
        ContainerSystemUsage.TOPIC="alert";
    }

    int userId;
    int applicationId;
    KafkaDomain inputSample;
    KafkaDomain inputLive;
    long timeSample;
    long timeLive;

    @KafkaListener(topics = "containerSystemUsage", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics(@Payload KafkaDomain message) {
        this.inputSample = message;
        this.userId = inputSample.getUserId();
        this.applicationId = inputSample.getApplicationId();
        this.timeSample = inputSample.getTime();

        Gson gson=new Gson();
        String json=gson.toJson(inputSample.getMetrics());
        ContainerMetricsSystemUsage containerMetricsSystemUsage = gson.fromJson(json, ContainerMetricsSystemUsage.class);

        try{
            Point point = Point.measurement("containerSystemUsageSample")
                    .time(timeSample, TimeUnit.MILLISECONDS)
                    .addField("systemMemory",  containerMetricsSystemUsage.getSystemmemory())
                    .addField("systemCpu", containerMetricsSystemUsage.getSystemcpu())
                    .tag("userID",String.valueOf(userId))
                    .tag("applicationID",String.valueOf(applicationId))
                    .build();
            metricsService.insertMetrics(point,"samplingMetrics");

        }
        catch (NullPointerException n){ }
        return true;

    }

    @KafkaListener(topics = "containerSystemUsageLive", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetricsLive ( KafkaDomain message) {
        this.inputLive = message;
        this.userId = inputLive.getUserId();
        this.applicationId = inputLive.getApplicationId();
        this.timeLive = inputLive.getTime();
//        ContainerMetricsSystemUsage containerMetricsSystemUsage = (ContainerMetricsSystemUsage) (inputSample.getMetrics());
        Gson gson=new Gson();
        String json=gson.toJson(inputLive.getMetrics());
        ContainerMetricsSystemUsage containerMetricsSystemUsage = gson.fromJson(json, ContainerMetricsSystemUsage.class);
        long inputSystemMemory = Long.parseLong(containerMetricsSystemUsage.getSystemmemory().split(":")[0]);
        long totalSystemMemory = Long.parseLong(containerMetricsSystemUsage.getSystemmemory().split(":")[1]);
        long systemRamUsedPercent = (inputSystemMemory/totalSystemMemory)*100;

        int alertLevel = ramComparatorContainer.compareValues(systemRamUsedPercent);

        System.out.println("AlertLevel = " + alertLevel);

        Alert alert = new Alert();
        alert.setApplicationId(applicationId);
        alert.setUserId(userId);
        alert.setTime(timeLive);
        alert.setMetricsName("ramUsageSystem");
        alert.setAlertLevel(alertLevel);
        kafkaTemplate.send(TOPIC, alert);

        long inputSystemCpu = Long.parseLong(containerMetricsSystemUsage.getSystemcpu().split(":")[0]);
        long totalSystemCpu = Long.parseLong(containerMetricsSystemUsage.getSystemcpu().split(":")[1]);
        long systemCpuUsedPercent = (inputSystemCpu/totalSystemCpu)*100;

        int alertLevel1 = cpuComparatorContainer.compareValues(systemCpuUsedPercent);

        System.out.println("AlertLevel = " + alertLevel);

        Alert alert1 = new Alert();
        alert1.setApplicationId(applicationId);
        alert1.setUserId(userId);
        alert1.setTime(timeLive);
        alert1.setMetricsName("cpuUsageSystem");
        alert1.setAlertLevel(alertLevel1);
        kafkaTemplate.send(TOPIC, alert1);


        return true;
    }
}