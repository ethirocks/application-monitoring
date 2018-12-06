package com.stackroute.samplingserver.job.nodeJob;

import com.stackroute.domain.GenericMetrics;
import com.stackroute.domain.KafkaDomain;
import com.stackroute.domain.nodeDomain.NodeMemoryMetrics;
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
public class NodeMemoryJob implements Job {
    private static KafkaTemplate<String, KafkaDomain> kafkaTemplate;
    private static String TOPIC;

    public NodeMemoryJob() {  }

    @Autowired
    public NodeMemoryJob(KafkaTemplate<String, KafkaDomain> kafkaTemplate) {
        if (NodeMemoryJob.kafkaTemplate == null) {
            NodeMemoryJob.kafkaTemplate = kafkaTemplate;
        }
        NodeMemoryJob.TOPIC="nodeMemory";
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        ResponseEntity<GenericMetrics<NodeMemoryMetrics>> response
                = restTemplate.exchange(dataMap.get("url") + "/products/memory"+
                        "?userID="+dataMap.get("userID")+"&applicationID="+dataMap.get("applicationID"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<NodeMemoryMetrics>>(){});
//        NodeMemoryMetrics response
//                = restTemplate.getForObject(url + "/products/memory?userID="+userID+"&applicationID="+applicationID, NodeMemoryMetrics.class);
        System.out.println("nodeMemory... "+response.getBody()+ " topic.... "+TOPIC);
        System.out.println(new Date(System.currentTimeMillis()));

        KafkaDomain kafkaDomain=new KafkaDomain();
        kafkaDomain.setApplicationId(response.getBody().getApplicationID());
        kafkaDomain.setUserId(response.getBody().getUserID());
        kafkaDomain.setTime(System.currentTimeMillis());
        kafkaDomain.setMetrics(response.getBody().getMetrics());
        kafkaTemplate.send(TOPIC,kafkaDomain);
    }
}
