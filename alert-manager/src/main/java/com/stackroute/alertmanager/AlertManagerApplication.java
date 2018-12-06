package com.stackroute.alertmanager;

import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class AlertManagerApplication {

	public static void main(String[] args)throws JSONException, IOException, URISyntaxException {
		SpringApplication.run(AlertManagerApplication.class, args);
	}
}
