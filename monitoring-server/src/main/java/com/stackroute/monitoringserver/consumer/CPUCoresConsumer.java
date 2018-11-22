package com.stackroute.monitoringserver.consumer;

import com.stackroute.monitoringserver.service.MetricsService;
import org.influxdb.dto.Point;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class CPUCoresConsumer implements IConsumer {

        private MetricsService metricsService;
    @Autowired
    public CPUCoresConsumer(MetricsService metricsService) {
            this.metricsService = metricsService;
        }

        @Override
        public boolean consumeMetrics(String url) throws IOException, JSONException, URISyntaxException {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Double> response
                    = restTemplate.getForEntity(url + "/cpucores", Double.class);
            Logger.getLogger("cpuCores " + response.toString());
            Double cpuCores = response.getBody();
            try {
                Point cpuCoresPoint = Point.measurement("cpuCores")
                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .addField("cpu_temp", cpuCores)
                        .build();
                metricsService.insertMetrics(cpuCoresPoint);
            }
            catch (NullPointerException n){}
            return true;
        }
}
