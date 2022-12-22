package com.example.appointback.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CALENDAR_TYPE")
public abstract class CalendarHolder {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private int id;

    @NotNull
    @Column(name = "NAME")
    private String name;
}
