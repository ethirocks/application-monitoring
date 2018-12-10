package com.stackroute.alertmanager.consumer.nodeConsumer;

import com.google.gson.Gson;
import com.stackroute.alertmanager.comparator.UsageComparator;
import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.Alert;
import com.stackroute.domain.HealthMetrics;
import com.stackroute.domain.KafkaDomain;
import com.stackroute.domain.nodeDomain.NodeCPUMetrics;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class NodeCPUConsumer{
    private static KafkaTemplate<String, Alert> kafkaTemplate;
    private static String TOPIC;
    private MetricsService metricsService;
    private UsageComparator usageComparator;

    @Autowired
    public NodeCPUConsumer(MetricsService metricsService, UsageComparator usageComparator, KafkaTemplate<String, Alert> kafkaTemplate) {
        this.metricsService = metricsService;
        this.usageComparator = usageComparator;
        if (NodeCPUConsumer.kafkaTemplate == null) {
            NodeCPUConsumer.kafkaTemplate = kafkaTemplate;
        }
        NodeCPUConsumer.TOPIC="alert";

    }
    int userId;
    int applicationId;
    KafkaDomain inputSample;
    KafkaDomain inputLive;
    long timeSample;
    long timeLive;

    @KafkaListener(topics = "nodeCPU", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics(@Payload KafkaDomain message){
        this.inputSample=message;
        this.userId= inputSample.getUserId();
        this.applicationId=inputSample.getApplicationId();
        this.timeSample=inputSample.getTime();

        System.out.println("input   "+inputSample);

        Gson gson=new Gson();
        String json=gson.toJson(inputSample.getMetrics());
        NodeCPUMetrics nodeCPU=gson.fromJson(json,NodeCPUMetrics.class);
        System.out.println("teppppii      "+nodeCPU);
//        NodeCPUMetrics nodeCPU = (NodeCPUMetrics) inputSample.getMetrics();


        try{
            Point cpuCoresPoint = Point.measurement("nodeCPU")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag("userID",String.valueOf(userId))
                    .tag("applicationID",String.valueOf(applicationId))
                    .addField("cpu", Double.parseDouble(nodeCPU.getCpu()))
                    .addField("systemCpu", Double.parseDouble(nodeCPU.getSystemcpu()))
                    .build();
            metricsService.insertMetrics(cpuCoresPoint,"samplingMetrics");
            System.out.println("......................."+metricsService.getMean("cpu","nodeCPU"));
            Double meanNodeCPU =(Double)(metricsService.getMean("cpu","nodeCPU").getResults().get(0).getSeries().get(0).getValues().get(0).get(1));
            try{
                    System.out.println("Inside try");
                Point cpuTempThreshold = Point.measurement("nodeCPUthreshold")
                        .time(0,TimeUnit.MILLISECONDS)
                        .tag("userID",String.valueOf(userId))
                        .tag("applicationID",String.valueOf(applicationId))
                        .addField("node_cpu_threshold",meanNodeCPU)
                        .build();
                System.out.println(cpuTempThreshold);
                metricsService.insertMetrics(cpuTempThreshold,"threshold");

            }  catch (NullPointerException n){        }

        }
        catch (NullPointerException n){ }
        Double usageThreshold = (Double)(metricsService.searchMetrics("nodeCPUthreshold",userId,applicationId,"threshold").getResults().get(0).getSeries().get(0).getValues().get(0).get(2));
        System.out.println("usagethresh"+usageThreshold);
        return true;
    }
    @KafkaListener(topics = "nodeCPULive1", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetricsLive ( KafkaDomain message) {
        this.inputLive = message;
        this.userId= inputLive.getUserId();
        this.applicationId=inputLive.getApplicationId();
        this.timeLive=inputLive.getTime();

        Gson gson=new Gson();
        String json=gson.toJson(inputLive.getMetrics());
        NodeCPUMetrics nodeCPULive=gson.fromJson(json,NodeCPUMetrics.class);

//        NodeCPUMetrics nodeCPULive = (NodeCPUMetrics) inputLive.getMetrics();
        System.out.println("NodeCPUlive = "+nodeCPULive);

        Double cpuThreshold = (Double)(metricsService.searchMetrics("nodeCPUthreshold",applicationId,userId,"threshold").getResults().get(0).getSeries().get(0).getValues().get(0).get(2));
        System.out.println("usagethresh"+cpuThreshold);
        try {
            int alertLevel = usageComparator.compareValues(Double.parseDouble(nodeCPULive.getCpu()), cpuThreshold);

            System.out.println("AlertLevel = " + alertLevel);

            if(alertLevel>0){
                Alert alert = new Alert();
                alert.setApplicationId(applicationId);
                alert.setUserId(userId);
                alert.setTime(timeLive);
                alert.setMetricsName("nodeCPU");
                alert.setAlertLevel(alertLevel);
                kafkaTemplate.send(TOPIC,alert);
            }

        }  catch (NullPointerException n){        }
        return true;
    }

}
