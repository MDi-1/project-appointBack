package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.util.Objects;

@AllArgsConstructor
@Getter
@ToString
public class AppointmentDto {

    private Long id;
    private String startDateTime;
    private int price;
    private Long medicalServiceId;
    private Long ownersId;
    private Long patientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentDto that = (AppointmentDto) o;
        return Objects.equals(startDateTime, that.startDateTime) && Objects.equals(ownersId, that.ownersId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, ownersId);
    }
}
