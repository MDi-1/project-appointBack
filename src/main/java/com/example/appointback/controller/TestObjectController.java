package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.Doctor;
import com.example.appointback.entity.Patient;
import com.example.appointback.entity.TestObjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/test")
@RequiredArgsConstructor
public class TestObjectController {

    private final TestObjectRepository repository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final TestObjectMapper mapper;

    @GetMapping("/{testObjId}")
    public TestObjectDto getTestObject(@PathVariable Long testObjId) {
        return mapper.mapToTestObjectDto(repository.findById(testObjId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getAll")
    public List<TestObjectDto> getTestObjects() {
        return mapper.mapToTestObjectDtoList(repository.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TestObjectDto createTestObject(@RequestBody TestObjectDto dto) {
        return mapper.mapToTestObjectDto(repository.save(mapper.mapToTestObject(dto)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TestObjectDto updateTestObject(@RequestBody TestObjectDto dto) {
        return mapper.mapToTestObjectDto(repository.save(mapper.mapToTestObject(dto)));
    }

    @GetMapping("/sampleDataFeed")
    public boolean sampleDataFeed() {
        Doctor doc1 = new Doctor("Doug", "Smith", "Specialist");
        Doctor doc2 = new Doctor("Alison", "Green", "Specialist");
        Doctor doc3 = new Doctor("Doc", "Marshall", "Specialist");
        doctorRepository.save(doc1);
        doctorRepository.save(doc2);
        doctorRepository.save(doc3);
        Patient patient1 = new Patient("Jane", "Dou");
        Patient patient2 = new Patient("John", "Doe");
        Patient patient3 = new Patient("Hugo", "Bossy");
        Patient patient4 = new Patient("Kriss", "deValnor");
        Patient patient5 = new Patient("Kristina", "Ronaldina");
        patientRepository.save(patient1);
        patientRepository.save(patient2);
        patientRepository.save(patient3);
        patientRepository.save(patient4);
        patientRepository.save(patient5);
        Appointment appt1 = new Appointment(LocalDateTime.of(2021, 12, 30, 12, 55), 30, doc1, patient1);
        Appointment appt2 = new Appointment(LocalDateTime.of(2022, 9, 2, 13, 45), 25, doc2, patient2);
        Appointment appt3 = new Appointment(LocalDateTime.of(2022, 10, 3, 9, 0), 25, doc3, patient3);
        appointmentRepository.save(appt1);
        appointmentRepository.save(appt2);
        appointmentRepository.save(appt3);
        return true;
    }
}
