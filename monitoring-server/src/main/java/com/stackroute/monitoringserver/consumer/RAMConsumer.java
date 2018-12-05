package com.stackroute.monitoringserver.consumer;

import com.stackroute.monitoringserver.domain.GenericMetrics;
import com.stackroute.monitoringserver.domain.HttpMetrics;
import com.stackroute.monitoringserver.domain.ThreadMetrics;
import com.stackroute.monitoringserver.service.KafkaService;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class RAMConsumer implements IConsumer {
    private MetricsService metricsService;
    @Autowired
    public RAMConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public boolean consumeMetrics(String url,Integer userID,Integer applicationID) throws IOException, JSONException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GenericMetrics<ArrayList<Long>>> response = restTemplate.exchange(
                url+"/memory?userID=0&applicationID=0",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<ArrayList<Long>>>(){});
        Logger.getLogger("memory " + response.toString());
        ArrayList<Long> memory = response.getBody().getMetrics();
        KafkaService kafkaService=new KafkaService();
        kafkaService.produce(memory,"ramLive",userID,applicationID);
        try {
            Point cpuCoresPoint = Point.measurement("memory")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag("userID",response.getBody().getUserID().toString())
                    .tag("applicationID",response.getBody().getApplicationID().toString())
                    .addField("free_physical_memory", memory.get(1))
                    .addField("total_physical_memory", memory.get(0))
                    .build();
            metricsService.insertMetrics(cpuCoresPoint);
        }
        catch (NullPointerException n){}
        return true;
    }
}
