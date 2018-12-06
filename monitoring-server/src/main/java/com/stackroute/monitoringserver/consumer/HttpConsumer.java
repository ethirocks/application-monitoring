package com.stackroute.monitoringserver.consumer;

import com.stackroute.domain.GenericMetrics;
import com.stackroute.domain.HttpMetrics;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class HttpConsumer implements IConsumer{


    private MetricsService metricsService;
    @Autowired
    public HttpConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public boolean consumeMetrics(String url, Integer userID, Integer applicationID) throws IOException, JSONException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GenericMetrics<List<HttpMetrics>>> response = restTemplate.exchange(
                url+"/httpMetrics?userID="+userID+"&applicationID="+applicationID,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<List<HttpMetrics>>>(){});
        KafkaService kafkaService=new KafkaService();
        kafkaService.produce(response.getBody().getMetrics(),"HttpLive1",userID,applicationID);

        try {
//            QueryResult queryResult=metricsService.queryMetrics("show series");
//            for (int i = 0; i < queryResult.getResults().get(0).getSeries().size(); i++) {
//                if(queryResult.getResults().get(0).getSeries().get(i).getName().equals("http_requests")){
//                    metricsService.queryMetrics("drop measurement http_requests");
//                }
//            }

            System.out.println("inside http "+response.getBody());

            List<HttpMetrics> httpMetricsList=response.getBody().getMetrics();
            for (HttpMetrics httpMetrics :
                    httpMetricsList) {
                org.influxdb.dto.Point point = Point.measurement("http_requests")
                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .tag("userID",response.getBody().getUserID().toString())
                        .tag("applicationID",response.getBody().getApplicationID().toString())
                        .addField("RequestCount",  httpMetrics.getRequestCount())
                        .addField("RequestId",  httpMetrics.getRequestId())
                        .addField("RequestMethod",  httpMetrics.getRequestMethod())
                        .addField("RequestUrl",httpMetrics.getRequestUrl())
                        .addField("ResponseStatus",httpMetrics.getResponseStatus())
                        .addField("ResponseTime",httpMetrics.getResponseTime())
                        .addField("ServerName",httpMetrics.getServerName())
                        .addField("ServerPort",httpMetrics.getServerPort())
                        .addField("SessionCreationTime",httpMetrics.getSessionCreationTime())
                        .addField("SessionId",httpMetrics.getSessionId())
                        .addField("SessionLastAccessedTime",httpMetrics.getSessionLastAccessedTime())
                        .build();
                metricsService.insertMetrics(point);
            }
        }
        catch (NullPointerException n){
            System.out.println("http null.... "+response.getBody());
        }
        return true;
    }
}
