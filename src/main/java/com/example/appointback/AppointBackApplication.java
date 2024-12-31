package com.example.appointback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppointBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppointBackApplication.class, args);
        System.out.println("---- Project Appoint application; version in development 0.0 ----\n");
    }
}
// temporarily restoring branch to play with "test objects" (like TestObjectController)