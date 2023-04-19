package com.example.appointback.controller;

import com.example.appointback.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/v1/service")
@RequiredArgsConstructor
public class MaintenanceController {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appRepository;
    private final MedServiceRepository medServiceRepo;
    private final TimeFrameRepository timeFrameRepository;
    private final SchedulerRepository schedulerRepository;
    private final TimeFrameController tfController;
    private final AppointmentMapper appMapper;
    private final TimeFrameMapper tfMapper;

    @Scheduled(cron = "0 0 10 * * *")
    @GetMapping("/dDbCheck") // abbreviation: "duplicate database check"
    public int dDbCheck() { // something is wrong with it, I think. (but scheduler works) fixme
        Set<Appointment> appCheckSet = new HashSet<>();
        List<AppointmentDto> appExcessList = new ArrayList<>();
        for (Appointment appointment : appRepository.findAll()) {
            if (!appCheckSet.add(appointment)) appExcessList.add(appMapper.mapToAppointmentDto(appointment));
        }
        Set<TimeFrame> tfCheckSet = new HashSet<>();
        List<TimeFrameDto> tfExcessList = new ArrayList<>();
        for (TimeFrame timeFrame : timeFrameRepository.findAll()) {
            if (!tfCheckSet.add(timeFrame)) tfExcessList.add(tfMapper.mapToTimeFrameDto(timeFrame));
        }
        System.out.println("---- Project Appoint application; duplicate objects in db: ----\n");
        for (AppointmentDto appItem : appExcessList) System.out.println(appItem);
        for (TimeFrameDto tfItem : tfExcessList) System.out.println(tfItem);
        return appExcessList.size() + tfExcessList.size();
    }

    @PostMapping("/sampleDataFeed")
    public void sampleDataFeed() {
        Doctor doc1 = new Doctor("Dough", "Smith", "Specialist", false);
        Doctor doc2 = new Doctor("Alison", "Green", "Manager", false);
        Doctor doc3 = new Doctor("Doc", "Marshall", "Board", false);
        schedulerRepository.save(new Scheduler("Default_Scheduler"));
        schedulerRepository.save(new Scheduler("Holiday_Scheduler"));
        doctorRepository.save(doc1);
        doctorRepository.save(doc2);
        doctorRepository.save(doc3);
        LocalDate today = LocalDate.of(2022, 9, 15); // for deployment "today"to be changed into this: LocalDate.now();
        TimeFrame tf0 = new TimeFrame(today.plusDays(30L), LocalTime.of(9 , 0), LocalTime.of(15, 0), "Present", doc1);
        TimeFrame tf1 = new TimeFrame(today.plusDays(31L), LocalTime.of(9 , 0), LocalTime.of(16, 0), "Present", doc1);
        TimeFrame tf2 = new TimeFrame(today.plusDays(32L), LocalTime.of(9 , 0), LocalTime.of(15, 0), "Present", doc1);
        TimeFrame tf3 = new TimeFrame(today.plusDays(31L), LocalTime.of(8 , 0), LocalTime.of(16, 0), "Present", doc2);
        TimeFrame tf4 = new TimeFrame(today.plusDays(32L), LocalTime.of(8 , 0), LocalTime.of(16, 0), "Present", doc2);
        TimeFrame tf5 = new TimeFrame(today.plusDays(33L), LocalTime.of(8 , 0), LocalTime.of(16, 0), "Present", doc3);
        TimeFrame tf6 = new TimeFrame(today.plusDays(34L), LocalTime.of(10, 0), LocalTime.of(16, 0), "Present", doc3);
        TimeFrame tf7 = new TimeFrame(today.plusDays(27L), LocalTime.of(10, 0), LocalTime.of(16, 0), "Present", doc3);
        timeFrameRepository.saveAll(Arrays.asList(tf0, tf1, tf2, tf3, tf4, tf5, tf6, tf7));
        Patient p1 = new Patient("Jane", "Dou");
        Patient p2 = new Patient("John", "Doe");
        Patient p3 = new Patient("Hugo", "Bossy");
        Patient p4 = new Patient("Kriss", "deValnor");
        Patient p5 = new Patient("Kristina", "Ronaldina");
        patientRepository.save(p1);
        patientRepository.save(p2);
        patientRepository.save(p3);
        patientRepository.save(p4);
        patientRepository.save(p5);
        appRepository.save(new Appointment(LocalDateTime.of(today.plusDays(30L), LocalTime.of(12, 0)), 150, doc1, p1));
        appRepository.save(new Appointment(LocalDateTime.of(today.plusDays(31L), LocalTime.of(12, 0)), 150, doc1, p1));
        appRepository.save(new Appointment(LocalDateTime.of(today.plusDays(31L), LocalTime.of(13, 0)), 160, doc1, p1));
        appRepository.save(new Appointment(LocalDateTime.of(today.plusDays(32L), LocalTime.of(14, 0)), 160, doc1, p1));
        appRepository.save(new Appointment(LocalDateTime.of(today.plusDays(31L), LocalTime.of(8 , 0)), 200, doc2, p2));
        appRepository.save(new Appointment(LocalDateTime.of(today.plusDays(32L), LocalTime.of(9 , 0)), 150, doc2, p3));
        appRepository.save(new Appointment(LocalDateTime.of(today.plusDays(33L), LocalTime.of(9 , 0)), 160, doc3, p1));
        appRepository.save(new Appointment(LocalDateTime.of(today.plusDays(34L), LocalTime.of(15, 0)), 160, doc3, p3));
        appRepository.save(new Appointment(LocalDateTime.of(today.plusDays(27L), LocalTime.of(11, 0)), 160, doc3, p3));
        medServiceRepo.save(new MedicalService("Laryngologist", null));
        tfController.autoCreateTimeFrames(today);
    }
}