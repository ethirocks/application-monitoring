package com.stackroute.apigateway.apigatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

@EnableZuulProxy
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableEurekaClient
public class ApigatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayServerApplication.class, args);
	}
	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat =
				new TomcatServletWebServerFactory() {
					@Override
					protected void postProcessContext(Context context) {
						SecurityConstraint securityConstraint = new SecurityConstraint();
						securityConstraint.setUserConstraint("CONFIDENTIAL");
						SecurityCollection collection = new SecurityCollection();
						collection.addPattern("/*");
						securityConstraint.addCollection(collection);
						context.addConstraint(securityConstraint);
					}
				};
		tomcat.addAdditionalTomcatConnectors(redirectConnector());
		return tomcat;
	}

	//Comment below code if any token error arrises
	private Connector redirectConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(9004);
		connector.setSecure(false);
		connector.setRedirectPort(9005);
		return connector;
	}

}
