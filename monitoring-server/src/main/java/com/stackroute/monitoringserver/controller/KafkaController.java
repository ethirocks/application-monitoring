package com.stackroute.monitoringserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("kafka")
public class KafkaController {
//    @Autowired
//    private KafkaTemplate<String, T> kafkaTemplate;
//
//    private static final String TOPIC = "Hello-Kafka-Json";
//
//    @GetMapping
//    public String post(@RequestBody String a) {   //@RequestBody T T
//
//        kafkaTemplate.send(TOPIC, new T("cpu_load_short","abc","ms"));
//
//        return "Published successfully";
//    }

@Autowired
private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "Hello-Kafka-Json";

    @PostMapping()
    public <T> String post(@RequestBody T t) {

        kafkaTemplate.send(TOPIC, t);

        return "Published successfully";
    }
}

//new T("cpu_load_short","abc","ms")