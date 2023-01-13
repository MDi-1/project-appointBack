package com.example.appointback.controller;

import com.example.appointback.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/v1/test")
@RequiredArgsConstructor
public class TestObjectController {

    private final TestObjectRepository repository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepo;
    private final MedServiceRepository medServiceRepo;
    private final TimeFrameRepository timeFrameRepository;
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

    @PostMapping("/sampleDataFeed")
    public void sampleDataFeed() {
        Doctor doc1 = new Doctor("Dough", "Smith", "Specialist");
        Doctor doc2 = new Doctor("Alison", "Green", "Specialist");
        Doctor doc3 = new Doctor("Doc", "Marshall", "Specialist");
        doctorRepository.save(doc1);
        doctorRepository.save(doc2);
        doctorRepository.save(doc3);
        TimeFrame tf0 = new TimeFrame(
                LocalDate.of(2022, 10, 10), LocalTime.of(9, 0), LocalTime.of(15, 0), "Present", doc2);
        TimeFrame tf1 = new TimeFrame(
                LocalDate.of(2022, 10, 10), LocalTime.of(8, 0), LocalTime.of(16, 0), "Present", doc1);
        TimeFrame tf2 = new TimeFrame(
                LocalDate.of(2022, 10, 11), LocalTime.of(8, 0), LocalTime.of(15, 0), "Present", doc1);
        TimeFrame tf3 = new TimeFrame(
                LocalDate.of(2022, 10, 12), LocalTime.of(10, 0), LocalTime.of(14, 0), "Present", doc1);
        TimeFrame tfa = new TimeFrame(
                LocalDate.of(2022, 10, 9), LocalTime.of(10, 0), LocalTime.of(14, 0), "Present", doc1);
        TimeFrame tf4 = new TimeFrame(
                LocalDate.of(2022, 6, 1), LocalTime.of(9, 0), LocalTime.of(15, 0), "Present", doc2);
        TimeFrame tf5 = new TimeFrame(
                LocalDate.of(2022, 6, 6), LocalTime.of(10, 0), LocalTime.of(13, 0), "Present", doc2);
        TimeFrame tf6 = new TimeFrame(
                LocalDate.of(2022, 5, 30), LocalTime.of(11, 0), LocalTime.of(14, 0), "Present", doc2);
        TimeFrame tf7 = new TimeFrame(
                LocalDate.of(2022, 5, 30), LocalTime.of(12, 0), LocalTime.of(14, 0), "Present", doc3);
        TimeFrame tf8 = new TimeFrame(
                LocalDate.of(2022, 10, 12), LocalTime.of(11, 0), LocalTime.of(14, 0), "Present", doc3);
        TimeFrame tf9 = new TimeFrame(
                LocalDate.of(2022, 11, 1), LocalTime.of(11, 0), LocalTime.of(14, 0), "Present", doc3);
        //TimeFrame tfn = new TimeFrame(LocalDate.now(), LocalTime.of(8, 0), LocalTime.of(16, 0), doc3);
        List<TimeFrame> list = new ArrayList<>(
                Arrays.asList(tf0, tf1, tf2, tf3, tfa, tf4, tf5, tf6, tf7, tf8, tf9 ));
        timeFrameRepository.saveAll(list);
        Collections.addAll(doc1.getTimeFrames(), tf1, tf2, tf3);
        Collections.addAll(doc2.getTimeFrames(), tf0, tf4, tf5, tf6);
        Collections.addAll(doc3.getTimeFrames(), tf7, tf8, tf9);
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
        Appointment appt1 = new Appointment(LocalDateTime.of(2023, 12, 30, 12, 55), 30, doc1, patient1);
        Appointment appt2 = new Appointment(LocalDateTime.of(2022, 9, 2, 13, 45), 25, doc2, patient2);
        Appointment appt3 = new Appointment(LocalDateTime.of(2022, 10, 3, 9, 0), 25, doc3, patient3);
        appointmentRepo.save(appt1);
        appointmentRepo.save(appt2);
        appointmentRepo.save(appt3);
        MedicalService ms1 = new MedicalService("Laryngologist", doc1);
        medServiceRepo.save(ms1);
    }

    @PostMapping("/sampleDataFeed_TF")
    public void sampleDataFeed_TF() {
        List<Doctor> doctors = doctorRepository.findAll();
        doctors.forEach(doc -> doc.getTimeFrames().stream());// fixme - auto TF creation for all docs in DB
        // (one day starts with 8:00, next day with other hour)
    }
}
