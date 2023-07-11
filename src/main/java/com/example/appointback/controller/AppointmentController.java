package com.example.appointback.controller;

import com.example.appointback.entity.*;
import com.example.appointback.entityfactory.CalendarHolderRepository;
import com.example.appointback.external.GoCalendarClient;
import com.google.api.services.calendar.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.appointback.external.GoCalendarClient.deleteEvent;
import static com.example.appointback.external.GoCalendarClient.postEvent;

@RestController
@RequestMapping("/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentMapper mapper;
    private final AppointmentRepository repository;
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
}
