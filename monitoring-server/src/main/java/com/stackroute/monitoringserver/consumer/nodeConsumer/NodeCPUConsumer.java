package com.stackroute.monitoringserver.consumer.nodeConsumer;

import com.stackroute.domain.GenericMetrics;
import com.stackroute.domain.nodeDomain.NodeCPUMetrics;
import com.stackroute.monitoringserver.consumer.IConsumer;
import com.stackroute.monitoringserver.service.KafkaService;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

@Service
public class NodeCPUConsumer implements IConsumer {
    private MetricsService metricsService;
    @Autowired
    public NodeCPUConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public boolean consumeMetrics(String url, Integer userID, Integer applicationID) throws IOException, JSONException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GenericMetrics<NodeCPUMetrics>> response
                = restTemplate.exchange(url + "/products/cpu?userID=" + userID + "&applicationID=" + applicationID,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<NodeCPUMetrics>>() {
                });
        System.out.println("cpuCores " + response.toString());
        NodeCPUMetrics nodeCPU = response.getBody().getMetrics();
        KafkaService kafkaService = new KafkaService();
        kafkaService.produce(response.getBody().getMetrics(), "nodeCPULive1", userID, applicationID);
        try {
            Point cpuCoresPoint = Point.measurement("nodeCPU")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag("userID", response.getBody().getUserID().toString())
                    .tag("applicationID", response.getBody().getApplicationID().toString())
                    .addField("cpu", Double.parseDouble(nodeCPU.getCpu()))
                    .addField("systemCpu", Double.parseDouble(nodeCPU.getSystemcpu()))
                    .build();
            System.out.println(cpuCoresPoint);
            metricsService.insertMetrics(cpuCoresPoint);
        } catch (NullPointerException n) {
        }
        return true;
    }
}
