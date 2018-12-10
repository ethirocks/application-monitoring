package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {
    Integer userId;
    Integer applicationId;
    long time;
    String metricsName;
    int alertLevel;
}
