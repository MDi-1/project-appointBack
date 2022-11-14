package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppointmentDto {

    private Long id;
    private String startDateTime;
    private Integer price;
    private int doctorId;
    private int patientId;

    @Override
    public String toString() {
        return "AppointmentDto{" + "id=" + id + ", startDateTime='" + startDateTime + '\'' + ", price=" + price +
                ", doctorId=" + doctorId + ", patientId=" + patientId + '}';
    }
}
