package com.stackroute.moviecruiserapp;

import com.stackroute.moviecruiserapp.domain.Movie;
import com.stackroute.moviecruiserapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MovieCruiserAppApplication{

	public static void main(String[] args) {
		SpringApplication.run(MovieCruiserAppApplication.class, args);
	}

}
