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
    public DoctorDto getDoctor(@PathVariable Long doctorId) throws IllegalArgumentException {
        System.out.println(repository.findById(doctorId));
        return mapper.mapToDoctorDto(repository.findById(doctorId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getAll")
    public List<DoctorDto> getDoctors() {
        return mapper.mapToDoctorDtoList(repository.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DoctorDto createDoctor(@RequestBody DoctorDto dto) {
        Doctor doctor = repository.save(mapper.mapToDoctor(dto));
        System.out.println(" ]] print [[ " + doctor);
        DoctorDto dtoReturned = mapper.mapToNewDoctorDto(doctor);
        System.out.println(" ]] print [[ " + dtoReturned);
        return dtoReturned;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DoctorDto updateDoctor(@RequestBody DoctorDto dto) {
        Doctor doctor = repository.save(mapper.mapToDoctor(dto));
        System.out.println(" ]] print [[ " + doctor);
        DoctorDto dtoReturned = mapper.mapToNewDoctorDto(doctor);
        System.out.println(" ]] print [[ " + dtoReturned);
        return dtoReturned;
    }

    @DeleteMapping("/{doctorId}")
    public void deleteDoctor(@PathVariable Long doctorId) {
        Doctor doc = repository.findById(doctorId).orElseThrow(IllegalArgumentException::new);
        List<Appointment> appointmentList = doc.getAppointments();
        for (Appointment item : appointmentList) {
            // fixme
        }
        repository.deleteById(doctorId);
    }
}
