package com.example.appointback.controller;

import com.example.appointback.entity.Doctor;
import com.example.appointback.entity.MedicalService;
import com.example.appointback.entity.MedicalServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedServiceMapper {

    @Autowired
    private DoctorRepository doctorRepository;

    public MedicalService mapToMedService(MedicalServiceDto dto) {
        return new MedicalService(dto.getId(), dto.getServiceName(), dto.getDescription(), dto.getPrice(),
                Optional.ofNullable(dto.getDoctorIds()).orElseGet(Collections::emptyList).stream()
                        .map(id -> doctorRepository.findById(id).orElseThrow(IllegalArgumentException::new))
                        .collect(Collectors.toList()));
    }

    public MedicalServiceDto mapToMedServiceDto(final MedicalService service) {
        return new MedicalServiceDto(service.getId(), service.getServiceName() ,service.getDescription(),
                service.getPrice(), service.getDoctors().stream().map(Doctor::getId).collect(Collectors.toList()));
    }

    public List<MedicalServiceDto> mapToMedServiceDtoList(final List<MedicalService> medicalServices) {
        return medicalServices.stream().map(this::mapToMedServiceDto).collect(Collectors.toList());
    }
}
