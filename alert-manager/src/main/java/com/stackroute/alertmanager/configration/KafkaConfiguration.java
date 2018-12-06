package com.stackroute.alertmanager.configration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.domain.Alert;
import com.stackroute.domain.KafkaDomain;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfiguration {
// Consumer Config
//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//        Map<String, Object> config = new HashMap<>();
//
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.23.239.108:9092");
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//
//
//        return new DefaultKafkaConsumerFactory<>(config);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }

    @Bean
    public  ConsumerFactory<String, KafkaDomain> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.23.239.108:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(KafkaDomain.class,new ObjectMapper()));
    }

    @Bean
    public  ConcurrentKafkaListenerContainerFactory<String, KafkaDomain> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaDomain> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
// Producer Config
    @Bean
    public ProducerFactory<String, Alert> producerFactory() {
    Map<String, Object> config = new HashMap<>();

    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.23.239.108:9092");
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Alert> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }





}
