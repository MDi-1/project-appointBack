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
@DiscriminatorValue("Doc")
public class Doctor extends CalendarHolder{

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "POSITION")
    private String position;

    @OneToMany(targetEntity = TimeFrame.class, mappedBy = "doctor",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TimeFrame> timeFrames = new ArrayList<>();

    @OneToMany(mappedBy = "doctor",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<MedicalService> medicalServices = new ArrayList<>();

    public Doctor(String name, String lastName, String position) {
        this.name = name;
        this.lastName = lastName;
        this.position = position;
    }
}
