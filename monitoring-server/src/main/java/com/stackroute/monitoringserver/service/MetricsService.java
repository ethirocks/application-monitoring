package com.stackroute.monitoringserver.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.influxdb.impl.InfluxDBResultMapper;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.apache.kafka.common.requests.FetchMetadata.log;

@Service
public class MetricsService{

    private InfluxDB influxDB;
    private ArrayList<String> databases=new ArrayList<>();

    public MetricsService() throws IOException, JSONException, URISyntaxException {

        influxDB = InfluxDBFactory.connect("http://localhost:8086","tanu","password");
        Pong response = this.influxDB.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            log.error("Error pinging server.");
        }
        if (!influxDB.describeDatabases().contains("applicationMetrics")) {
            influxDB.query(new Query("create database applicationMetrics","_internal"),TimeUnit.MILLISECONDS);
            System.out.println("going in doesn;t contain....");
            URL url = new URL("http://localhost:8086/query?q=CREATE+DATABASE+applicationMetrics");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        }
        databases.add("applicationMetrics");
        databases.add("cAdvisor");
        databases.add("_internal");
        databases.add("metrics");
    }

    public void insertMetrics(Point point){
        BatchPoints batchPoints = BatchPoints
                .database(databases.get(0))
                .build();
        batchPoints.point(point);
        influxDB.write(batchPoints);
    }

    public QueryResult searchMetrics(String metricsName) throws JSONException {
        Query query=new Query("select * from "+metricsName,databases.get(0));
        QueryResult queryResult = influxDB.query(query,TimeUnit.MILLISECONDS);

        return queryResult;
    }

    public QueryResult queryMetrics(String givenQuery) throws IOException, JSONException {
        Query query=new Query(givenQuery,databases.get(0));
        QueryResult queryResult = influxDB.query(query,TimeUnit.MILLISECONDS);

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        return queryResult;
    }

    public QueryResult getAllMetrics() throws IOException, JSONException {
        Query query=new Query("show series",databases.get(0));
        QueryResult queryResult = influxDB.query(query,TimeUnit.MILLISECONDS);
        return queryResult;
    }

}
