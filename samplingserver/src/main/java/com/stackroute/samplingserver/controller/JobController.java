package com.stackroute.samplingserver.controller;

import com.stackroute.domain.Application;
import com.stackroute.samplingserver.job.*;
import com.stackroute.samplingserver.job.nodeJob.NodeCPUJob;
import com.stackroute.samplingserver.job.nodeJob.NodeMemoryJob;
import com.stackroute.samplingserver.job.nodeJob.NodeTemperatureJob;
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
@CrossOrigin
@RequestMapping("api/v1/samplingserver")
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

    @PostMapping("check")
    public void check1(@RequestBody Application application) {
        service.check(URL,JmeterProperties,JmeterHome,application.getUserId(),application.getId());
    }

    @PostMapping()
    public String startJob(@RequestParam(value = "interval", defaultValue = "1") Integer interval,
                           @RequestBody Application application){

        Map<String,String> urlMap=new HashMap<>();
        List<Class> classes=new ArrayList<>();
    //    interval=Integer.parseInt(interval.toString());
//        if (!application.getDependency().equals("docker")){
//            service.check(application.getAddress(),JmeterProperties,JmeterHome,application.getUserId(),application.getId());
//        }
        JobScheduler jobScheduler =new JobScheduler();
        if (application.getDependency().equals("jar")) {
            classes.add(CPUCoresJob.class);
            classes.add(HardDiskJob.class);
            classes.add(ThreadJob.class);
            classes.add(CPUTempJob.class);
            classes.add(NetworkJob.class);
            classes.add(RAMJob.class);
            classes.add(CPUUsageJob.class);
            classes.add(HttpJob.class);
            classes.add(HealthJob.class);
        }
        else if (application.getDependency().equals("docker")){
            classes.add(ContainerJob.class);
        }
        else if(application.getDependency().equals("war")){
            classes.add(WarCpuCoresJob.class);
            classes.add(WarCpuUsageJob.class);
            classes.add(WarCpuTempJob.class);
            classes.add(WarNetworkJob.class);
            classes.add(WarRAMJob.class);
            classes.add(WarThreadJob.class);
        }
        else if (application.getDependency().equals("nodeJs")){
            classes.add(NodeCPUJob.class);
            classes.add(NodeMemoryJob.class);
            classes.add(NodeTemperatureJob.class);
        }

        try {
            jobScheduler.trigger(classes,interval,application.getAddress(),application.getUserId(),application.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return "not published...";
        }

        return "over";
    }
}