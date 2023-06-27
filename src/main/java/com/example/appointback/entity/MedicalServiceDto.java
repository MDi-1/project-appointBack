package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString // temporary fixme
public class MedicalServiceDto {

    private Long id;
    private String serviceName;
    private String description;
    private int price;
    private List<Long> doctorIds;
}
