package com.example.appointback.controller;

import com.example.appointback.entity.Doctor;
import com.example.appointback.entity.MedicalService;
import com.example.appointback.entity.MedicalServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedServiceMapper {

    @Autowired
    private DoctorRepository doctorRepository;

    public MedicalService mapToMedService(MedicalServiceDto dto) {
        List<Doctor> list = new ArrayList<>();
        for (Long id : dto.getDoctorIds()) {
            Doctor d = doctorRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            list.add(d);
        }
        return new MedicalService(dto.getId(), dto.getServiceName(), dto.getDescription(), list);
    }

    public MedicalServiceDto mapToMedServiceDto(final MedicalService service) {
        return new MedicalServiceDto(service.getId(), service.getServiceName() ,service.getDescription(),
                service.getDoctors().stream().map(Doctor::getId).collect(Collectors.toList()));
    }

    public List<MedicalServiceDto> mapToMedServiceDtoList(final List<MedicalService> medicalServices) {
        return medicalServices.stream().map(this::mapToMedServiceDto).collect(Collectors.toList());
    }
}
