package com.stackroute.agent.domain;

import lombok.Data;

@Data
public class Metrics {
    Integer userID;
    Integer applicationID;
    Object metrics;
}
