package com.stackroute.notificationServer.configuration;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.stackroute.domain.Alert;
import com.stackroute.domain.Application;
import com.stackroute.domain.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KakfaConsumerConfiguration {

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.23.239.108:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);


        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, User> userConsumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.23.239.222:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "userConsumer");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new org.springframework.kafka.support.serializer.JsonDeserializer<>(User.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(userConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Application> applicationConsumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.23.239.222:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "applicationConsumer");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new org.springframework.kafka.support.serializer.JsonDeserializer<>(Application.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Application> applicationKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Application> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(applicationConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Alert> alertConsumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.23.239.108:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "alertConsumer");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new org.springframework.kafka.support.serializer.JsonDeserializer<>(Alert.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Alert> alertKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Alert> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(alertConsumerFactory());
        return factory;
    }

}
