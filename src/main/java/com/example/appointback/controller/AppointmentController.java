package com.example.appointback.controller;

import com.example.appointback.entity.AppointmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    @GetMapping("/{apId}")
    public AppointmentDto getAppointment(@PathVariable int apId) {
        return null; // fixme
    }

    @GetMapping("/getAll")
    public List<AppointmentDto> getAppointments() {
        return null; // there will be param inside this f. for a single doctor / fixme
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentDto createAppointment(@RequestBody AppointmentDto dto) {
        return null; // fixme
    }

    @PutMapping("/{apId}")
    public AppointmentDto updateAppointment(@PathVariable int apId, @RequestBody AppointmentDto dto) {
        return null; // fixme
    }

    @DeleteMapping("/{apId}")
    public void deleteAppointment(@PathVariable int apId) {
        // fixme
    }
}
