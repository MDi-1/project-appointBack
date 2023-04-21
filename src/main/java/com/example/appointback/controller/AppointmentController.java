package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.AppointmentDto;
import com.example.appointback.entity.Doctor;
import com.example.appointback.entity.Patient;
import com.example.appointback.external.GoCalendarClient;
import com.google.api.services.calendar.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
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
    public AppointmentDto createAppointment(@RequestBody AppointmentDto dto) {
        Appointment response = repository.save(mapper.mapToAppointment(dto));
        Doctor doc = doctorRepository.findById(dto.getDoctorId()).orElseThrow(IllegalArgumentException::new);
        if (doc.isGoCalendarSync()) postEvent(response);
        return mapper.mapToAppointmentDto(response);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentDto updateAppointment(@RequestBody AppointmentDto dto) {
        return mapper.mapToAppointmentDto(repository.save(mapper.mapToAppointment(dto)));
    }

    @DeleteMapping("/{apId}")
    public void deleteAppointment(@PathVariable Long apId) {
        Appointment appointment = repository.findById(apId).orElseThrow(IllegalArgumentException::new);
        Doctor doctor = (Doctor) appointment.getDoctor();
        if (doctor.isGoCalendarSync()) deleteEvent(apId);
        repository.deleteById(apId);
    }

    @GetMapping("/getEv")
    public void goApiGet() throws GeneralSecurityException, IOException {
        GoCalendarClient.getEvents();
    }

    @PostMapping(value = "/createEv", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Event goApiPost(@RequestBody AppointmentDto dto) {
        return GoCalendarClient.postEvent(mapper.mapToAppointment(dto));
    }

    @GetMapping("/deleteEv/{apId}")
    public void goApiDel() { }
}
