package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.Doctor;
import com.example.appointback.entity.DoctorDto;
import com.example.appointback.entity.MedicalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Long id;
        List<Long> listOfAppIds;
        List<Long> listOfServiceIds;
        if(doctor.getTimeFrame() == null) id = 0L; else id = doctor.getTimeFrame().getId();
        if(doctor.getAppointments() != null) {
            listOfAppIds = doctor.getAppointments().stream().map(Appointment::getId).collect(Collectors.toList());
        } else listOfAppIds = new ArrayList<>();
        if(doctor.getMedicalServices() != null) {
            listOfServiceIds = doctor.getMedicalServices()
                    .stream()
                    .map(MedicalService::getId)
                    .collect(Collectors.toList());
        } else  listOfServiceIds = new ArrayList<>();

        return new DoctorDto(doctor.getId(), doctor.getFirstName(), doctor.getLastName(), doctor.getPosition(),
                id, listOfAppIds, listOfServiceIds);
    }

    public List<DoctorDto> mapToDoctorDtoList(final List<Doctor> doctorList) {
        return doctorList.stream().map(this::mapToDoctorDto).collect(Collectors.toList());
    }
}
