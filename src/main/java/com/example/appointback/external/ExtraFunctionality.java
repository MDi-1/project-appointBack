package com.example.appointback.external;

import com.example.appointback.controller.TimeFrameRepository;
import com.example.appointback.entity.TimeFrame;
import java.util.List;

// This is an attempt to play with Spring without using REST.
// Main goal is to call anything from main()
public class ExtraFunctionality {

    private TimeFrameRepository repository;

    public ExtraFunctionality(TimeFrameRepository repository) {
        this.repository = repository;
    }

    public void run() {
        List<TimeFrame> list = repository.findAll();
        for (TimeFrame element : list) System.out.println(element);
    }
}