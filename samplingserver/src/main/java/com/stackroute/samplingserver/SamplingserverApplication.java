package com.stackroute.samplingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication

public class SamplingserverApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SamplingserverApplication.class, args);
	}
}
