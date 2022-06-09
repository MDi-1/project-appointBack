package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MedicalServiceDto {

    private short id;
    private String description;
    private Long doctorId;
}
