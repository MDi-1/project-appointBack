package com.example.appointback.controller;

import com.example.appointback.entity.*;
import com.example.appointback.entityfactory.CalendarHolderRepository;
import com.example.appointback.external.GoCalendarClient;
import com.google.api.services.calendar.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.appointback.external.GoCalendarClient.deleteEvent;
import static com.example.appointback.external.GoCalendarClient.postEvent;

@RestController
@RequestMapping("/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentMapper mapper;
    private final TimeFrameController tfController;
    private final AppointmentRepository repository;
    private final TimeFrameRepository tfRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final CalendarHolderRepository calendarHolderRepository;

    @GetMapping("/{apId}")
    public AppointmentDto getAppointment(@PathVariable Long apId) {
        return mapper.mapToAppointmentDto(repository.findById(apId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getAll")
    public List<AppointmentDto> getAllAppointments() {
        return mapper.mapToAppointmentDtoList(repository.findAll());
    }

    @GetMapping("/doctorApps/{docId}")
    public List<AppointmentDto> getAppsForDoctor(@PathVariable Long docId) {
        Doctor doc = doctorRepository.findById(docId).orElseThrow(IllegalArgumentException::new);
        return mapper.mapToAppointmentDtoList(doc.getAppointments());
    }

    @GetMapping("/patientApps/{patientId}")
    public List<AppointmentDto> getAppsForPatient(@PathVariable Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(IllegalArgumentException::new);
        return mapper.mapToAppointmentDtoList(patient.getAppointments());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentDto createAppointment(@RequestBody AppointmentDto appointmentDto) {
        Appointment clearedAppointment = clearWeekendCollision(mapper.mapToAppointment(appointmentDto));
        Appointment response = repository.save(clearedAppointment);
        CalendarHolder calendarHolder = calendarHolderRepository.findById(appointmentDto.getOwnersId())
                .orElseThrow(IllegalArgumentException::new);
        boolean synced = false;
        if (calendarHolder instanceof Doctor) {
            Doctor doc = (Doctor) calendarHolder;
            synced = doc.isGoCalendarSync();
        }
        if (calendarHolder instanceof Employee) {
            Employee employee = (Employee) calendarHolder;
            synced = employee.isGoCalendarSync();
        }
        if (synced) postEvent(response);
        return mapper.mapToAppointmentDto(response);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentDto updateAppointment(@RequestBody AppointmentDto appointmentDto) {
        Appointment clearedAppointment = clearWeekendCollision(mapper.mapToAppointment(appointmentDto));
        return mapper.mapToAppointmentDto(repository.save(clearedAppointment));
    }

    @DeleteMapping("/{apId}")
    public void deleteAppointment(@PathVariable Long apId) {
        System.out.println(" ]]] DELETE APPOINTMENT FIRED [[[ ");
        Appointment appointment = repository.findById(apId).orElseThrow(IllegalArgumentException::new);
        Doctor doctor = (Doctor) appointment.getDoctor();
        if (doctor.isGoCalendarSync()) deleteEvent(apId);
        repository.deleteById(apId);
    }

    @PostMapping(value = "/createEv", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Event goApiPost(@RequestBody AppointmentDto dto) {
        return GoCalendarClient.postEvent(mapper.mapToAppointment(dto));
    }

    public Appointment clearWeekendCollision(Appointment appointment) {
        LocalDateTime dateTime = appointment.getStartDateTime();
        while(dateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) || dateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            dateTime = dateTime.plusDays(1L);
        }
        appointment.setStartDateTime(dateTime);
        return appointment;
    }

    // eventually we ran across the problem where we can find Appointments posted without validation (Appointment
    // with no timeframe coverage) So there should be validation logic first instead of building
    // things like searchForOrphanedApps().
    public List<Appointment> searchForOrphanedApps() {
        List<Appointment> negativeList = new ArrayList<>();
        List<Appointment> resultList = repository.findAll();
        List<TimeFrame> allTfs = tfRepository.findAll();
        allTfs.forEach(tf -> negativeList.addAll(tfController.checkForAppsOutsideTf(tf)));
        resultList.removeAll(negativeList);
        return resultList.stream().filter(app -> allTfs.stream()
                        .noneMatch(tf -> {
                            boolean condition2 = app.getStartDateTime().toLocalDate().equals(tf.getTimeframeDate());
                            boolean condition1 = app.getDoctor().equals(tf.getDoctor());
                            return condition1 && condition2;
                            // return keyword here terminates shenanigans with extracted conditions. May try to see how
                            // simplified this be when conditions to be refactored as inline variable, then return
                            // keyword and curly braces need to be removed.
                        })).collect(Collectors.toList());
    }

    @GetMapping("/getOrphanedApps")
    public List<AppointmentDto> getOrphanedApps() {
        return mapper.mapToAppointmentDtoList(searchForOrphanedApps());
    }
}
