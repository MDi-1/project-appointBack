package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.AppointmentDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppointmentMapper {
    public Appointment mapToAppointment(final AppointmentDto appointmentDto) {
        return new Appointment();
    }
}
