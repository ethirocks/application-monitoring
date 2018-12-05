package com.stackroute.alertmanager.consumer;

import com.google.gson.Gson;
import com.stackroute.domain.HttpMetrics;
import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.KafkaDomain;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class HttpConsumer {

    private MetricsService metricsService;
    @Autowired
    public HttpConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    int userId;
    int applicationId;
    KafkaDomain inputSample;
    KafkaDomain inputLive;
    long timeSample;
    long timeLive;

    @KafkaListener(topics = "http", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics(@Payload KafkaDomain message){
        this.inputSample=message;
        this.userId= inputSample.getUserId();
        this.applicationId=inputSample.getApplicationId();
        this.timeSample=inputSample.getTime();

//        new ParameterizedTypeReference<List<HttpMetrics>>(){};
        List<HttpMetrics> httpMetricsList=(List<HttpMetrics>)(inputSample.getMetrics());
        Gson gson=new Gson();
        String json=gson.toJson(httpMetricsList.get(httpMetricsList.size()-1));
        HttpMetrics httpMetrics =gson.fromJson(json, HttpMetrics.class);

        try {

                Point point = Point.measurement("http_requests_agent_Sample")
                        .time(timeSample, TimeUnit.MILLISECONDS)
                        .addField("RequestCount",  httpMetrics.getRequestCount())
                        .addField("RequestId",  httpMetrics.getRequestId())
                        .addField("RequestMethod",  httpMetrics.getRequestMethod())
                        .addField("RequestUrl",httpMetrics.getRequestUrl())
                        .addField("ResponseStatus",httpMetrics.getResponseStatus())
                        .addField("ResponseTime",httpMetrics.getResponseTime())
                        .addField("ServerName",httpMetrics.getServerName())
                        .addField("ServerPort",httpMetrics.getServerPort())
                        .addField("SessionCreationTime",httpMetrics.getSessionCreationTime())
                        .addField("SessionId",httpMetrics.getSessionId())
                        .addField("SessionLastAccessedTime",httpMetrics.getSessionLastAccessedTime())
                        .build();
                metricsService.insertMetrics(point,"samplingMetrics");

        }
        catch (NullPointerException n){ }
        return true;
    }
}
