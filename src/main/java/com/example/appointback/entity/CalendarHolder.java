package com.example.appointback.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CALENDAR_TYPE")
public abstract class CalendarHolder {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    int id;

    @NotNull
    @Column(name = "NAME")
    String name;

    @OneToMany(mappedBy = "doctor",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    List<Appointment> appointments = new ArrayList<>();
}
