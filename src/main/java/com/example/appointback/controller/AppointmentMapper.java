package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.AppointmentDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentMapper {

    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;

    public Appointment mapToAppointment(final AppointmentDto dto) {
        return new Appointment(
                dto.getId(),
                LocalDateTime.parse(dto.getStartDateTime()),
                dto.getPrice(),
                doctorRepository.findById(dto.getDoctorId()).orElseThrow(IllegalArgumentException::new),
                patientRepository.findById(dto.getPatientId()).orElseThrow(IllegalArgumentException::new));
    }

    public AppointmentDto mapToAppointmentDto(final Appointment appointment) {
        return new AppointmentDto(
                appointment.getId(),
                appointment.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd','HH:mm")),
                appointment.getPrice(),
                appointment.getDoctor().getId(),
                appointment.getPatient().getId());
    }

    public List<AppointmentDto> mapToAppointmentDtoList(final List<Appointment> appointments) {
        return appointments.stream().map(this::mapToAppointmentDto).collect(Collectors.toList());
    }
}
