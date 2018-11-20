package com.stackroute.monitoringserver.service;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.influxdb.impl.InfluxDBResultMapper;
import org.json.JSONException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.apache.kafka.common.requests.FetchMetadata.log;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Service
public class MetricsServiceTest {

    private QueryResult queryResult;

    @Before
    public void setUp(){
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        queryResult=new QueryResult();

    }

    @After
    public void after(){
        queryResult=null;
    }

    @Mock
    private InfluxDB influxDB;

    private ArrayList<String> databases=new ArrayList<>();

    @InjectMocks
    MetricsService metricsService;

    @Test
    public void insertMetricsTestSuccess(){
        when(influxDB.query(any())).thenReturn(queryResult);
        Assert.assertNull(queryResult.getError());
    }

    @Test
    public void searchMetricsTestSuccess() throws IOException, JSONException {
        when(influxDB.query(any(),any())).thenReturn(queryResult);
        Assert.assertNull(metricsService.searchMetrics("health").getError());
        verify(influxDB,times(1)).query(any(),any());
    }

    @Test
    public void queryMetricsTestSuccess() throws IOException, JSONException {
        when(influxDB.query(any(),any())).thenReturn(queryResult);
        Assert.assertNull(metricsService.queryMetrics("show series").getError());
        verify(influxDB,times(1)).query(any(),any());
    }

    @Test
    public void getAllMetricsTestSuccess() throws IOException, JSONException {
        when(influxDB.query(any(),any())).thenReturn(queryResult);
        Assert.assertNull(metricsService.getAllMetrics().getError());
        verify(influxDB,times(1)).query(any(),any());
    }



    @Test
    public void insertMetricsTestFailure(){
        queryResult.setError("error");
        when(influxDB.query(any())).thenReturn(queryResult);
        Assert.assertNotNull(queryResult.getError());
    }

    @Test
    public void searchMetricsTestFailure() throws IOException, JSONException {
        queryResult.setError("error");
        when(influxDB.query(any(),any())).thenReturn(queryResult);
        Assert.assertNotNull(metricsService.searchMetrics("health").getError());
        verify(influxDB,times(1)).query(any(),any());
    }

    @Test
    public void queryMetricsTestFailure() throws IOException, JSONException {
        queryResult.setError("error");
        when(influxDB.query(any(),any())).thenReturn(queryResult);
        Assert.assertNotNull(metricsService.queryMetrics("show series").getError());
        verify(influxDB,times(1)).query(any(),any());
    }

    @Test
    public void getAllMetricsTestFailure() throws IOException, JSONException {
        queryResult.setError("error");
        when(influxDB.query(any(),any())).thenReturn(queryResult);
        Assert.assertNotNull(metricsService.getAllMetrics().getError());
        verify(influxDB,times(1)).query(any(),any());
    }
}
