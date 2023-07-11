package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.Doctor;
import com.example.appointback.entity.DoctorDto;
import com.example.appointback.entity.Scheduler;
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
    private final SchedulerRepository schedulerRepository;

    @GetMapping("/{doctorId}")
    public DoctorDto getDoctor(@PathVariable Long doctorId) {
        return mapper.mapToDoctorDto(repository.findById(doctorId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getAll")
    public List<DoctorDto> getDoctors() {
        return mapper.mapToDoctorDtoList(repository.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DoctorDto createDoctor(@RequestBody DoctorDto dto) {
        return mapper.mapToDoctorDto(repository.save(mapper.mapToDoctor(dto)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DoctorDto updateDoctor(@RequestBody DoctorDto dto) {
        return mapper.mapToDoctorDto(repository.save(mapper.mapToDoctor(dto)));
    }

    @DeleteMapping("/{doctorId}")
    public String deleteDoctor(@PathVariable Long doctorId) {
        Doctor doc = repository.findById(doctorId).orElseThrow(IllegalArgumentException::new);
        Scheduler sc = schedulerRepository.findByName("Default_Scheduler");
        if (doc.getAppointments() == null  || doc.getAppointments().size() < 1 ) {
            System.out.println("---- Project Appoint application ---- doctor had not any appointment");
            repository.deleteById(doctorId);
            return null;
        }
        if (sc != null) {
            System.out.println("---- Project Appoint application ---- reassigning from - to:\n" + doc + " \n " + sc);
            for (Appointment app : doc.getAppointments()) {
                app.setDoctor(sc);
                appRepository.save(app);
            }
            repository.deleteById(doctorId);
            return null;
        } else {
            return "---- Project Appoint application ---- Unable to reassign doctor's appointments.";
        }
    }
}
