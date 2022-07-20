package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MedicalServiceDto {

    private int id;
    private String description;
    private Long doctorId;
}
