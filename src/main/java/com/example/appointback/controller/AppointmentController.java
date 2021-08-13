package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.AppointmentDto;
import com.example.appointback.entity.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentMapper mapper;
    private final AppointmentRepository repository;
    private final DoctorRepository doctorRepository;

    @GetMapping("/{apId}")
    public AppointmentDto getAppointment(@PathVariable Long apId) {
        return mapper.mapToAppointmentDto(repository.findById(apId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getAll")
    public List<AppointmentDto> getAllAppointments() {
        return mapper.mapToAppointmentDtoList(repository.findAll());
    }

    @GetMapping("/doctorApps/{docId}")
    public List<AppointmentDto> getAppsForOneDoctor(@PathVariable Long docId) {
        Doctor doc = doctorRepository.findById(docId).orElseThrow(IllegalArgumentException::new);
        return mapper.mapToAppointmentDtoList(doc.getAppointments());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentDto createAppointment(@RequestBody AppointmentDto dto) {
        return mapper.mapToAppointmentDto(repository.save(mapper.mapToAppointment(dto)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentDto updateAppointment(@RequestBody AppointmentDto dto) {
        return mapper.mapToAppointmentDto(repository.save(mapper.mapToAppointment(dto)));
    }

    @DeleteMapping("/{apId}")
    public void deleteAppointment(@PathVariable Long apId) {
        repository.deleteById(apId);
    }
}
