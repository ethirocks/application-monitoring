package com.stackroute.samplingserver.job;

import com.stackroute.domain.ContainerMetrics;
import com.stackroute.domain.ContainerMetricsSystemUsage;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class ContainerJob implements Job {

    private static KafkaTemplate<String, KafkaDomain> kafkaTemplate;
    private static String metricsTOPIC;
    private static String temperatureTOPIC;
    private static String systemUsageTOPIC;

    public ContainerJob() {  }

    @Autowired
    public ContainerJob(KafkaTemplate<String, KafkaDomain> kafkaTemplate) {
        if (ContainerJob.kafkaTemplate == null) {
            ContainerJob.kafkaTemplate = kafkaTemplate;
        }
        ContainerJob.metricsTOPIC="containerMetrics";
        ContainerJob.systemUsageTOPIC="containerSystemUsage";
        ContainerJob.temperatureTOPIC="containerTemperature";
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        ResponseEntity<GenericMetrics<List<ContainerMetrics>>> response = restTemplate.exchange(
                dataMap.get("url")+"/container/metrics"+
                        "?userID="+dataMap.get("userID")+"&applicationID="+dataMap.get("applicationID"),
                        HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<List<ContainerMetrics>>>(){});
//            Map<Long,KafkaDomain> responseMap=new HashMap<>();
//            responseMap.put(System.currentTimeMillis(),response.getBody());
//            kafkaTemplate.send(metricsTOPIC, responseMap);
        System.out.println("...."+response.getBody().toString());
        KafkaDomain kafkaDomain=new KafkaDomain();
        kafkaDomain.setApplicationId(response.getBody().getApplicationID());
        kafkaDomain.setUserId(response.getBody().getUserID());
        kafkaDomain.setTime(System.currentTimeMillis());
        kafkaDomain.setMetrics(response.getBody().getMetrics());
        kafkaTemplate.send(metricsTOPIC,kafkaDomain);

        ResponseEntity<GenericMetrics<String>> response1 = restTemplate.exchange(dataMap.get("url") + "/container/temperature"+
                        "?userID="+dataMap.get("userID")+"&applicationID="+dataMap.get("applicationID"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<String>>(){});
//            Map<Long,KafkaDomain> responseMap1=new HashMap<>();
//            responseMap.put(System.currentTimeMillis(),response1.getBody());
//            kafkaTemplate.send(temperatureTOPIC, responseMap1);

        KafkaDomain kafkaDomain2=new KafkaDomain();
        kafkaDomain2.setApplicationId(response1.getBody().getApplicationID());
        kafkaDomain2.setUserId(response1.getBody().getUserID());
        kafkaDomain2.setTime(System.currentTimeMillis());
        kafkaDomain2.setMetrics(response1.getBody().getMetrics());
        kafkaTemplate.send(temperatureTOPIC,kafkaDomain2);

        ResponseEntity<GenericMetrics<ContainerMetricsSystemUsage>> response2 =
                restTemplate.exchange(dataMap.get("url") + "/container/systemusage"+
                                "?userID="+dataMap.get("userID")+"&applicationID="+dataMap.get("applicationID"),
                        HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<ContainerMetricsSystemUsage>>(){});
//            Map<Long,KafkaDomain> responseMap2=new HashMap<>();
//            responseMap.put(System.currentTimeMillis(),response2.getBody());
//            kafkaTemplate.send(systemUsageTOPIC, responseMap2);

        KafkaDomain kafkaDomain3=new KafkaDomain();
        kafkaDomain3.setApplicationId(response2.getBody().getApplicationID());
        kafkaDomain3.setUserId(response2.getBody().getUserID());
        kafkaDomain3.setTime(System.currentTimeMillis());
        kafkaDomain3.setMetrics(response2.getBody().getMetrics());
        kafkaTemplate.send(systemUsageTOPIC,kafkaDomain3);


    }
}
