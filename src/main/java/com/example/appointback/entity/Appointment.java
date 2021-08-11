package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private long duration; // użyć później plusMinutes(long minutes)

    @ManyToOne
    @JoinColumn(name = "ID")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "ID")
    private Patient patient;
}
