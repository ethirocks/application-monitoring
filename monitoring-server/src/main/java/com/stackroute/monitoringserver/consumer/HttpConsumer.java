//package com.stackroute.monitoringserver.consumer;
//
//import com.stackroute.monitoringserver.domain.HttpMetrics;
//import com.stackroute.monitoringserver.service.MetricsService;
//import org.influxdb.dto.Point;
//import org.influxdb.dto.QueryResult;
//import org.json.JSONException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class HttpConsumer implements IConsumer{
//
//
//    private MetricsService metricsService;
//    @Autowired
//    public HttpConsumer(MetricsService metricsService) {
//        this.metricsService = metricsService;
//    }
//
//    @Override
//    public void consumeMetrics(String url) throws IOException, JSONException, URISyntaxException {
//        RestTemplate restTemplate = new RestTemplate();
//        //URI url = new URI("http://172.23.239.108:8081/httpMetrics");
//        ResponseEntity<List<HttpMetrics>> response = restTemplate.exchange(
//                url+"/httpMetrics/",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<HttpMetrics>>(){});
//
//        QueryResult queryResult=metricsService.queryMetrics("show series");
//        if(!queryResult.getResults().equals(null) && !queryResult.getResults().get(0).getSeries().equals(null) && !queryResult.getResults().get(0).equals(null) && !queryResult.equals(null)){
//            for (int i = 0; i < queryResult.getResults().get(0).getSeries().size(); i++) {
//                if(queryResult.getResults().get(0).getSeries().get(i).getName().equals("http_requests")){
//                    metricsService.queryMetrics("drop measurement http_requests");
//                }
//            }
//        }
//
//
//        List<HttpMetrics> httpMetricsList=response.getBody();
//        for (HttpMetrics httpMetrics :
//                httpMetricsList) {
//        org.influxdb.dto.Point point = Point.measurement("http_requests")
//                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
//                .addField("RequestCount",  httpMetrics.getRequestCount())
//                .addField("RequestId",  httpMetrics.getRequestId())
//                .addField("RequestMethod",  httpMetrics.getRequestMethod())
//                .addField("RequestUrl",httpMetrics.getRequestUrl())
//                .addField("ResponseStatus",httpMetrics.getResponseStatus())
//                .addField("ResponseTime",httpMetrics.getResponseTime())
//                .addField("ServerName",httpMetrics.getServerName())
//                .addField("ServerPort",httpMetrics.getServerPort())
//                .addField("SessionCreationTime",httpMetrics.getSessionCreationTime())
//                .addField("SessionId",httpMetrics.getSessionId())
//                .addField("SessionLastAccessedTime",httpMetrics.getSessionLastAccessedTime())
//                .build();
//        metricsService.insertMetrics(point);
//        }
//    }
//}
