package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SERVICES")
public class MedicalService {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private short id;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;

    public MedicalService(String description, Doctor doctor) {
        this.description = description;
        this.doctor = doctor;
    }
}
