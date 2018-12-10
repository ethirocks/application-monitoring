package com.stackroute.domain;

import lombok.Data;

@Data
public class KafkaDomain {
    Integer userId;
    Integer applicationId;
    long time;
    Object metrics;
}
