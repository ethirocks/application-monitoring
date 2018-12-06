package com.stackroute.alertmanager.consumer;

import com.google.gson.Gson;
import com.stackroute.alertmanager.comparator.HttpComparator;
import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.*;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class JmeterConsumer {
    private static KafkaTemplate<String, Alert> kafkaTemplate;
    private static String TOPIC;
    private MetricsService metricsService;
    private HttpComparator httpComparator;

    @Autowired
    public JmeterConsumer(MetricsService metricsService, HttpComparator httpComparator, KafkaTemplate<String, Alert> kafkaTemplate) {
        this.metricsService = metricsService;
        this.httpComparator = httpComparator;
        if (JmeterConsumer.kafkaTemplate == null) {
            JmeterConsumer.kafkaTemplate = kafkaTemplate;
        }
        JmeterConsumer.TOPIC="alert";

    }
    int userId;
    int applicationId;
    KafkaDomain inputSample;
    KafkaDomain inputLive;
    long timeSample;
    long timeLive;

    @KafkaListener(topics = "jmeterMetrics", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetrics (@Payload KafkaDomain message) {
        this.inputSample = message;
        this.userId = inputSample.getUserId();
        this.applicationId = inputSample.getApplicationId();
        this.timeSample = inputSample.getTime();

        Gson gson=new Gson();
        String json=gson.toJson(inputSample.getMetrics());
        JmeterMetrics jmeterMetrics = gson.fromJson(json, JmeterMetrics.class);

        try{
            Point jmeter = Point.measurement("http_requests_jmeter_Sample")
                    .time(timeSample, TimeUnit.MILLISECONDS)
                    .addField("requestUrl",  jmeterMetrics.getRequestUrl())
                    .addField("serverPort",  jmeterMetrics.getServerPort())
                    .addField("requestMethod",  jmeterMetrics.getRequestMethod())
                    .addField("responseTime",  jmeterMetrics.getResponseTime())
                    .addField("endpoint",  jmeterMetrics.getEndpoint())
                    .tag("userID",String.valueOf(userId))
                    .tag("applicationID",String.valueOf(applicationId))
                    .build();
            metricsService.insertMetrics(jmeter,"samplingMetrics");
            Double meanResponseTime = (Double)  (metricsService.getMean("responseTime","http_requests_jmeter_Sample").getResults().get(0).getSeries().get(0).getValues().get(0).get(1));
            System.out.println(meanResponseTime);
            try{
                Point jmeterThreshold = Point.measurement("jmeter_response_time_threshold")
                        .time(0,TimeUnit.MILLISECONDS)
                        .tag("userID",String.valueOf(userId))
                        .tag("applicationID",String.valueOf(applicationId))
                        .addField("response_Time_Threshold",meanResponseTime)
                        .addField("requestUrl",  (jmeterMetrics.getRequestUrl()+":"+jmeterMetrics.getServerPort()+jmeterMetrics.getEndpoint()))
                        .addField("serverPort",  jmeterMetrics.getServerPort())
                        .addField("requestMethod",  jmeterMetrics.getRequestMethod())
                        .build();
                metricsService.insertMetrics(jmeterThreshold,"threshold");

            }catch (NullPointerException n){ }
        }
        catch (NullPointerException n){ }
        return true;
    }

    @KafkaListener(topics = "http", containerFactory = "kafkaListenerContainerFactory")
    public boolean consumeMetricsLive (@Payload KafkaDomain message){
        this.inputLive = message;
        this.userId= inputLive.getUserId();
        this.applicationId=inputLive.getApplicationId();
        this.timeLive=inputLive.getTime();

        List<HttpMetrics> httpMetricsList = (List<HttpMetrics>)(inputLive.getMetrics());

        Gson gson=new Gson();
        String json=gson.toJson(httpMetricsList.get(httpMetricsList.size()-1));
        HttpMetrics httpMetrics =gson.fromJson(json, HttpMetrics.class);

        long jmeterThreshold = (Long)(metricsService.searchMetricsJmeter("jmeter_response_time_threshold",userId,applicationId,httpMetrics.getRequestUrl(),httpMetrics.getServerPort(),httpMetrics.getRequestMethod(),"threshold").getResults().get(0).getSeries().get(0).getValues().get(0).get(2));

        try {
            int alertLevel = httpComparator.compareValues(httpMetrics.getResponseTime(), jmeterThreshold);
            System.out.println("AlertLevel = " + alertLevel);

            if (alertLevel>0){
                Alert alert = new Alert();
                alert.setApplicationId(applicationId);
                alert.setUserId(userId);
                alert.setTime(timeLive);
                alert.setMetricsName("responseTime for url: "+httpMetrics.getRequestUrl()+" port: "+httpMetrics.getServerPort()+" requestMethod: "+httpMetrics.getRequestMethod());
                alert.setAlertLevel(alertLevel);
                kafkaTemplate.send(TOPIC,alert);
            }

        }  catch (NullPointerException n){        }

        return true;
    }

}
