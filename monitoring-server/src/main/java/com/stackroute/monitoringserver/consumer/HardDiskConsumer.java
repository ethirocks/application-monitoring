package com.stackroute.monitoringserver.consumer;

import com.stackroute.monitoringserver.domain.HealthMetrics;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class HardDiskConsumer implements IConsumer {
    private MetricsService metricsService;
    @Autowired
    public HardDiskConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public boolean consumeMetrics(String url) throws IOException, JSONException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HealthMetrics> response
                = restTemplate.getForEntity(url+"/health", HealthMetrics.class);
        HealthMetrics healthMetrics= response.getBody();
        long startTime = System.currentTimeMillis();
        Point diskPoint = Point.measurement("disk_utilization")
                .time(startTime, TimeUnit.MILLISECONDS)
                .addField("timeStamp",String.valueOf(startTime))
                .addField("details_diskFree",  healthMetrics.getDetails().getDiskFree())
                .addField("details_diskTotal",  healthMetrics.getDetails().getDiskTotal())
                .build();
        metricsService.insertMetrics(diskPoint);

        try{
             diskPoint = Point.measurement("disk_utilization")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("details_diskFree",  healthMetrics.getDetails().getDiskFree())
                    .addField("details_diskTotal",  healthMetrics.getDetails().getDiskTotal())
                    .build();
            metricsService.insertMetrics(diskPoint);
        }
        catch (NullPointerException n){
            System.out.println("disk null.... "+healthMetrics);
        }
        return true;
    }
}
