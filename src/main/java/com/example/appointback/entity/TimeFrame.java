package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@NamedNativeQuery(
    name = "TimeFrame.findTimeFrameByDoc",
    query = "SELECT * FROM timeframes WHERE doctor_id = :ID ORDER BY t_date" ,
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
    private Long id;

    @NotNull
    @Column(name = "T_DATE")
    private LocalDate timeframeDate;

    @NotNull
    @Column(name = "TIME_START")
    private LocalTime timeStart;

    @NotNull
    @Column(name = "TIME_END")
    private LocalTime timeEnd;

    @NotNull
    @Column(name = "STATUS")
    private String status;

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;

    public TimeFrame(LocalDate timeframeDate, LocalTime timeStart,
                     LocalTime timeEnd, String status, Doctor doctor) {
        this.timeframeDate = timeframeDate;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.status = status;
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeFrame timeFrame = (TimeFrame) o;
        return Objects.equals(timeframeDate, timeFrame.timeframeDate) &&
                Objects.equals(doctor, timeFrame.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeframeDate, doctor);
    }
}
