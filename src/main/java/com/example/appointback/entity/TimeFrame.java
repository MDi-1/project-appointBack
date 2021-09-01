package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
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
    private LocalDateTime monEnd;

    @Column(name = "TUE_START")
    private LocalDateTime tueStart;

    @Column(name = "TUE_DURATION")
    private LocalDateTime tueEnd;

    @Column(name = "WED_START")
    private LocalDateTime wedStart;

    @Column(name = "WED_DURATION")
    private LocalDateTime wedEnd;

    @Column(name = "THU_START")
    private LocalDateTime thuStart;

    @Column(name = "THU_DURATION")
    private LocalDateTime thuEnd;

    @Column(name = "FRI_START")
    private LocalDateTime friStart;

    @Column(name = "FRI_DURATION")
    private LocalDateTime friEnd;

    @JoinColumn(name = "DOCTOR_ID")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Doctor doctor;

    public TimeFrame(Doctor doctor) {
        this.doctor = doctor;
    }
}
