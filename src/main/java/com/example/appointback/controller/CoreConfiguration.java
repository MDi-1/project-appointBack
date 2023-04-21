package com.example.appointback.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@EnableScheduling
@Configuration
public class CoreConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static LocalDate getStartingDate() {
        return LocalDate.of(2022, 9, 15); // to be changed for deployment to .now()
    }
}
