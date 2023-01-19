package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@AllArgsConstructor
@Getter
public class MedicalServiceDto {

    private Long id;
    private String serviceName;
    private String description;
    private List<Long> doctorIds;

    public MedicalServiceDto(String serviceName, String description) {
        this.serviceName = serviceName;
        this.description = description;
    }
}
