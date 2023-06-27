package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class AppointmentDto {

    private Long id;
    private String startDateTime;
    private Integer price;
    private Long ownersId;
    private Long patientId;
}
