package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@AllArgsConstructor
@Getter
public class DoctorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private Long timeFrameId;
    private List<Long> appointmentIds;
    private List<Long> medServiceIds;

    public DoctorDto(Long id, String firstName, String lastName, String position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
    }
}
