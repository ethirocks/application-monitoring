package com.stackroute.alertmanager.consumer;

import com.google.gson.Gson;
import com.stackroute.alertmanager.comparator.CpuComparatorContainer;
import com.stackroute.alertmanager.comparator.RamComparatorContainer;
import com.stackroute.domain.Alert;
import com.stackroute.domain.ContainerMetrics;
import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.HealthMetrics;
import com.stackroute.domain.KafkaDomain;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ContainerConsumer {
    private MetricsService metricsService;
    private RamComparatorContainer ramComparatorContainer;
    private CpuComparatorContainer cpuComparatorContainer;
    private static KafkaTemplate<String, Alert> kafkaTemplate;
    private static String TOPIC;
    @Autowired
    public ContainerConsumer(MetricsService metricsService, CpuComparatorContainer cpuComparatorContainer, RamComparatorContainer ramComparatorContainer, KafkaTemplate<String, Alert> kafkaTemplate) {
        this.metricsService = metricsService;
        this.cpuComparatorContainer = cpuComparatorContainer;
        this.ramComparatorContainer = ramComparatorContainer;
        if (ContainerConsumer.kafkaTemplate == null) {
            ContainerConsumer.kafkaTemplate = kafkaTemplate;
        }
        ContainerConsumer.TOPIC="alert";
    }
    int userId;
    int applicationId;
    KafkaDomain inputSample;
    KafkaDomain inputLive;
    long timeSample;
    long timeLive;

    @KafkaListener(topics = "containerMetrics", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics(@Payload KafkaDomain message){
        this.inputSample=message;
        this.userId= inputSample.getUserId();
        this.applicationId=inputSample.getApplicationId();
        this.timeSample=inputSample.getTime();
        List<ContainerMetrics> containerMetricsList= (List<ContainerMetrics>) inputSample.getMetrics();

        try {
            for (ContainerMetrics containerMetrics :
                    containerMetricsList) {
                System.out.println(containerMetrics.toString());
                Point point = Point.measurement("Container")
                        .time(timeSample, TimeUnit.MILLISECONDS)
                        .tag("containerId", containerMetrics.getContainer())
                        .addField("container", containerMetrics.getContainer())
                        .addField("containerName", containerMetrics.getName())
                        .addField("threads", containerMetrics.getThreads())
                        .addField("networkIO", containerMetrics.getNetworkIO())
                        .addField("blockIO", containerMetrics.getBlockIO())
                        .addField("memory_raw", containerMetrics.getMemory().getRaw())
                        .addField("memory_percent", containerMetrics.getMemory().getPercent())
                        .addField("cpu", containerMetrics.getCpu())
                        .build();
                metricsService.insertMetrics(point, "samplingMetrics");

            }

        }  catch (NullPointerException n){        }

        return true;
    }

    @KafkaListener(topics = "containerMetricsLive1", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetricsLive ( KafkaDomain message) {
        this.inputLive = message;
        this.userId = inputLive.getUserId();
        this.applicationId = inputLive.getApplicationId();
        this.timeLive = inputLive.getTime();

        List<ContainerMetrics> containerMetricsList= (List<ContainerMetrics>) inputLive.getMetrics();

        try {
            for (int i = 0; i < containerMetricsList.size(); i++) {

                Gson gson=new Gson();
                String json=gson.toJson(containerMetricsList.get(i));
                ContainerMetrics containerMetrics= gson.fromJson(json,ContainerMetrics.class);
                double ramPercentLive = Double.parseDouble(containerMetrics.getMemory().getPercent());
                int alertLevel = ramComparatorContainer.compareValues(ramPercentLive);

                System.out.println("AlertLevel = " + alertLevel);

                if (alertLevel>0){
                    Alert alert = new Alert();
                    alert.setApplicationId(applicationId);
                    alert.setUserId(userId);
                    alert.setTime(timeLive);
                    alert.setMetricsName("ramUsageContainer");
                    alert.setAlertLevel(alertLevel);
                    kafkaTemplate.send(TOPIC, alert);
                }

                double cpuPercentLive = Double.parseDouble(containerMetrics.getCpu());
                int alertLevel1 = cpuComparatorContainer.compareValues(cpuPercentLive);

                System.out.println("AlertLevel = " + alertLevel);

                if (alertLevel1>0){
                    Alert alert1 = new Alert();
                    alert1.setApplicationId(applicationId);
                    alert1.setUserId(userId);
                    alert1.setTime(timeLive);
                    alert1.setMetricsName("cpuUsageContainer");
                    alert1.setAlertLevel(alertLevel1);
                    kafkaTemplate.send(TOPIC, alert1);
                }
            }
        }  catch (NullPointerException n){        }
        return true;

    }
}
