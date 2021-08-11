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
    public AppointmentDto getAppointment(@PathVariable Long apId) {
        return null; // ToDo
    }

    @GetMapping("/getAll")
    public List<AppointmentDto> getAppointments() {
        return null; // there will be param inside this f. for a single doctor / ToDo
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentDto createAppointment(@RequestBody AppointmentDto dto) {
        return null; // ToDo
    }

    @PutMapping("/{apId}")
    public AppointmentDto updateAppointment(@PathVariable Long apId, @RequestBody AppointmentDto dto) {
        return null; // ToDo
    }

    @DeleteMapping("/{apId}")
    public void deleteAppointment(@PathVariable Long apId) {
        // ToDo
    }
}
