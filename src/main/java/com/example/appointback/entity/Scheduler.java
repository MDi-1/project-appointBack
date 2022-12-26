package com.example.appointback.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SCHEDULERS")
@DiscriminatorValue("Sched")
public class Scheduler extends CalendarHolder {

    public Scheduler(String name) {
        this.name = name;
    }
}
