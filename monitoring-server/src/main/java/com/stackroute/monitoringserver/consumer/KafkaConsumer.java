//package com.monitoringserver.monitoringserver.consumer;
//
//import MetricsService;
//import Metrics;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Logger;
//
//@Service
//public class KafkaConsumer {
//    private MetricsService metricsService;
//
//    public KafkaConsumer(MetricsService metricsService) {
//        this.metricsService=metricsService;
//    }
//
//    String input;
//
//    @KafkaListener(topics = "Hello-Kafka", groupId = "group_id")
//    public void consume(String message) {
//        this.input=message;
//        Logger.getLogger("Consumed message: " + message);
//        String response=metricsService.streamMetrics(message);
//        Logger.getLogger("response message: " + response);
//    }
//
//    @KafkaListener(topics = "Hello-Kafka-Json", groupId = "group_json",
//            containerFactory = "metricsKafkaListenerFactory")
//    public void consumeJson(Metrics metrics) {
//        Logger.getLogger("Consumed JSON Message: " + metrics.toString());
//    }
//}
