//package com.stackroute.alertmanager.consumer.warConsumer;
//
//import com.google.gson.Gson;
//import com.stackroute.alertmanager.service.MetricsService;
//import com.stackroute.domain.HealthMetrics;
//import com.stackroute.domain.KafkaDomain;
//import com.stackroute.domain.ThreadMetrics;
//import com.stackroute.domain.warMetrics.WarThreadMetrics;
//import org.influxdb.dto.Point;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Service;
//
//import java.util.Iterator;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class WarThreadConsumer {
//
//    private MetricsService metricsService;
//
//    public WarThreadConsumer(MetricsService metricsService) {
//        this.metricsService = metricsService;
//    }
//
//    int userId;
//    int applicationId;
//    KafkaDomain inputSample;
//    long timeSample;
//    @KafkaListener(topics = "warThread", containerFactory = "kafkaListenerContainerFactory")
//    public boolean consumeMetrics(@Payload KafkaDomain message){
//        this.inputSample=message;
//        this.userId= inputSample.getUserId();
//        this.applicationId=inputSample.getApplicationId();
//        this.timeSample=inputSample.getTime();
//        Gson gson=new Gson();
//        String json=gson.toJson(inputSample.getMetrics());
//        WarThreadMetrics threadMetrics= gson.fromJson(json, WarThreadMetrics.class);
//        try{
//            long time=System.currentTimeMillis();
//            if (threadMetrics!=null && threadMetrics.getType_Of_threads()!=null){
//                Iterator it = threadMetrics.getType_Of_threads().entrySet().iterator();
//                while (it.hasNext()) {
//                    Map.Entry threadEntry = (Map.Entry)it.next();
//                    Thread thread= (Thread) threadEntry.getKey();
//                    Point point = Point.measurement("warThreadSample")
//                            .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
//                            .tag("timeStamp",String.valueOf(time))
//                            .tag("total_Threads",String.valueOf(threadMetrics.getTotal_Threads()))
//                            .addField("thread_name",thread.getName())
//                            .addField("thread_priority",thread.getPriority())
//                            .addField("thread_group",thread.getThreadGroup().getName())
//                            .addField("thread_status",threadEntry.getValue().toString())
//                            .build();
//                    metricsService.insertMetrics(point, "samplingMetrics");
//                }
//            }
//        }
//        catch (NullPointerException n){ }
//        return true;
//    }
//
//
//}
//
