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
@Entity(name = "SCHEDULERS")
public class Scheduler {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private int id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "doctor",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    public Scheduler(String name) {
        this.name = name;
    }
}
