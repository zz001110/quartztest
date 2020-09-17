package com.quartztest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuartztestApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartztestApplication.class, args);
    }

}
