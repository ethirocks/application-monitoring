package com.stackroute.monitoringserver.consumer.warConsumer;

import com.stackroute.monitoringserver.consumer.IConsumer;
import com.stackroute.monitoringserver.domain.GenericMetrics;
import com.stackroute.monitoringserver.domain.ThreadMetrics;
import com.stackroute.monitoringserver.domain.warMetrics.WarThreadMetrics;
import com.stackroute.monitoringserver.service.KafkaService;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.json.JSONException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class WarThreadConsumer implements IConsumer {

    private MetricsService metricsService;

    public WarThreadConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public boolean consumeMetrics(String url, Integer userID, Integer applicationID) throws IOException, JSONException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GenericMetrics<WarThreadMetrics>> response
                = restTemplate.exchange(url+"/threads?userID=0&applicationID=0",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GenericMetrics<WarThreadMetrics>>(){});
        KafkaService kafkaService=new KafkaService();
        kafkaService.produce(response.getBody().getMetrics(),"warThreadLive",userID,applicationID);
        Logger.getLogger("ThreadDetails "+response.toString());
        WarThreadMetrics threadMetrics= response.getBody().getMetrics();
        try{
            long time=System.currentTimeMillis();
            if (threadMetrics!=null && threadMetrics.getType_Of_threads()!=null){
                Iterator it = threadMetrics.getType_Of_threads().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry threadEntry = (Map.Entry)it.next();
                    Thread thread= (Thread) threadEntry.getKey();
                    Point point = Point.measurement("warThread")
                            .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                            .tag("userID",response.getBody().getUserID().toString())
                            .tag("applicationID",response.getBody().getApplicationID().toString())
                            .tag("timeStamp",String.valueOf(time))
                            .tag("total_Threads",String.valueOf(threadMetrics.getTotal_Threads()))
                            .addField("thread_name",thread.getName())
                            .addField("thread_priority",thread.getPriority())
                            .addField("thread_group",thread.getThreadGroup().getName())
                            .addField("thread_status",threadEntry.getValue().toString())
                            .build();
                    metricsService.insertMetrics(point);
                }
            }
        }
        catch (NullPointerException n){ }
        return true;
    }


}

