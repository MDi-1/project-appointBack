package com.example.appointback.controller;

import com.example.appointback.entity.MedicalService;
import com.example.appointback.entity.MedicalServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/medService")
@RequiredArgsConstructor
public class MedServiceController {

    private final MedServiceMapper mapper;
    private final MedServiceRepository repository;

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
        System.out.println("  data  " + dto);
        MedicalService ms = mapper.mapToMedService(dto);
        System.out.println("  data  " + ms);
        MedicalService msReturn = repository.save(ms);
        System.out.println("  data  " + msReturn);
        MedicalServiceDto dtoReturn = mapper.mapToMedServiceDto(msReturn);
        System.out.println("  data  " + dtoReturn);
        return dtoReturn;
        //return mapper.mapToMedServiceDto(repository.save(mapper.mapToMedService(dto)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public MedicalServiceDto updateMedService(@RequestBody MedicalServiceDto dto) {
        return mapper.mapToMedServiceDto(repository.save(mapper.mapToMedService(dto)));
    }

    @DeleteMapping("/{serviceId}")
    public void deleteMedService(@PathVariable Long serviceId) {
        repository.deleteById(serviceId);
    }
}
