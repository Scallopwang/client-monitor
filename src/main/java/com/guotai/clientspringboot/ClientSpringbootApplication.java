package com.guotai.clientspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClientSpringbootApplication {


    public static void main(String[] args) {
        SpringApplication.run(ClientSpringbootApplication.class, args);
    }

}
