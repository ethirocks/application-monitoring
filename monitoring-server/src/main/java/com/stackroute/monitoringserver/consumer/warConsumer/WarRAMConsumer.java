package com.stackroute.monitoringserver.consumer.warConsumer;

import com.stackroute.monitoringserver.consumer.IConsumer;
import com.stackroute.monitoringserver.domain.GenericMetrics;
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
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class WarRAMConsumer implements IConsumer {
    private MetricsService metricsService;
    @Autowired
    public WarRAMConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public boolean consumeMetrics(String url, Integer userID, Integer applicationID) throws IOException, JSONException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GenericMetrics<ArrayList<Long>>> response = restTemplate.exchange(
                url+"/memory?userID=0&applicationID=0",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<ArrayList<Long>>>(){});
        KafkaService kafkaService=new KafkaService();
        kafkaService.produce(response.getBody().getMetrics(),"warRAMLive",userID,applicationID);
        Logger.getLogger("memory " + response.toString());
        ArrayList<Long> memory = response.getBody().getMetrics();
        try {
            Point cpuCoresPoint = Point.measurement("warMemory")
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
