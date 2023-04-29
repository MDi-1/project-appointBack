package com.example.appointback.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity(name = "SCHEDULERS")
@DiscriminatorValue("Sched")
public class Scheduler extends CalendarHolder {

    public Scheduler(String name) {
        this.name = name;
    }

    public Scheduler(Long id, @NotNull String name, List<Appointment> appointments) {
        super(id, name, appointments);
    }

    @Override
    public String toString() {
        return "Scheduler{" + "id=" + id + ", name='" + name + '\'' + ", appointments=" + appointments + '}';
    }
}
