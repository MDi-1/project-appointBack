package com.example.appointback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppointBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppointBackApplication.class, args);
        System.out.println("---- Tiny Clinic application starting; version (pre alpha) 0.0 ----");
    }
}
//fixme
//Program nie uruchamia się, ale kompiluje jest takie coś w konsoli:
//Hibernate: alter table appointments add constraint FKmujeo4tymoo98cmf7uj3vsv76 foreign key (doctor_id) references
// doctors (id)
//Ale w momencie gdy zrobimy drop wszystkich tabeli to program się jednak uruchamia
