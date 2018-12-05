package com.stackroute.samplingserver.controller;

import com.stackroute.samplingserver.job.*;
import com.stackroute.samplingserver.job.warJob.*;
import com.stackroute.samplingserver.scheduler.JobScheduler;
import com.stackroute.samplingserver.service.JmeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/samplingserver")
@CrossOrigin
public class JobController {

    @Value("${url}")
    private String url;

    @Value("${containerUrl}")
    private String containerUrl;

    @Value("${warUrl}")
    private String warUrl;

    @Autowired
    private JobRegistry jobRegistry=new JobRegistry();

    private double interval=1;

    @Autowired
    private JmeterService service;

    @Value("${JmeterProperties}")
    String JmeterProperties;

    @Value("${JmeterHome}")
    String JmeterHome;

    @Value("${URL}")
    String URL;

    @GetMapping("check")
    public void check1(Integer userID,Integer applicationID) {
        service.check(URL,JmeterProperties,JmeterHome,userID,applicationID);
    }

    @PostMapping()
    public String startJob(@RequestParam Integer userID,
                           @RequestParam Integer applicationID,
                           @RequestBody Integer interval){

        Map<String,String> urlMap=new HashMap<>();
        List<Class> classes=new ArrayList<>();

        JobScheduler jobScheduler =new JobScheduler();

        classes.add(ContainerJob.class);
        classes.add(CPUCoresJob.class);
        classes.add(HardDiskJob.class);
        classes.add(ThreadJob.class);
        classes.add(CPUTempJob.class);
        classes.add(NetworkJob.class);
        classes.add(RAMJob.class);
        classes.add(CPUUsageJob.class);
        classes.add(HttpJob.class);
        classes.add(HealthJob.class);

        classes.add(WarCpuCoresJob.class);
        classes.add(WarCpuUsageJob.class);
        classes.add(WarCpuTempJob.class);
        classes.add(WarNetworkJob.class);
        classes.add(WarRAMJob.class);
        classes.add(WarThreadJob.class);

        urlMap.put("url",url);
        urlMap.put("containerUrl",containerUrl);
        urlMap.put("warUrl",warUrl);

        try {
            jobScheduler.trigger(classes,interval,urlMap,userID,applicationID);
        } catch (Exception e) {
            e.printStackTrace();
            return "not published re idiot..!! Go check from controller...";
        }

        return "Published successfully";
    }

    @PostMapping("interval")
    public String post(@RequestBody long interval){
        this.interval=interval;
        return "interval stored";
    }
}
