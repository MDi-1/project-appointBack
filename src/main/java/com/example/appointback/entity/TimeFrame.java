package com.example.appointback.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TIMEFRAMES")
public class TimeFrame {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    // zamiast tych wszystkich pól zrobić tablicę / fixme
    @Column(name = "MON_START")
    private LocalDateTime monStart;

    @Column(name = "MON_DURATION")
    private Long monDuration;

    @Column(name = "TUE_START")
    private LocalDateTime tueStart;

    @Column(name = "TUE_DURATION")
    private Long tueDuration;

    @Column(name = "WED_START")
    private LocalDateTime wedStart;

    @Column(name = "WED_DURATION")
    private Long wedDuration;

    @Column(name = "THU_START")
    private LocalDateTime thuStart;

    @Column(name = "THU_DURATION")
    private Long thuDuration;

    @Column(name = "FRI_START")
    private LocalDateTime friStart;

    @Column(name = "FRI_DURATION")
    private Long friDuration;

    @JoinColumn(name = "DOCTOR_ID")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Doctor doctor;

    public TimeFrame(Doctor doctor) {
        this.doctor = doctor;
    }
}
