package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.AppointmentDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class AppointmentMapper {

    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;

    public Appointment mapToAppointment(final AppointmentDto dto) {
        return new Appointment(
                dto.getId(),
                LocalDateTime.parse(dto.getStartDate()),
                dto.getDuration(),
                doctorRepository.findById(dto.getDoctorId()).orElseThrow(IllegalArgumentException::new),
                patientRepository.findById(dto.getPatientId()).orElseThrow(IllegalArgumentException::new));
    }

    public AppointmentDto mapToAppointmentDto(final Appointment appointment) {
        return new AppointmentDto(
                appointment.getId(),
                appointment.getStartDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")), // fixme
                appointment.getDuration(),
                appointment.getDoctor().getId(),
                appointment.getPatient().getId());
    }
}
