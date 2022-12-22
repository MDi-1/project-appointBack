package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.Doctor;
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
    private final AppointmentRepository appRepository;

    @GetMapping("/{doctorId}")
    public DoctorDto getDoctor(@PathVariable int doctorId) throws IllegalArgumentException {
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
    public void deleteDoctor(@PathVariable int doctorId) {
        Doctor doc = repository.findById(doctorId).orElseThrow(IllegalArgumentException::new);
        List<Appointment> appointmentList = doc.getAppointments();
        for (Appointment item : appointmentList) {

        }
        repository.deleteById(doctorId);
    }
}
