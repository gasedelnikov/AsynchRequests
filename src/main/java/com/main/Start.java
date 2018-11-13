package com.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@Import({RabbitConfiguration.class})
public class Start extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Start.class, args);
    }    
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Start.class);
    }


  

}