package com.example.appointback.controller;

import com.example.appointback.entity.DoctorDto;
import com.example.appointback.entity.MedicalServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/medService")
@RequiredArgsConstructor
public class MedServiceController {

    @GetMapping("/{serviceId}")
    public MedicalServiceDto getMedService(@PathVariable int serviceId) {
        return null; // fixme
    }

    @GetMapping("/getAll")
    public List<DoctorDto> getMedServices() {
        return null; // fixme
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public MedicalServiceDto createMedService(@RequestBody MedicalServiceDto dto) {
        return null; // fixme
    }

    @PutMapping("/{serviceId}")
    public MedicalServiceDto updateMedService(@PathVariable int serviceId, @RequestBody MedicalServiceDto dto) {
        return null; // fixme
    }

    @DeleteMapping("/{serviceId}")
    public void deleteMedService(@PathVariable int serviceId) {
    }

}
