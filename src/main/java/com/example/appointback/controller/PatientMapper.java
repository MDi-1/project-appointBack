package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.Patient;
import com.example.appointback.entity.PatientDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientMapper {

    private final AppointmentRepository repository;

    public Patient mapToPatient(final PatientDto patientDto) {
        return new Patient(patientDto.getId(), patientDto.getFirstName(), patientDto.getLastName(),
                patientDto.getAppointmentsIds().stream()
                        .map(repository::findById)
                        .map(Optional::get)
                        .collect(Collectors.toList()));
    }

    public PatientDto mapToPatientDto(final Patient patient) {
        return new PatientDto(patient.getId(), patient.getFirstName(), patient.getLastName(),
                patient.getAppointments().stream().map(Appointment::getId).collect(Collectors.toList()));
    }

    public List<PatientDto> mapToPatientDtoList(final List<Patient> patientList) {
        return patientList.stream().map(this::mapToPatientDto).collect(Collectors.toList());
    }
}
