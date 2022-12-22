package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "APPOINTMENTS")
public class Appointment {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    Long id;

    @NotNull
    @Column(name = "START_DATE")
    private LocalDateTime startDateTime;

    @Column(name = "PRICE")
    private int price;

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;

    public Appointment(LocalDateTime startDateTime, int price, Doctor doctor, Patient patient) {
        this.startDateTime = startDateTime;
        this.price = price;
        this.doctor = doctor;
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(startDateTime, that.startDateTime) && Objects.equals(doctor, that.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, doctor);
    }
}
