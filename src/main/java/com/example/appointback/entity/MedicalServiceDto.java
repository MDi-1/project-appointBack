package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
public class MedicalServiceDto {

    private Long id;
    private String serviceName;
    private String description;
    private List<Long> doctorIds;
}
