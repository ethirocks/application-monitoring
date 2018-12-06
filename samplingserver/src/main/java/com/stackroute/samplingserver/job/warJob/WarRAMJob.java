package com.stackroute.samplingserver.job.warJob;

import com.stackroute.domain.GenericMetrics;
import com.stackroute.domain.KafkaDomain;
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

import java.util.ArrayList;

@Service
public class WarRAMJob implements Job {

    private static KafkaTemplate<String, KafkaDomain> kafkaTemplate;
    private static String TOPIC;

    public WarRAMJob() {  }

    @Autowired
    public WarRAMJob(KafkaTemplate<String, KafkaDomain> kafkaTemplate) {
        if (WarRAMJob.kafkaTemplate == null) {
            WarRAMJob.kafkaTemplate = kafkaTemplate;
        }
        WarRAMJob.TOPIC="warRam";
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        ResponseEntity<GenericMetrics<ArrayList<Long>>> response = restTemplate.exchange(
                dataMap.getString("url")+"/memory"+
                        "?userID="+dataMap.get("userID").toString()+"&applicationID="+dataMap.get("applicationID").toString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<ArrayList<Long>>>(){});
//        Map<Long,KafkaDomain> responseMap=new HashMap<>();
//        responseMap.put(System.currentTimeMillis(),response.getBody());
//        kafkaTemplate.send(TOPIC, responseMap);

        KafkaDomain kafkaDomain=new KafkaDomain();
        kafkaDomain.setApplicationId(response.getBody().getApplicationID());
        kafkaDomain.setUserId(response.getBody().getUserID());
        kafkaDomain.setTime(System.currentTimeMillis());
        kafkaDomain.setMetrics(response.getBody().getMetrics());
        kafkaTemplate.send(TOPIC,kafkaDomain);
    }
}
