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

    @Column(name = "PRICE")
    private int price;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;

    public Appointment(LocalDateTime startDate, int price, Doctor doctor, Patient patient) {
        this.startDate = startDate;
        this.price = price;
        this.doctor = doctor;
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Appointment{" + "id= " + id + ", startDate= " + startDate + ", price= " + price + '}';
    }
}
