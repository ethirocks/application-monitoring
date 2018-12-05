package com.stackroute.monitoringserver.consumer;

import com.stackroute.monitoringserver.domain.GenericMetrics;
import com.stackroute.monitoringserver.domain.HealthMetrics;
import com.stackroute.monitoringserver.domain.ThreadMetrics;
import com.stackroute.monitoringserver.service.KafkaService;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
    public boolean consumeMetrics(String url, Integer userID, Integer applicationID) throws IOException, JSONException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GenericMetrics<HealthMetrics>> response
                = restTemplate.exchange(url+"/health?userID=0&applicationID=0",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<HealthMetrics>>(){});
        HealthMetrics healthMetrics= response.getBody().getMetrics();
        KafkaService kafkaService=new KafkaService();
        kafkaService.produce(response.getBody().getMetrics(),"hardDiskLive",userID,applicationID);
        long startTime = System.currentTimeMillis();
//        Point diskPoint = Point.measurement("disk_utilization")
//                .time(startTime, TimeUnit.MILLISECONDS)
//        //        .addField("timeStamp",String.valueOf(startTime))
//                .addField("details_diskFree",  healthMetrics.getDetails().getDiskFree())
//                .addField("details_diskTotal",  healthMetrics.getDetails().getDiskTotal())
//                .build();
//        metricsService.insertMetrics(diskPoint);

        try{
            Point diskPoint = Point.measurement("disk_utilization")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag("userID",response.getBody().getUserID().toString())
                    .tag("applicationID",response.getBody().getApplicationID().toString())
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
