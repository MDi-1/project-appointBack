package com.example.appointback.controller;

import com.example.appointback.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.appointback.controller.CoreConfiguration.*;

@RestController
@RequestMapping("/v1/service")
@RequiredArgsConstructor
public class MaintenanceController {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final MedServiceRepository medServiceRepo;
    private final SchedulerRepository schedulerRepository;
    private final AppointmentRepository appRepository;
    private final AppointmentController appController;
    private final TimeFrameController tfController;
    private List<AppointmentDto> appExcessList = new ArrayList<>();
    private List<TimeFrameDto> tfExcessList = new ArrayList<>();

    @Scheduled(cron = "0 0 10 * * *")
    @GetMapping("/dDbCheck") // abbreviation: "duplicate database check"
    public int dDbCheck() {
        List<AppointmentDto> allAppointments = appController.getAllAppointments();
        List<TimeFrameDto> allTimeFrames = tfController.getTimeFrames();
        Set<AppointmentDto> appCheckSet = new HashSet<>();
        for (AppointmentDto appointmentDto : allAppointments) {
            if (!appCheckSet.add(appointmentDto)) appExcessList.add(appointmentDto);
        }
        Set<TimeFrameDto> tfCheckSet = new HashSet<>();
        for (TimeFrameDto timeFrameDto : allTimeFrames) {
            if (!tfCheckSet.add(timeFrameDto)) tfExcessList.add(timeFrameDto);
        }
        System.out.println("---- Project Appoint application; duplicate objects in db: ----");
        for (AppointmentDto appItem : appExcessList) System.out.println(appItem);
        for (TimeFrameDto tfItem : tfExcessList) System.out.println(tfItem);
        int duplicateAmount = appExcessList.size() + tfExcessList.size();
        appExcessList.clear();
        tfExcessList.clear();
        return duplicateAmount;
    }

    @PostMapping("/sampleDataFeed")
    public void sampleDataFeed() { // intended for use to populate empty database only; not allowed to use repetitively
        System.out.println("---- Project Appoint application; execute -sampleDataFeed- function: ----");
        schedulerRepository.save(new Scheduler("Default_Scheduler"));
        schedulerRepository.save(new Scheduler("Holiday_Scheduler"));
        MedicalService[] msArray = {new MedicalService("Physician", 160), new MedicalService("Laryngologist", 200)};
        medServiceRepo.saveAll(Arrays.asList(msArray));
        Doctor[] doctors = {
                new Doctor("Dough", "Smith", CalendarHolder.Position.Specialist, false),
                new Doctor("Alison", "Green", CalendarHolder.Position.Manager, false),
                new Doctor("Doc", "Marshall", CalendarHolder.Position.Board, false)
        };
        doctors[0].setMedicalServices(Collections.singletonList(msArray[0]));
        doctors[1].setMedicalServices(Collections.singletonList(msArray[0]));
        doctors[2].setMedicalServices(Collections.singletonList(msArray[1]));
        doctorRepository.saveAll(Arrays.asList(doctors));
        Patient[] patients = {
                new Patient("Jane", "Dou"),
                new Patient("John", "Doe"),
                new Patient("Hugo", "Bossy"),
                new Patient("Kriss", "deValnor"),
                new Patient("Kristina", "Ronaldina")
        };
        patientRepository.saveAll(Arrays.asList(patients));

        System.out.println("present date:" + getPresentDate());

        feedDatabaseWithRandomApps(doctors, patients, getPresentDate().toString());
        tfController.autoCreateTimeFrames(getPresentDate());
    }

    @PostMapping("/addSomeRandomApps/{startingDateString}")
    public void feedDatabaseWithRandomApps(Doctor[] doctorArray, Patient[] patientArray, String startingDateString) {
        System.out.println("---- execute -addSomeRandomApps- function: ----");
        List<Doctor> doctorList;
        List<Patient> patientList;
        if (doctorArray == null) doctorList = doctorRepository.findAll();
        else doctorList = Arrays.asList(doctorArray);
        if (patientArray == null) patientList = patientRepository.findAll();
        else patientList = Arrays.asList(patientArray);
        LocalDate startingDate = LocalDate.parse(startingDateString,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Random random = new Random();
        int iterations = 4 + random.nextInt(12);
        List<Appointment> appointments = doctorList.stream()
                .flatMap(doc -> IntStream.range(0, iterations)
                        .mapToObj(e -> {
                            long days = random.nextInt(15);
                            int hour = 8 + random.nextInt(7);
                            int idxPat = random.nextInt(patientList.size());
                            List<MedicalService> docMsList = doc.getMedicalServices();
                            MedicalService ms = docMsList.get(random.nextInt(docMsList.size()));
                            LocalDateTime dt = LocalDateTime.of(startingDate.plusDays(days), LocalTime.of(hour, 0));
                            Appointment a = new Appointment(null, dt, ms.getPrice(), ms, doc, patientList.get(idxPat)); // 2 B del fixme
                            System.out.println(a);
                            return new Appointment(null, dt, ms.getPrice(), ms, doc, patientList.get(idxPat));
                        }))
                .collect(Collectors.toList());
        appointments.forEach(appController::clearWeekendCollision);
        appRepository.saveAll(appointments);
        dDbCheck();
        appExcessList.forEach(dto -> {
            try {
                appController.deleteAppointment(dto.getId());
            } catch (IllegalArgumentException e) {
                System.out.println(" ]]] caught IllegalArgumentException when deleting [[[ ");
                appController.getAllAppointments().stream().filter(a -> a.equals(dto)).forEach(x -> {
                    System.out.println("   x");
                });
            }
        });
    }

    public void setAppExcessList(List<AppointmentDto> appExcessList) {
        this.appExcessList = appExcessList;
    }

    public void setTfExcessList(List<TimeFrameDto> tfExcessList) {
        this.tfExcessList = tfExcessList;
    }
}