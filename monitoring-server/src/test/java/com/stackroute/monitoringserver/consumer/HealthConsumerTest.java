//package com.stackroute.monitoringserver.consumer;
//
//import com.stackroute.monitoringserver.service.MetricsService;
//import com.stackroute.monitoringserver.service.PollingService;
//import org.json.JSONException;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import javax.validation.constraints.AssertTrue;
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.util.Timer;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//public class HealthConsumerTest {
//
//    MetricsService metricsService=new MetricsService();
//
//    @InjectMocks
//    HealthConsumer consumer;
//
//    @Mock
//    RestTemplate restTemplate;
//
//    private ResponseEntity<Object> response;
//
//    public HealthConsumerTest() throws JSONException, IOException, URISyntaxException {
//    }
//
//    @Before
//    public void setUp(){
//        //Initialising the mock object
//        MockitoAnnotations.initMocks(this);
//        response= ResponseEntity.noContent().build();
//        consumer=new HealthConsumer(metricsService);
//    }
//
//    @After
//    public void after(){
//        response=null;
//        //pollingService=null;
//    }
//
//    @Test
//    public void consumeMetricsSuccess(){
//        when(restTemplate.getForEntity(any(),any())).thenReturn(response);
//        Assert.assertTrue(consumer.consumeMetrics("http://localhost:8000"));
//    }
//
//}
