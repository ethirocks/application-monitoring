//package com.stackroute.monitoringserver.service;
//
//import com.stackroute.monitoringserver.consumer.HealthConsumer;
//import com.stackroute.monitoringserver.consumer.IConsumer;
//import org.influxdb.InfluxDB;
//import org.influxdb.dto.QueryResult;
//import org.json.JSONException;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@Service
//public class PollingServiceTest {
//
//    MetricsService metricsService=new MetricsService();
//
//    @Mock
//    Timer timer;
//
//    @Mock
//    HealthConsumer consumer=new HealthConsumer(metricsService);
//
//    @Mock
//    RestTemplate restTemplate;
//
//    @InjectMocks
//    PollingService pollingService;
//
//    private ResponseEntity<Object> response;
//
//    @Before
//    public void setUp(){
//        //Initialising the mock object
//        MockitoAnnotations.initMocks(this);
//        response= ResponseEntity.noContent().build();
//        pollingService=new PollingService();
//    }
//
//    @After
//    public void after(){
//        response=null;
//        //pollingService=null;
//    }
//
//
//    public PollingServiceTest() throws JSONException, IOException, URISyntaxException {
//    }
//
//    @Test
//    public void setTimerTaskSuccess() throws JSONException, IOException, URISyntaxException {
//        when(restTemplate.getForEntity(any(),any())).thenReturn(response);
//        when(restTemplate.exchange(any(),any(),any(),(ParameterizedTypeReference<Object>) any())).thenReturn(response);
//        when(consumer.consumeMetrics(any())).thenReturn(false);
//        Assert.assertTrue(pollingService.setTimerTask(consumer,""));
//    }
//
////    @Test
////    public void startSuccess() {
////        Assert.assertTrue(pollingService.start(any()));
////    }
////
////    @Test
////    public void stopSuccess() {
////        Assert.assertTrue(pollingService.stop());
////    }
//
////    @Test
////    public void setTimerTaskFailure() throws JSONException, IOException, URISyntaxException {
//////        when(restTemplate.getForEntity(any(),any())).thenReturn(response);
//////        when(restTemplate.exchange(any(),any(),any(),(ParameterizedTypeReference<Object>) any())).thenReturn(response);
////        when(consumer.consumeMetrics(any())).thenReturn(false);
////        Assert.assertFalse(pollingService.setTimerTask(consumer,""));
////    }
////
////    @Test
////    public void startFailure() {
////        Assert.assertTrue(pollingService.start(any()));
////    }
////
////    @Test
////    public void stopFailure() {
////        Assert.assertTrue(pollingService.stop());
////    }
//}
