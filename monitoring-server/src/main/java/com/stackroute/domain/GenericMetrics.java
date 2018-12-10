package com.stackroute.domain;

import lombok.Data;

@Data
public class GenericMetrics<T> {
    Integer userID;
    Integer applicationID;
    T metrics;
}
