package com.example.appointback.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "DOCTORS")
public class Doctor {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @JoinColumn(name = "ID")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private TimeFrame timeFrame;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalService> services = new ArrayList<>();
}
