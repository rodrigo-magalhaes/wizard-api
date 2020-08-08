package com.magalhaes.wizardapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class WizardApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WizardApiApplication.class, args);
    }

}
