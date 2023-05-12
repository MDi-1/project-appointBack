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
    private Integer price; // pricing functionality is in fact not developed yet fixme
    private Long ownersId;
    private Long patientId;

    @Override
    public String toString() {
        return " AppointmentDto= (" + " id=" + id + ", startDateTime='" +
                startDateTime + '\'' + ", price=" + price + ", doctorId=" +
                ownersId + ", patientId=" + patientId + ')';
    }
}
