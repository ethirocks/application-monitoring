package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("ThresholdValues")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThresholdValue implements Serializable {
    @Id
    int applicationId;
    String metricName;
    int userId;
    String thresholdValue;
}
