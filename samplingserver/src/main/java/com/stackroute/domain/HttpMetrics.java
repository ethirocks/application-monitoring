package com.stackroute.domain;

import lombok.Data;

@Data
public class HttpMetrics {
    private String requestMethod;
    private String requestUrl;
    private String sessionId;
    private Long sessionCreationTime;
    private Long sessionLastAccessedTime;
    private Long responseTime;
    private String serverName;
    private Integer serverPort;
    private Integer responseStatus;
    private Long requestId;
    private Long requestCount;
}
