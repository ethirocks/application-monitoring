package com.stackroute.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class JmeterMetrics {
    private String requestUrl;
    private int serverPort;
    private String requestMethod;
    private long responseTime;
    private String endpoint;
}
