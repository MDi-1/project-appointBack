package com.example.appointback.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TIMEFRAMES")
public class TimeFrame {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "MON_START")
    SimpleDateFormat mon_start;

    @Column(name = "MON_END")
    SimpleDateFormat mon_end;

    @Column(name = "TUE_START")
    SimpleDateFormat tue_start;

    @Column(name = "TUE_END")
    SimpleDateFormat tue_end;

    @Column(name = "WED_START")
    SimpleDateFormat wed_start;

    @Column(name = "WED_END")
    SimpleDateFormat wed_end;

    @Column(name = "THU_START")
    SimpleDateFormat thu_start;

    @Column(name = "THU_END")
    SimpleDateFormat thu_end;

    @Column(name = "FRI_START")
    SimpleDateFormat fri_start;

    @Column(name = "FRI_END")
    SimpleDateFormat fri_end;

    @JoinColumn(name = "ID")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Doctor doctor;
}
