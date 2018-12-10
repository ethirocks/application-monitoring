package com.stackroute.samplingserver.job.nodeJob;

import com.stackroute.domain.GenericMetrics;
import com.stackroute.domain.KafkaDomain;
import com.stackroute.domain.nodeDomain.NodeCPUMetrics;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class NodeCPUJob implements Job {
    private static KafkaTemplate<String, KafkaDomain> kafkaTemplate;
    private static String TOPIC;

    public NodeCPUJob() {  }

    @Autowired
    public NodeCPUJob(KafkaTemplate<String, KafkaDomain> kafkaTemplate) {
        if (NodeCPUJob.kafkaTemplate == null) {
            NodeCPUJob.kafkaTemplate = kafkaTemplate;
        }
        NodeCPUJob.TOPIC="nodeCPU";
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        ResponseEntity<GenericMetrics<NodeCPUMetrics>> response
                = restTemplate.exchange(dataMap.get("url") + "/products/cpu"+
                        "?userID="+dataMap.get("userID")+"&applicationID="+dataMap.get("applicationID"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<NodeCPUMetrics>>(){});
//        NodeCPUMetrics response
//                = restTemplate.getForObject(url + "/products/cpu?userID="+userID+"&applicationID="+applicationID, NodeCPUMetrics.class);
        System.out.println("nodeCPU... "+response.getBody()+ " topic.... "+TOPIC);
        System.out.println(new Date(System.currentTimeMillis()));

        KafkaDomain kafkaDomain=new KafkaDomain();
        kafkaDomain.setApplicationId(response.getBody().getApplicationID());
        kafkaDomain.setUserId(response.getBody().getUserID());
        kafkaDomain.setTime(System.currentTimeMillis());
        kafkaDomain.setMetrics(response.getBody().getMetrics());
        kafkaTemplate.send(TOPIC,kafkaDomain);
    }

}
