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

    private final DoctorMapper mapper;
    private final DoctorRepository repository;

    @GetMapping("/{doctorId}")
    public DoctorDto getDoctor(@PathVariable Long doctorId) throws IllegalArgumentException {
        return mapper.mapToDoctorDto(repository.findById(doctorId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getAll")
    public List<DoctorDto> getDoctors() {
        return mapper.mapToDoctorDtoList(repository.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DoctorDto createDoctor(@RequestBody DoctorDto dto) {
        return mapper.mapToNewDoctorDto(repository.save(mapper.mapToDoctor(dto)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DoctorDto updateDoctor(@RequestBody DoctorDto dto) {
        return mapper.mapToDoctorDto(repository.save(mapper.mapToDoctor(dto)));
    }

    @DeleteMapping("/{doctorId}")
    public void deleteDoctor(@PathVariable Long doctorId) {
        repository.deleteById(doctorId);
    }
}
