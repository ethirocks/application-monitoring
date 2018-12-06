package com.stackroute.alertmanager.service;

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
        if (!influxDB.describeDatabases().contains("samplingMetrics")) {
            influxDB.query(new Query("create database samplingMetrics","_internal"),TimeUnit.MILLISECONDS);
            URL url = new URL("http://localhost:8086/query?q=CREATE+DATABASE+samplingMetrics");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        }
        if (!influxDB.describeDatabases().contains("threshold")) {
            influxDB.query(new Query("create database threshold","_internal"),TimeUnit.MILLISECONDS);
            URL url = new URL("http://localhost:8086/query?q=CREATE+DATABASE+samplingMetrics");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        }
        databases.add("samplingMetrics");
        databases.add("threshold");
        databases.add("_internal");
    }

    public void insertMetrics(Point point,String database){
        BatchPoints batchPoints = BatchPoints
                .database(database)
                .build();
        batchPoints.point(point);
        influxDB.write(batchPoints);
    }

    public QueryResult getMean(String columnName, String metricsName){
        System.out.println("SELECT MEAN("+columnName+") FROM "+metricsName);
        Query query = new Query("SELECT MEAN("+columnName+") FROM "+metricsName,databases.get(0));
        QueryResult queryResult= influxDB.query(query,TimeUnit.MILLISECONDS);
        return queryResult;
    }

    
    
    public QueryResult searchMetricsJmeter(String metricsName,Integer userID,Integer applicationID,String requestUrlString,int serverPort,String requestMethod, String databaseName) {//throws JSONException {
        Query query=new Query("select * from "+metricsName+" where userID='"+userID+"' and applicationID='"+applicationID+"' and requestUrlString='"+requestUrlString+"' and serverPort='"+serverPort+"' and requestMethod='"+requestMethod+"'",databaseName);
        QueryResult queryResult = influxDB.query(query,TimeUnit.MILLISECONDS);

        return queryResult;
    }

    public QueryResult searchMetrics(String metricsName,int userID,int applicationID,String databaseName) {//throws JSONException {
        System.out.println("select * from "+metricsName+" where userID='"+userID+"' and applicationID='"+applicationID+"'");
        Query query=new Query("select * from "+metricsName+" where userID='"+userID+"' and applicationID='"+applicationID+"'",databaseName);
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
