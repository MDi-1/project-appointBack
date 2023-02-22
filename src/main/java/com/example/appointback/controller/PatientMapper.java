package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.Patient;
import com.example.appointback.entity.PatientDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientMapper {

    private AppointmentRepository appointmentRepository;

    public Patient mapToPatient(final PatientDto patientDto) {
        List<Appointment> appList;
        if (patientDto.getAppointmentsIds() != null) {
            appList = patientDto.getAppointmentsIds().stream().map(aLong -> appointmentRepository.findById(aLong)
                    .orElseThrow(IllegalArgumentException::new)).collect(Collectors.toList());
        } else { appList = null; }
        return new Patient(patientDto.getId(), patientDto.getFirstName(), patientDto.getLastName(), appList);
    }

    public PatientDto mapToPatientDto(final Patient patient) {
        List<Long> appointments;
        if (patient.getAppointments() != null) {
            appointments = patient.getAppointments().stream().map(Appointment::getId).collect(Collectors.toList());
        } else appointments = new ArrayList<>(); // later to be written without "if" but in one stream() line.
        return new PatientDto(patient.getId(), patient.getFirstName(), patient.getLastName(), appointments);
    }

    public PatientDto mapToNewPatientDto(final Patient patient) {
        return new PatientDto(patient.getId(), patient.getFirstName(), patient.getLastName());
    }

    public List<PatientDto> mapToPatientDtoList(final List<Patient> patientList) {
        return patientList.stream().map(this::mapToPatientDto).collect(Collectors.toList());
    }
}
