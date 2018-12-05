package com.stackroute.samplingserver.controller;

import com.stackroute.samplingserver.service.JmeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin
public class JmeterController {

    @Autowired
    private JmeterService service;

    @Value("$JmeterProperties")
    String JmeterProperties;

    @Value("$JmeterHome")
    String JmeterHome;

    @Value("$URL")
    String URL;

    @GetMapping("check")
    public void check1(Integer userID,Integer applicationID) {
        service.check(URL,JmeterProperties,JmeterHome,userID,applicationID);
    }
}
