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

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "ID", unique = true)
    Long id;

    @NotNull
    @Column(name = "NAME")
    String name;

    @OneToMany(mappedBy = "doctor",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    List<Appointment> appointments = new ArrayList<>();

    @Override
    public String toString() {
        return "CalendarHolder{" + "id=" + id + ", name='" + name + '\'' + ", appointments=" + appointments + '}';
    }
}
