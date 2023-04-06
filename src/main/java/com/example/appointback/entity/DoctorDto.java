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
    private String name;
    private String lastName;
    private String position;
    private boolean goCalendarSync;
    private List<Long> timeFrameIds;
    private List<Long> appointmentIds;
    private List<Long> medServiceIds;

    public DoctorDto(Long id, String name, String lastName, String position) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.position = position;
    }

    @Override
    public String toString() {
        return "DoctorDto{" + "id=" + id + ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' + ", position='" + position +
                '\'' + ", timeFrameIds=" + timeFrameIds +
                ", appointmentIds=" + appointmentIds +
                ", medServiceIds=" + medServiceIds + '}';
    }
}
