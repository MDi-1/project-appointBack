package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DoctorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private List<Long> timeFrameIds;
    private List<Long> appointmentIds;
    private List<Integer> medServiceIds;

    public DoctorDto(Long id, String firstName, String lastName, String position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
    }
}
