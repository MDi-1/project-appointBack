package com.example.appointback.controller;

import com.example.appointback.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static com.example.appointback.controller.CoreConfiguration.getStartingDate;
import static com.example.appointback.entity.CalendarHolder.Position.*;

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
    private final AppointmentController appointmentController;
    private final AppointmentMapper appointmentMapper;
    private final TimeFrameController tfController;
    private final TimeFrameMapper tfMapper;

    @Scheduled(cron = "0 0 10 * * *")
    @GetMapping("/dDbCheck") // abbreviation: "duplicate database check"
    public int dDbCheck() { // something is wrong with it, I think. (but scheduler works) fixme
        Set<Appointment> appCheckSet = new HashSet<>();
        List<AppointmentDto> appExcessList = new ArrayList<>();
        for (Appointment appointment : appRepository.findAll()) {
            if (!appCheckSet.add(appointment)) appExcessList.add(appointmentMapper.mapToAppointmentDto(appointment));
        }
        Set<TimeFrame> tfCheckSet = new HashSet<>();
        List<TimeFrameDto> tfExcessList = new ArrayList<>();
        for (TimeFrame timeFrame : timeFrameRepository.findAll()) {
            if (!tfCheckSet.add(timeFrame)) tfExcessList.add(tfMapper.mapToTimeFrameDto(timeFrame));
        }
        System.out.println("---- Project Appoint application; duplicate objects in db: ----");
        for (AppointmentDto appItem : appExcessList) System.out.println(appItem);
        for (TimeFrameDto tfItem : tfExcessList) System.out.println(tfItem);
        return appExcessList.size() + tfExcessList.size();
    }

    @PostMapping("/sampleDataFeed")
    public void sampleDataFeed() {
        Doctor doc1 = new Doctor("Dough", "Smith", Specialist, false);
        Doctor doc2 = new Doctor("Alison", "Green", Manager, false);
        Doctor doc3 = new Doctor("Doc", "Marshall", Board, false);
        schedulerRepository.save(new Scheduler("Default_Scheduler"));
        schedulerRepository.save(new Scheduler("Holiday_Scheduler"));
        doctorRepository.save(doc1);
        doctorRepository.save(doc2);
        doctorRepository.save(doc3);
        Patient p1 = new Patient("Jane", "Dou");
        Patient p2 = new Patient("John", "Doe");
        Patient p3 = new Patient("Hugo", "Bossy");
        Patient p4 = new Patient("Kriss", "deValnor");
        Patient p5 = new Patient("Kristina", "Ronaldina");
        patientRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
        List<Appointment> appointments = Arrays.asList(
            new Appointment(LocalDateTime.of(getStartingDate().plusDays(0L), LocalTime.of(11, 0)), 150, doc1, p1),
            new Appointment(LocalDateTime.of(getStartingDate().plusDays(2L), LocalTime.of(12, 0)), 150, doc1, p1),
            new Appointment(LocalDateTime.of(getStartingDate().plusDays(1L), LocalTime.of(13, 0)), 160, doc1, p1),
            new Appointment(LocalDateTime.of(getStartingDate().plusDays(3L), LocalTime.of(14, 0)), 160, doc1, p1),
            new Appointment(LocalDateTime.of(getStartingDate().plusDays(3L), LocalTime.of(8 , 0)), 200, doc2, p2),
            new Appointment(LocalDateTime.of(getStartingDate().plusDays(5L), LocalTime.of(9 , 0)), 150, doc2, p3),
            new Appointment(LocalDateTime.of(getStartingDate().plusDays(3L), LocalTime.of(9 , 0)), 160, doc3, p1),
            new Appointment(LocalDateTime.of(getStartingDate().plusDays(4L), LocalTime.of(15, 0)), 160, doc3, p3),
            new Appointment(LocalDateTime.of(getStartingDate().plusDays(7L), LocalTime.of(11, 0)), 160, doc3, p3)
        );
        appointments.forEach(appointmentController::clearWeekendCollision);
        appRepository.saveAll(appointments);
        medServiceRepo.save(new MedicalService("Laryngologist", null));
        tfController.autoCreateTimeFrames(getStartingDate());
    }
}