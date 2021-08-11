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

    private final DoctorMapper doctorMapper;
    private final DoctorRepository repository;

    /*
    @GetMapping("/{doctorId}")
    public DoctorDto getDoctor(@PathVariable Long doctorId) throws IllegalArgumentException {
        return doctorMapper.mapToDoctorDto(repository.findById(doctorId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getAll")
    public List<DoctorDto> getDoctors() {
        return doctorMapper.mapToDoctorDtoList(repository.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DoctorDto createDoctor(@RequestBody DoctorDto dto) {
        return doctorMapper.mapToNewDoctorDto(repository.save(doctorMapper.mapToDoctor(dto)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DoctorDto updateDoctor(@RequestBody DoctorDto dto) {
        return doctorMapper.mapToDoctorDto(repository.save(doctorMapper.mapToDoctor(dto)));
    }
     */

    @DeleteMapping("/{doctorId}")
    public void deleteDoctor(@PathVariable Long doctorId) {
        repository.deleteById(doctorId);
    }
}
