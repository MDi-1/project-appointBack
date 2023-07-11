package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MedicalServiceDto {

    private Long id;
    private String serviceName;
    private String description;
    private int price;
    private List<Long> appointmentIds;
    private List<Long> doctorIds;

    public MedicalServiceDto(String serviceName, int price) {
        this.serviceName = serviceName;
        this.price = price;
    }
}
