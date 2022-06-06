package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "APPOINTMENTS")
public class Appointment {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    Long id;

    @NotNull
    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "DURATION")
    private long duration; // użyć później plusMinutes(Long minutes)


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "DOCTOR_ID")
    @NotNull
    private Doctor doctor;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PATIENT_ID")
    @NotNull
    private Patient patient;

    public Appointment(LocalDateTime startDate, long duration, Doctor doctor, Patient patient) {
        this.startDate = startDate;
        this.duration = duration;
        this.doctor = doctor;
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Appointment{" + "id= " + id + ", startDate= " + startDate + ", duration= " + duration + '}';
    }
}
