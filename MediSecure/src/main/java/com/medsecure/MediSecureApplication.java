package com.medsecure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
 @EnableScheduling
public class MediSecureApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediSecureApplication.class, args);
    }
}