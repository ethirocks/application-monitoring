package com.stackroute.monitoringserver.domain;

import lombok.Data;
import org.influxdb.annotation.Measurement;

@Data
@Measurement(name = "http_requests")
public class HttpMetrics {
    private String requestMethod;
    private String requestUrl;
    private String sessionId;
    private long sessionCreationTime;
    private long sessionLastAccessedTime;
    private long responseTime;
    private String serverName;
    private Integer serverPort;
    private Integer responseStatus;
    private long requestId;
    private long requestCount;
}
