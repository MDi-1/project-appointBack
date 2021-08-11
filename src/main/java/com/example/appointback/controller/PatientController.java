package com.example.appointback.controller;

import com.example.appointback.entity.DoctorDto;
import com.example.appointback.entity.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/patient")
@RequiredArgsConstructor
public class PatientController {
    
    @GetMapping("/{patientId}")
    public PatientDto getPatient(@PathVariable Long patientId) {
        return null; // fixme
    }

    @GetMapping("/getAll")
    public List<PatientDto> getPatients() {
        return null; // fixme
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public PatientDto createPatient(@RequestBody PatientDto dto) {
        return null; // fixme
    }

    @PutMapping("/{patientId}")
    public PatientDto updatePatient(@PathVariable int patientId, @RequestBody PatientDto dto) {
        return null; // fixme
    }

    @DeleteMapping("/{patientId}")
    public void deletePatient(@PathVariable Long patientId) {
    }

}
