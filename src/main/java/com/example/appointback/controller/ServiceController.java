package com.example.appointback.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/service")
@RequiredArgsConstructor
public class ServiceController {

    @GetMapping("/duplicateCheck")
    public boolean getAppointment() {
        mapper.mapToAppointmentDto(repository.findById(apId).orElseThrow(IllegalArgumentException::new));
        return false;
    }

}
