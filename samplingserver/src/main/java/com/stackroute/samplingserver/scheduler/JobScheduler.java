package com.stackroute.samplingserver.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JobScheduler {

    public void trigger(List<Class> classNames, double interval, Map<String,String> urlMap,Integer userID,Integer applicationID) throws Exception
    {
    	JobDataMap jobDataMap=new JobDataMap();
    	jobDataMap.put("url",urlMap.get("url"));
    	jobDataMap.put("containerUrl",urlMap.get("containerUrl"));
    	jobDataMap.put("warUrl",urlMap.get("warUrl"));
    	jobDataMap.put("userID",userID);
    	jobDataMap.put("applicationID",applicationID);

		Scheduler scheduler=null;

		for (Class className:
			 classNames) {
			JobKey jobKey = JobKey.jobKey("job"+ className + LocalDateTime.now());
			JobDetail job = new JobDetail();
			job.setName("get metrics from " + className.getName()+"  "+LocalDateTime.now());
			job.setJobClass(className);
			job.setJobDataMap(jobDataMap);
			job.setGroup("group"+className.getName());

			scheduler = StdSchedulerFactory.getDefaultScheduler();

			CronTrigger trigger = new CronTrigger();
			trigger.setName("start " + job.getName());
			trigger.setCronExpression("0/30 * * * * ?");
			trigger.setStartTime(new Date(System.currentTimeMillis() + 10000));
			trigger.setEndTime(new Date((long) (System.currentTimeMillis() + 1000 * (interval *60 *60))));    // interval no of minutes
			scheduler.scheduleJob(job, trigger);
		}
		scheduler.start();
    }
}
