package com.fitness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
    SecurityAutoConfiguration.class
})
public class FitnessCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitnessCenterApplication.class, args);
    }
}
