package com.stackroute.monitoringserver.domain;

import lombok.Data;

@Data
public class GenericMetrics<T> {
    Integer userID;
    Integer applicationID;
    T metrics;
}
