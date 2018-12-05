package com.stackroute.monitoringserver.service;

import com.stackroute.monitoringserver.domain.KafkaDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private static KafkaTemplate<String, KafkaDomain> kafkaTemplate;

    public KafkaService() {
    }

    @Autowired
    public KafkaService(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate=kafkaTemplate;
    }

    public void produce(Object object, String topicName,Integer userID,Integer applicationID){
        KafkaDomain kafkaDomain=new KafkaDomain();
        kafkaDomain.setApplicationId(userID);
        kafkaDomain.setUserId(applicationID);
        kafkaDomain.setTime(System.currentTimeMillis());
        kafkaDomain.setMetrics(object);
        kafkaTemplate.send(topicName,kafkaDomain);
    }
}
