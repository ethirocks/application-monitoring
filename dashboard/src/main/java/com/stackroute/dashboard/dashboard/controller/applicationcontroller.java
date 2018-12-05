package com.stackroute.dashboard.dashboard.controller;

import com.stackroute.dashboard.dashboard.Service.ApplicationgeneratorService;
import com.stackroute.domain.AppDetails;
import com.stackroute.domain.ApplicationDomain;
//import com.stackroute.dashboard.dashboard.com.stackroute.domain.application;
import com.stackroute.domain.SendKafkaDomain;
import com.stackroute.domain.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Integer.parseInt;

@CrossOrigin
@RestController
public class applicationcontroller {

    @Autowired
    private ApplicationgeneratorService applicationgeneratorService;
    @Autowired
    private KafkaTemplate<String, Application> kafkaTemplate;

    @PostMapping("/addapplication")
    public ResponseEntity<String> check(@RequestBody ApplicationDomain applicationDomain) {
        ResponseEntity responseEntity;
        String message = applicationgeneratorService.addapplication(applicationDomain);
        System.out.println(message);
        if (message.equals("added successfully")) {
            System.out.println("inside");
            List<Application> applicationList = applicationgeneratorService.sendingkafka(applicationDomain.getUid());

            for (Application application : applicationList) {
//                System.out.println(application+"............");
                if (application.getApplicationName().equals(applicationDomain.getAppname())) {
//                    System.out.println(application+"............");
                    kafkaTemplate.send("application2", application);
                }
            }
            responseEntity = new ResponseEntity<String>(message, HttpStatus.OK);

        } else {
            responseEntity = new ResponseEntity<String>(message, HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("/appdetails")
    public ResponseEntity<?> check1(@RequestParam int i) {
        List<SendKafkaDomain> sendKafkaDomains = applicationgeneratorService.getallinfo();
        ResponseEntity responseEntity=null;
        int y = 0;
        for (SendKafkaDomain a : sendKafkaDomains) {
            if (a.getCid() == i) {
                responseEntity = new ResponseEntity<List<AppDetails>>(a.getAppDetails(), HttpStatus.OK);
                return responseEntity;
            } else {
                y++;
            }
        }
        if (y==0) {

        }
        else{
            responseEntity = new ResponseEntity<String>("user not found", HttpStatus.CONFLICT);
        }
return responseEntity;
    }
}