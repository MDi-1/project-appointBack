package com.example.appointback.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @Column(name = "GO_CALENDAR_SYNC")
    private boolean goCalendarSync;

    @OneToMany(targetEntity = TimeFrame.class, mappedBy = "doctor",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TimeFrame> timeFrames = new ArrayList<>();

    @ManyToMany(targetEntity = com.example.appointback.entity.MedicalService.class,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable(name = "JOIN_DOC_SERVICE",
            joinColumns = {@JoinColumn(name = "DOCTOR_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "SERVICE_ID", referencedColumnName = "S_ID")})
    private List<MedicalService> medicalServices;

    public Doctor(String name, String lastName, String position) {
        this.name = name;
        this.lastName = lastName;
        this.position = position;
    }

    public Doctor(Long id, @NotNull String name, String lastName, String position, boolean goCalendarSync,
                  List<Appointment> appointments, List<TimeFrame> timeFrames, List<MedicalService> medicalServices) {
        super(id, name, appointments);
        this.lastName = lastName;
        this.position = position;
        this.goCalendarSync = goCalendarSync;
        this.timeFrames = timeFrames;
        this.medicalServices = medicalServices;
    }

    @Override
    public String toString() {
        int appListSize, msListSize;
        if (appointments == null) appListSize = -1;
        else appListSize = appointments.size();
        if (medicalServices == null) msListSize = -1;
        else msListSize = medicalServices.size();
        return "Doctor{" + "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", goCalendarSync='" + goCalendarSync + '\'' +
                ", appList size= " + appListSize +
                ", timeFrames=" + timeFrames +
                ", msList size= " + msListSize +
                //", medicalServices=" + medicalServices +    // <== don't print this!
                // printing list of medicalServices will result in back and forth references doctor to medicalService,
                // then to doctor, then to medicalService, ...and eventually we get StackOverflowError; thus be careful
                // when using toString() in @ManyToMany relationship.
                '}';
    }
}
