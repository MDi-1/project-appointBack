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

    private AppointmentRepository appointmentRepository;
    private TimeFrameRepository timeFrameRepository;
    private MedServiceRepository medServiceRepository;

    public Doctor mapToDoctor(DoctorDto dto) {
        List<Appointment> appointments;
        if (dto.getAppointmentIds() != null) {
            appointments = dto.getAppointmentIds().stream().map(aLong -> appointmentRepository.findById(aLong)
                            .orElseThrow(IllegalArgumentException::new)).collect(Collectors.toList());
        } else { appointments = null; }

        List<TimeFrame> timeFrames;
        if (dto.getTimeFrameIds() != null) {
            timeFrames = dto.getTimeFrameIds().stream().map(aLong -> timeFrameRepository.findById(aLong)
                            .orElseThrow(IllegalArgumentException::new)).collect(Collectors.toList());
        } else { timeFrames = null; }

        List<MedicalService> msList;
        if (dto.getMedServiceIds() != null) {
            msList = dto.getMedServiceIds().stream().map(aLong -> medServiceRepository.findById(aLong)
                            .orElseThrow(IllegalArgumentException::new)).collect(Collectors.toList());
        } else { msList = null; }
        return new Doctor(dto.getId(),dto.getName(),dto.getLastName(),dto.getPosition(),appointments,timeFrames,msList);
    }

    public Doctor mapToNewDoctor(final DoctorDto doctorDto) {
        return new Doctor(doctorDto.getName(), doctorDto.getLastName(), doctorDto.getPosition());
    }

    public DoctorDto mapToNewDoctorDto(final Doctor doctor) {
        return new DoctorDto(doctor.getId(), doctor.getName(), doctor.getLastName(), doctor.getPosition());
    }

    public DoctorDto mapToDoctorDto(final Doctor doctor) {

        List<Long> listOfTimeFrames;
        if (doctor.getTimeFrames() != null) {
            listOfTimeFrames = doctor.getTimeFrames().stream().map(TimeFrame::getId).collect(Collectors.toList());
        } else listOfTimeFrames = new ArrayList<>();

        List<Long> listOfAppIds;
        if (doctor.getAppointments() != null) {
            listOfAppIds = doctor.getAppointments().stream().map(Appointment::getId).collect(Collectors.toList());
        } else listOfAppIds = new ArrayList<>();

        List<Long> listOfServIds;
        if (doctor.getMedicalServices() != null) {
            listOfServIds =doctor.getMedicalServices().stream().map(MedicalService::getId).collect(Collectors.toList());
        } else listOfServIds = new ArrayList<>();

        return new DoctorDto(doctor.getId(), doctor.getName(), doctor.getLastName(), doctor.getPosition(),
                listOfTimeFrames, listOfAppIds, listOfServIds);
    }

    public List<DoctorDto> mapToDoctorDtoList(final List<Doctor> doctorList) {
        return doctorList.stream().map(this::mapToDoctorDto).collect(Collectors.toList());
    }
}
