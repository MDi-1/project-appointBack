package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private CalendarHolder doctor;

    @ManyToOne
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;

    public Appointment (LocalDateTime startDateTime, int price,
                        CalendarHolder doctor, Patient patient) {
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
        return Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(doctor, that.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, doctor);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", startDateTime=" + startDateTime +
                ", price=" + price +
                ", doctor=" + doctor +
                ", patient=" + patient +
                '}';
    }
}
