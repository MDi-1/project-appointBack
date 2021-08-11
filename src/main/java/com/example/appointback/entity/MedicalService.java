package com.example.appointback.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SERVICES")
public class MedicalService {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    Long id;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ID")
    private Doctor doctor;

    public MedicalService(String description, Doctor doctor) {
        this.description = description;
        this.doctor = doctor;
    }
}
