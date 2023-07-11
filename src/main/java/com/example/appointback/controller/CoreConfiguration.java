package com.example.appointback.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.LocalTime;

@EnableScheduling
@Configuration
public class CoreConfiguration {

    public static LocalTime DEFAULT_STARTING_TIME = LocalTime.of(8, 0);
    public static LocalTime DEFAULT_ENDING_TIME = LocalTime.of(8, 0);

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static LocalDate getPresentDate() {
        return LocalDate.now();
    }
}
