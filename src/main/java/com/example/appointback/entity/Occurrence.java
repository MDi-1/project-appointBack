package com.example.appointback.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Occurrence { //list of these objects will populate week timetable in front-end docView
// there is no id for this object - no repo, do write to DB. no medicalService instance field - the doctor responsible
// for particular service will be chosen by the app.
    private LocalDateTime startDateTime;
    private Doctor doctor;
    private Patient patient;
}
