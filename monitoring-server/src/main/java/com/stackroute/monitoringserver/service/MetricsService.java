package com.stackroute.monitoringserver.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.stackroute.monitoringserver.domain.HealthMetrics;
import com.stackroute.monitoringserver.domain.MemoryPoint;
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

    public MetricsService() {
        influxDB = InfluxDBFactory.connect("http://localhost:8086","tanu","password");
        Pong response = this.influxDB.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            log.error("Error pinging server.");
        }
        //influxDB.createDatabase("cAdvisor");
        databases.add("cAdvisor");
        databases.add("metrics");
    }

    public void insertMetrics(Point point){
        BatchPoints batchPoints = BatchPoints
                .database("cAdvisor")
                .build();
        batchPoints.point(point);
        influxDB.write(batchPoints);
    }

//    
//    public String streamMetrics(String input){
//        return null;
//    }

//    
//    public boolean deleteMetrics(String metricsName) {
//        Query query=new Query("drop series from "+metricsName,databases.get(0));
//        QueryResult queryResult= influxDB.query(query,TimeUnit.MILLISECONDS);
//        return !queryResult.hasError();
//    }

    
    public QueryResult searchMetrics(String metricsName) throws JSONException {
        Query query=new Query("select * from "+metricsName,databases.get(0));
        QueryResult queryResult = influxDB.query(query,TimeUnit.MILLISECONDS);
        return queryResult;
    }

    
    public QueryResult queryMetrics(String givenQuery) throws IOException, JSONException {
        Query query=new Query(givenQuery,databases.get(0));
        QueryResult queryResult = influxDB.query(query,TimeUnit.MILLISECONDS);

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        String memoryPointList = resultMapper.toString();
        //        .toPOJO(queryResult, String.class);
        String result="";
//        for (MemoryPoint memoryPoint:
//                memoryPointList) {
//            result=result.concat(memoryPoint.toString());
//            result=result.concat("\n");
//        }
        return queryResult;
    }

    
    public String getAllMetrics() throws IOException, JSONException {
        Query query=new Query("show series",databases.get(0));
        QueryResult queryResult = influxDB.query(query,TimeUnit.MILLISECONDS);
        return queryResult.getResults().toString();
    }

//    public static String readFileToString(String filePathName) throws IOException {
//        File dirs = new File(filePathName);
//        String filePath = dirs.getCanonicalPath();
//
//        StringBuilder fileData = new StringBuilder(1000);//Constructs a string buffer with no characters in it and the specified initial capacity
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//
//            char[] buf = new char[1024];
//            int numRead = 0;
//            while ((numRead = reader.read(buf)) != -1) {
//                String readData = String.valueOf(buf, 0, numRead);
//                fileData.append(readData);
//                buf = new char[1024];
//            }
//        }
//        return fileData.toString();
//    }

}
