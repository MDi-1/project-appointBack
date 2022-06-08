package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    @NotNull
    @Column(name = "T_DATE")
    private LocalDate timeframeDate;

    @NotNull
    @Column(name = "TIME_START")
    private LocalTime timeStart;

    @NotNull
    @Column(name = "TIME_END")
    private LocalTime timeEnd;

    @JoinColumn(name = "DOCTOR_ID")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //fixme
    private Doctor doctor;                                          //fixme

    public TimeFrame(Doctor doctor) {
        this.doctor = doctor;
    }
}
