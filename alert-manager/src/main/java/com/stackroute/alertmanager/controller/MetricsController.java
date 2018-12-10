package com.stackroute.alertmanager.controller;

import com.stackroute.alertmanager.service.MetricsService;
import com.stackroute.domain.QueryValue;
import org.influxdb.dto.QueryResult;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
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

//    @GetMapping("{metricsName}")
//    public ResponseEntity searchMetrics(@PathVariable("metricsName") String metricsName,
//                                        @RequestParam Integer userID,
//                                        @RequestParam Integer applicationID){
//        ResponseEntity responseEntity;
//        QueryResult queryResult;
//        try {
//            queryResult = metricsService.searchMetrics(metricsName,userID,applicationID);
//            responseEntity=new ResponseEntity<QueryResult>(queryResult, HttpStatus.OK);
//        } catch (JSONException e) {
//            responseEntity=new ResponseEntity<String>("Json exception", HttpStatus.CONFLICT);
//        }
//        return responseEntity;
//    }

    @GetMapping("query/{query}")
    public ResponseEntity queryMetrics(@PathVariable("query") String query) throws IOException, JSONException {
        ResponseEntity responseEntity;
        QueryResult queriedMetricsList= metricsService.queryMetrics(query);;
        System.out.println(queriedMetricsList);
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

    @GetMapping("sampleData/{query}")
    public ResponseEntity sampleData(@PathVariable("query") String query){
        ResponseEntity responseEntity;
        QueryResult queriedMetricsList;
        try {
            queriedMetricsList = metricsService.queryMetrics(query);
            //System.out.println(queriedMetricsList);
            //System.out.println(queriedMetricsList.getResults().get(0).getSeries().get(0).getValues());
            QueryValue timeMeanValue=new QueryValue();
            Object time=queriedMetricsList.getResults().get(0).getSeries().get(0).getValues().get(0).get(0);
            Object value=queriedMetricsList.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);
            if (queriedMetricsList.getResults().get(0).getSeries().get(0).getValues().get(0).get(1).equals("UP")){
                value=100;
            }
            else if (queriedMetricsList.getResults().get(0).getSeries().get(0).getValues().get(0).get(1).equals("UP")){
                value=0;
            }
            timeMeanValue.setTime( Double.parseDouble(time.toString()));
            timeMeanValue.setValue( Double.parseDouble(value.toString()));
            responseEntity=new ResponseEntity<QueryValue>(timeMeanValue, HttpStatus.OK);
            System.out.println(responseEntity);
        } catch (IOException e) {
            responseEntity=new ResponseEntity<String>("IO Exception", HttpStatus.CONFLICT);
        } catch (JSONException e) {
            responseEntity=new ResponseEntity<String>("Json exception", HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

}