package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppointmentDto {

    private Long id;
    private String startDate;
    private Long duration;
    private Long doctorId;
    private Long patientId;
}
