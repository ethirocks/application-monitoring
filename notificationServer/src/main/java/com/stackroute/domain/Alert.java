package com.stackroute.domain;

import lombok.Data;

@Data
public class Alert {

    Integer userId;
    Integer applicationId;
    long time;
    String metricsName;
    int alertLevel;

    public Alert() {
    }

    public Alert(Integer userId, Integer applicationId, long time, String metricsName, int alertLevel) {
        this.userId = userId;
        this.applicationId = applicationId;
        this.time = time;
        this.metricsName = metricsName;
        this.alertLevel = alertLevel;
    }
}
