package com.stackroute.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.stackroute,com.stackroute")
public class HomeConfiguration {
    @Bean
    public ViewResolver ViewResolver(){
        InternalResourceViewResolver ViewResolver=new InternalResourceViewResolver();
        ViewResolver.setPrefix("/WEB-INF/views/");
        ViewResolver.setSuffix(".jsp");
        return ViewResolver;
    }
}
