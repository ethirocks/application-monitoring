package com.stackroute.notificationServer;

import com.stackroute.notificationServer.exception.UserAlreadyExistsException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.mail.MessagingException;

@EnableEurekaClient
@SpringBootApplication
public class NotificationServerApplication {

	public static void main(String[] args) throws UserAlreadyExistsException, MessagingException {
		SpringApplication.run(NotificationServerApplication.class, args);
	}
}
