package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CALENDAR_TYPE")
public abstract class CalendarHolder {

    public enum Position { Specialist, Manager, Board }

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "ID", unique = true)
    Long id;

    @NotNull
    @Column(name = "NAME")
    String name;

    @Enumerated(EnumType.STRING)
    Position position;

    @OneToMany(mappedBy = "doctor",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    List<Appointment> appointments = new ArrayList<>();

    public CalendarHolder(Long id, String name, List<Appointment> appointments) {
        this.id = id;
        this.name = name;
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "CalendarHolder{" + "id=" + id + ", name='" + name + '\'' + ", appointments=" + appointments + '}';
    }
}
