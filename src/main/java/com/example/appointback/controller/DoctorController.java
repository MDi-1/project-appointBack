package com.example.appointback.controller;

import com.example.appointback.entity.DoctorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {

    @GetMapping("/{doctorId}")
    public DoctorDto getDoctor(@PathVariable int doctorId) {
        return null; // fixme
    }

    @GetMapping("/getAll")
    public List<DoctorDto> getDoctors() {
        return null; // fixme
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DoctorDto createDoctor(@RequestBody DoctorDto dto) {
        return null; // fixme
    }

    @PutMapping("/{doctorId}")
    public DoctorDto updateDoctor(@PathVariable int doctorId, @RequestBody DoctorDto dto) {
        return null; // fixme
    }

    @DeleteMapping("/{doctorId}")
    public void deleteDoctor(@PathVariable int doctorId) {
    }
}
