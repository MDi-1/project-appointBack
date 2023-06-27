package com.example.appointback.controller;

import com.example.appointback.entity.MedicalService;
import com.example.appointback.entity.MedicalServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/medService")
@RequiredArgsConstructor
public class MedServiceController {

    private final MedServiceMapper mapper;
    private final MedServiceRepository repository;
    private final DoctorRepository doctorRepository;

    @GetMapping("/{serviceId}")
    public MedicalServiceDto getMedService(@PathVariable Long serviceId) {
        return mapper.mapToMedServiceDto(repository.findById(serviceId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getAll")
    public List<MedicalServiceDto> getMedServices() {
        return mapper.mapToMedServiceDtoList(repository.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public MedicalServiceDto createMedService(@RequestBody MedicalServiceDto dto) {
        return mapper.mapToMedServiceDto(repository.save(mapper.mapToMedService(dto)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public MedicalServiceDto updateMedService(@RequestBody MedicalServiceDto dto) {
        return mapper.mapToMedServiceDto(repository.save(mapper.mapToMedService(dto)));
    }

    @DeleteMapping("/{serviceId}")
    public void deleteMedService(@PathVariable Long serviceId) {
        MedicalService msToDelete = repository.findById(serviceId).orElseThrow(IllegalArgumentException::new);
        doctorRepository.findAll().forEach(doctor -> {
            List<MedicalService> msList = doctor.getMedicalServices().stream()
                    .filter(ms -> ms != msToDelete).collect(Collectors.toList());
            doctor.setMedicalServices(msList);
            doctorRepository.save(doctor);
        });
        repository.delete(msToDelete);
    }
}
