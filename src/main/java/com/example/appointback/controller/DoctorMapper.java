package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.Doctor;
import com.example.appointback.entity.DoctorDto;
import com.example.appointback.entity.MedicalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorMapper {

    public Doctor mapToDoctor(final DoctorDto doctorDto) {
        return new Doctor(doctorDto.getFirstName(), doctorDto.getLastName(), doctorDto.getPosition());
    }

    public DoctorDto mapToNewDoctorDto(final Doctor doctor) {
        return new DoctorDto(doctor.getId(), doctor.getFirstName(), doctor.getLastName(), doctor.getPosition());
    }

    public DoctorDto mapToDoctorDto(final Doctor doctor) {
        return new DoctorDto(doctor.getId(), doctor.getFirstName(), doctor.getLastName(), doctor.getPosition(),
                doctor.getTimeFrame().getId(),
                doctor.getAppointments().stream().map(Appointment::getId).collect(Collectors.toList()),
                doctor.getMedicalServices().stream().map(MedicalService::getId).collect(Collectors.toList())
        );
    }

    public List<DoctorDto> mapToDoctorDtoList(final List<Doctor> doctorList) {
        return doctorList.stream().map(this::mapToDoctorDto).collect(Collectors.toList());
    }
}
