package com.stackroute.samplingserver.job.nodeJob;

import com.stackroute.domain.GenericMetrics;
import com.stackroute.domain.KafkaDomain;
import com.stackroute.domain.nodeDomain.NodeTemperatureMetrics;
import org.apache.http.impl.client.HttpClients;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class NodeTemperatureJob implements Job {
    private static KafkaTemplate<String, KafkaDomain> kafkaTemplate;
    private static String TOPIC;

    public NodeTemperatureJob() {  }

    @Autowired
    public NodeTemperatureJob(KafkaTemplate<String, KafkaDomain> kafkaTemplate) {
        if (NodeTemperatureJob.kafkaTemplate == null) {
            NodeTemperatureJob.kafkaTemplate = kafkaTemplate;
        }
        NodeTemperatureJob.TOPIC="nodeTemperature";
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        ResponseEntity<GenericMetrics<NodeTemperatureMetrics>> response
                = restTemplate.exchange(dataMap.get("url") + "/products/temperature"+
                        "?userID="+dataMap.get("userID")+"&applicationID="+dataMap.get("applicationID"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<NodeTemperatureMetrics>>(){});
//        NodeTemperatureMetrics response
//                = restTemplate.getForObject(url + "/products/memory?userID="+userID+"&applicationID="+applicationID, NodeTemperatureMetrics.class);
        System.out.println("nodeTemperature... "+response.getBody()+ " topic.... "+TOPIC);
        System.out.println(new Date(System.currentTimeMillis()));

        KafkaDomain kafkaDomain=new KafkaDomain();
        kafkaDomain.setApplicationId(response.getBody().getApplicationID());
        kafkaDomain.setUserId(response.getBody().getUserID());
        kafkaDomain.setTime(System.currentTimeMillis());
        kafkaDomain.setMetrics(response.getBody().getMetrics());
        kafkaTemplate.send(TOPIC,kafkaDomain);
    }
}
