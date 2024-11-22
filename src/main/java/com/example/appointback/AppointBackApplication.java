package com.example.appointback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class AppointBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppointBackApplication.class, args);
        System.out.println("---- Project Appoint application; version in development 0.1 ----\n");
        System.out.println("type 'p' and press enter to proceed");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equals("p")) {
            //ExtraFunctionality inst = new ExtraFunctionality();
            //inst.run();
            System.out.println("end of listing");
        } else {
            System.out.println("wrong input");
        }
    }
}