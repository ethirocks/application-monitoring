package com.stackroute.apigateway.apigatewayserver;

import com.stackroute.apigateway.apigatewayserver.filter.ErrorFilter;
import com.stackroute.apigateway.apigatewayserver.filter.PostFilter;
import com.stackroute.apigateway.apigatewayserver.filter.PreFilter;
import com.stackroute.apigateway.apigatewayserver.filter.RouteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableEurekaClient
public class ApigatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayServerApplication.class, args);
	}
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}
	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}
	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}
	@Bean
	public RouteFilter routeFilter() {
		return new RouteFilter();
	}

}

