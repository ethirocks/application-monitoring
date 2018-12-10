//package com.stackroute.alertmanager.consumer;
//
//import com.google.gson.Gson;
//import com.stackroute.domain.HealthMetrics;
//import com.stackroute.domain.KafkaDomain;
//import com.stackroute.domain.ThreadMetrics;
//import com.stackroute.alertmanager.service.MetricsService;
//import org.influxdb.dto.Point;
//import org.json.JSONException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//import java.util.logging.Logger;
//
//@Service
//public class ThreadConsumer  {
//
//    private MetricsService metricsService;
//
//    public ThreadConsumer(MetricsService metricsService) {
//        this.metricsService = metricsService;
//    }
//
//    int userId;
//    int applicationId;
//    KafkaDomain inputSample;
//    long timeSample;
//    @KafkaListener(topics = "thread", containerFactory = "kafkaListenerContainerFactory")
//    public boolean consumeMetrics(@Payload KafkaDomain message){
//        this.inputSample=message;
//        this.userId= inputSample.getUserId();
//        this.applicationId=inputSample.getApplicationId();
//        this.timeSample=inputSample.getTime();
//
//        Gson gson=new Gson();
//        String json=gson.toJson(inputSample.getMetrics());
//        ThreadMetrics threadMetrics= gson.fromJson(json, ThreadMetrics.class);
//        try{
//            long time=timeSample;
//            if (threadMetrics!=null && threadMetrics.getType_Of_threads()!=null){
//                Iterator it = threadMetrics.getType_Of_threads().entrySet().iterator();
//                while (it.hasNext()) {
//                    Map.Entry threadEntry = (Map.Entry)it.next();
//                    Thread thread= (Thread) threadEntry.getKey();
//                    Point point = Point.measurement("threadSample")
//                            .time(timeSample, TimeUnit.MILLISECONDS)
//                            .tag("timeStamp",String.valueOf(time))
//                            .tag("total_Threads",String.valueOf(threadMetrics.getTotal_Threads()))
//                            .addField("thread_name",thread.getName())
//                            .addField("thread_priority",thread.getPriority())
//                            .addField("thread_group",thread.getThreadGroup().getName())
//                            .addField("thread_status",threadEntry.getValue().toString())
//                            .build();
//                    metricsService.insertMetrics(point,"samplingMetrics");
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
