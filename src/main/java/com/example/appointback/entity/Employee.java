package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "EMPLOYEES")
@DiscriminatorValue("Emplo")
public class Employee extends CalendarHolder {//this class applies for all non doctor employees

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "GO_CALENDAR_SYNC")
    private boolean goCalendarSync;

    @OneToMany(targetEntity = TimeFrame.class, mappedBy = "doctor",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TimeFrame> timeFrames = new ArrayList<>();

    public Employee(String name, String lastName, String position, boolean goCalendarSync) {
        this.name = name;
        this.lastName = lastName;
        this.position = position;
        this.goCalendarSync = goCalendarSync;
    }
}
