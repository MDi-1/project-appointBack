package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@AllArgsConstructor
@Getter
public class PatientDto {

    private Long id;
    private String firstName;
    private String lastName;
    private List<Long> appointmentsIds;
}
