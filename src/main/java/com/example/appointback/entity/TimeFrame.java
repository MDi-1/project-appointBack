package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@NamedNativeQuery(
        name = "TimeFrame.findTimeFrameByDoc",
        query = "SELECT * FROM timeframes WHERE doctor_id = :ID ORDER BY t_date",
        resultClass = TimeFrame.class
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TIMEFRAMES")
public class TimeFrame {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private long id;

    @NotNull
    @Column(name = "T_DATE")
    private LocalDate timeframeDate;

    @NotNull
    @Column(name = "TIME_START")
    private LocalTime timeStart;

    @NotNull
    @Column(name = "TIME_END")
    private LocalTime timeEnd;

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;

    public TimeFrame(LocalDate timeframeDate, LocalTime timeStart, LocalTime timeEnd, Doctor doctor) {
        this.timeframeDate = timeframeDate;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeFrame timeFrame = (TimeFrame) o;
        return Objects.equals(timeframeDate, timeFrame.timeframeDate) && Objects.equals(doctor, timeFrame.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeframeDate, doctor);
    }
}
