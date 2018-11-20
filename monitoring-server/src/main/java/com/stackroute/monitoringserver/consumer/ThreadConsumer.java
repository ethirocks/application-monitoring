package com.stackroute.monitoringserver.consumer;

import com.stackroute.monitoringserver.domain.ThreadMetrics;
import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.json.JSONException;
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
public class ThreadConsumer implements IConsumer {

    private MetricsService metricsService;

    public ThreadConsumer(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public boolean consumeMetrics(String url) throws IOException, JSONException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ThreadMetrics> response
                = restTemplate.getForEntity(url+"/threads", ThreadMetrics.class);
        Logger.getLogger("ThreadDetails "+response.toString());
        ThreadMetrics threadMetrics= response.getBody();
        try{
            long time=System.currentTimeMillis();
            if (threadMetrics!=null && threadMetrics.getType_Of_threads()!=null){
                Iterator it = threadMetrics.getType_Of_threads().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry threadEntry = (Map.Entry)it.next();
                    Thread thread= (Thread) threadEntry.getKey();
                    Point point = Point.measurement("thread")
                            .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
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

