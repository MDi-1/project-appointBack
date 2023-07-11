package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.AppointmentDto;
import com.example.appointback.entity.CalendarHolder;
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
    private SchedulerRepository scRepository;
    private PatientRepository patientRepository;
    private MedServiceRepository msRepository;

    public Appointment mapToAppointment(final AppointmentDto dto) {
        CalendarHolder calendarHolder = doctorRepository.findById(dto.getOwnersId()).orElse(null);
        if (calendarHolder == null) {
            calendarHolder = scRepository.findById(dto.getOwnersId()).orElseThrow(IllegalArgumentException::new);
        }
        return new Appointment(
                dto.getId(),
                LocalDateTime.parse(dto.getStartDateTime()),
                dto.getPrice(),
                msRepository.findById(dto.getMedicalServiceId()).orElse(null),
                calendarHolder,
                patientRepository.findById(dto.getPatientId()).orElseThrow(IllegalArgumentException::new));
    }

    public AppointmentDto mapToAppointmentDto(final Appointment appointment) {
        Long medicalServiceId;
        if (appointment.getMedicalService() != null) medicalServiceId = appointment.getMedicalService().getId();
        else medicalServiceId = null;
        return new AppointmentDto(
                appointment.getId(),
                appointment.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd','HH:mm")),
                appointment.getPrice(),
                medicalServiceId,
                appointment.getDoctor().getId(),
                appointment.getPatient().getId());
    }

    public List<AppointmentDto> mapToAppointmentDtoList(final List<Appointment> appointments) {
        return appointments
                .stream()
                .map(this::mapToAppointmentDto)
                .collect(Collectors.toList());
    }
}
