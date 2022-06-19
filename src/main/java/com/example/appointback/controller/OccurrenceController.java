package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.Doctor;
import com.example.appointback.entity.Occurrence;
import com.example.appointback.entity.TimeFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/occurrence")
@RequiredArgsConstructor
public class OccurrenceController {

    private final OccurrenceMapper mapper;
    private final DoctorRepository doctorRepository;

    public Occurrence getOccurrence() {
        return null;
    }

    @GetMapping("/getDoctorOccurrences/{doctorId}")
    public List<Occurrence> getDoctorOccurrences(@PathVariable Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(IllegalArgumentException::new);
        List<TimeFrame> timeFrameList = doctor.getTimeFrames();
        List<Appointment> appointmentList = doctor.getAppointments();


        return null;
    }
}
