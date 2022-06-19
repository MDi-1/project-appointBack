package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OccurrenceDto {

    private String startDateTime;
    private Long doctorId;
    private Long patientId;
}
