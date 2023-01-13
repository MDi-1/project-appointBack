package com.example.appointback.controller;

import com.example.appointback.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorMapper {

    public Doctor mapToDoctor(final DoctorDto doctorDto) {
        return new Doctor(doctorDto.getName(), doctorDto.getLastName(), doctorDto.getPosition());
    }

    public DoctorDto mapToNewDoctorDto(final Doctor doctor) {
        return new DoctorDto(doctor.getId(), doctor.getName(), doctor.getLastName(), doctor.getPosition());
    }

    public DoctorDto mapToDoctorDto(final Doctor doctor) {
        List<Long> listOfTimeFrames;
        if (doctor.getTimeFrames() != null) {
            listOfTimeFrames = doctor.getTimeFrames().stream().map(TimeFrame::getId).collect(Collectors.toList());
        } else {
            listOfTimeFrames = new ArrayList<>();
        }
        List<Long> listOfAppIds;
        if (doctor.getAppointments() != null) {
            listOfAppIds = doctor.getAppointments().stream().map(Appointment::getId).collect(Collectors.toList());
        } else {
            listOfAppIds = new ArrayList<>();
        }
        List<Long> listOfServiceIds;
        if (doctor.getMedicalServices() != null) {
            listOfServiceIds = doctor
                    .getMedicalServices()
                    .stream()
                    .map(MedicalService::getId)
                    .collect(Collectors.toList());
        } else  listOfServiceIds = new ArrayList<>();
        return new DoctorDto(doctor.getId(), doctor.getName(), doctor.getLastName(), doctor.getPosition(),
                listOfTimeFrames, listOfAppIds, listOfServiceIds);
    }

    public List<DoctorDto> mapToDoctorDtoList(final List<Doctor> doctorList) {
        return doctorList.stream().map(this::mapToDoctorDto).collect(Collectors.toList());
    }
}
