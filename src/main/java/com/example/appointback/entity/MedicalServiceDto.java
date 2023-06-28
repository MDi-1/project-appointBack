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
    private int price;
    private List<Long> doctorIds;
}
