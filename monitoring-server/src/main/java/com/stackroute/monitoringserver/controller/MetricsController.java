package com.stackroute.monitoringserver.controller;

import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.QueryResult;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/metrics")
public class MetricsController {

    private MetricsService metricsService;
    @Autowired
    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

//    @PostMapping
//    public ResponseEntity insertMetrics(@RequestBody String metrics){
//        ResponseEntity responseEntity;
//        try{
//            String insertedMetrics = metricsService.insertMetrics(metrics);
//            responseEntity=new ResponseEntity<String>(insertedMetrics, HttpStatus.CREATED);
//        }
//        catch (JSONException e) {
//            responseEntity=new ResponseEntity<String>("", HttpStatus.CONFLICT);
//        }
//        return responseEntity;
//    }

    @GetMapping
    public ResponseEntity getAllMetrics(){
        QueryResult metricsList = null;
        ResponseEntity responseEntity;
        try {
            metricsList = metricsService.getAllMetrics();
            responseEntity=new ResponseEntity<QueryResult>(metricsList, HttpStatus.OK);
        } catch (IOException e) {
            responseEntity=new ResponseEntity<String>("IO Exception", HttpStatus.CONFLICT);
        } catch (JSONException e) {
            responseEntity=new ResponseEntity<String>("Json exception", HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("{metricsName}")
    public ResponseEntity searchMetrics(@PathVariable("metricsName") String metricsName,
                                        @RequestParam Integer userID,
                                        @RequestParam Integer applicationID){
        ResponseEntity responseEntity;
        QueryResult queryResult;
        try {
            queryResult = metricsService.searchMetrics(metricsName,userID,applicationID);
            responseEntity=new ResponseEntity<QueryResult>(queryResult, HttpStatus.OK);
        } catch (JSONException e) {
            responseEntity=new ResponseEntity<String>("Json exception", HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("query/{query}")
    public ResponseEntity queryMetrics(@PathVariable("query") String query){
        ResponseEntity responseEntity;
        QueryResult queriedMetricsList;
        try {
            queriedMetricsList = metricsService.queryMetrics(query);
            responseEntity=new ResponseEntity<QueryResult>(queriedMetricsList, HttpStatus.OK);
        } catch (IOException e) {
            responseEntity=new ResponseEntity<String>("IO Exception", HttpStatus.CONFLICT);
        } catch (JSONException e) {
            responseEntity=new ResponseEntity<String>("Json exception", HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

}