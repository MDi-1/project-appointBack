package com.example.appointback.controller;

import com.example.appointback.entity.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientMapper mapper;
    private final PatientRepository repository;
    
    @GetMapping("/{patientId}")
    public PatientDto getPatient(@PathVariable Long patientId) {
        return mapper.mapToPatientDto(repository.findById(patientId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getAll")
    public List<PatientDto> getPatients() {
        return mapper.mapToPatientDtoList(repository.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public PatientDto createPatient(@RequestBody PatientDto dto) {
        return mapper.mapToNewPatientDto(repository.save(mapper.mapToPatient(dto)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public PatientDto updatePatient(@RequestBody PatientDto dto) {
        return mapper.mapToPatientDto(repository.save(mapper.mapToPatient(dto)));
    }

    @DeleteMapping("/{patientId}")
    public void deletePatient(@PathVariable Long patientId) {
        repository.deleteById(patientId);
    }
}
