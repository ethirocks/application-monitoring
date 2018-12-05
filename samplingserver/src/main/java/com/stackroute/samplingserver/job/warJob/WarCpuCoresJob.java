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

@Service
public class WarCpuCoresJob implements Job {

    private static KafkaTemplate<String, KafkaDomain> kafkaTemplate;
    private static String TOPIC;

    public WarCpuCoresJob() {  }

    @Autowired
    public WarCpuCoresJob(KafkaTemplate<String, KafkaDomain> kafkaTemplate) {
        if (WarCpuCoresJob.kafkaTemplate == null) {
            WarCpuCoresJob.kafkaTemplate = kafkaTemplate;
        }
        WarCpuCoresJob.TOPIC="warCpuCores";
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        ResponseEntity<GenericMetrics<Double>> response
                = restTemplate.exchange(dataMap.getString("warUrl") + "/cpucores"+
                        "?userID="+dataMap.get("userID").toString()+"&applicationID="+dataMap.get("applicationID").toString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<Double>>(){});
//        Map<Long,KafkaDomain> responseMap=new HashMap<>();
//        responseMap.put(System.currentTimeMillis(),response.getBody());
//        System.out.println("..."+TOPIC+"..."+response.getBody());
//        kafkaTemplate.send(TOPIC, responseMap);

        KafkaDomain kafkaDomain=new KafkaDomain();
        kafkaDomain.setApplicationId(response.getBody().getApplicationID());
        kafkaDomain.setUserId(response.getBody().getUserID());
        kafkaDomain.setTime(System.currentTimeMillis());
        kafkaDomain.setMetrics(response.getBody().getMetrics());
        kafkaTemplate.send(TOPIC,kafkaDomain);

    }
}
