package com.stackroute.samplingserver.job;

import com.stackroute.domain.GenericMetrics;
import com.stackroute.domain.HealthMetrics;
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

@Service
public class HealthJob implements Job {

    private static KafkaTemplate<String, KafkaDomain> kafkaTemplate;
    private static String TOPIC;

    public HealthJob() {  }

    @Autowired
    public HealthJob(KafkaTemplate<String, KafkaDomain> kafkaTemplate) {
        if (HealthJob.kafkaTemplate == null) {
            HealthJob.kafkaTemplate = kafkaTemplate;
            HealthJob.TOPIC="health";
        }
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        System.out.println(dataMap.getKeys().length+"...........................................\n\n\n"+dataMap.get("url")+"/health"+
                "?userID="+dataMap.get("userID").toString()+"&applicationID="+dataMap.get("applicationID").toString());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        ResponseEntity<GenericMetrics<HealthMetrics>> response
                = restTemplate.exchange(dataMap.get("url")+"/health"+
                        "?userID="+dataMap.get("userID")+"&applicationID="+dataMap.get("applicationID"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<HealthMetrics>>(){});
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
