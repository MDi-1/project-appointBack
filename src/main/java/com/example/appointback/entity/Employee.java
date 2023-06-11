package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "EMPLOYEES")
@DiscriminatorValue("Emplo")
public class Employee extends CalendarHolder {//this class applies for all non doctor employees

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "GO_CALENDAR_SYNC")
    private boolean goCalendarSync;

    @OneToMany(targetEntity = TimeFrame.class, mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TimeFrame> timeFrames = new ArrayList<>();

    public Employee(String name, String lastName, Position position, boolean goCalendarSync) {
        this.name = name;
        this.lastName = lastName;
        this.position = position;
        this.goCalendarSync = goCalendarSync;
    }

    public Employee(Long id, String name, String lastName, Position position, boolean goCalendarSync,
                    List<Appointment> appointments, List<TimeFrame> timeFrames) {
        super(id, name, position, appointments);
        this.lastName = lastName;
        this.position = position;
        this.goCalendarSync = goCalendarSync;
        this.timeFrames = timeFrames;
    }
}
