package com.example.appointback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppointBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppointBackApplication.class, args);
        System.out.println("---- Project Appoint application starting; version (pre alpha) 0.0 ----\n");
    }
}

//todo: consider precedent interface of Repositories