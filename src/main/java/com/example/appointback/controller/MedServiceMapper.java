package com.example.appointback.controller;

import com.example.appointback.entity.MedicalService;
import com.example.appointback.entity.MedicalServiceDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedServiceMapper {

    private final DoctorRepository doctorRepository;

    public MedicalService mapToMedService(final MedicalServiceDto dto) {
        return new MedicalService(dto.getId(), dto.getDescription(),
                doctorRepository.findById(dto.getDoctorId()).orElseThrow(IllegalArgumentException::new));
    }

    public MedicalServiceDto mapToMedServiceDto(final MedicalService service) {
        return new MedicalServiceDto(service.getId(), service.getDescription(), service.getDoctor().getId());
    }

    public List<MedicalServiceDto> mapToMedServiceDtoList(final List<MedicalService> medicalServices) {
        return medicalServices.stream().map(this::mapToMedServiceDto).collect(Collectors.toList());
    }
}
