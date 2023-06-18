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

    public enum TfStatus { Present, Day_Off, Holiday }

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

    @Enumerated(EnumType.STRING)
    private TfStatus tfStatus;

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID")
    private CalendarHolder doctor;

    public TimeFrame(LocalDate tfDate, LocalTime timeStart, LocalTime timeEnd, TfStatus tfStatus, Doctor doctor) {
        this.timeframeDate = tfDate;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.tfStatus = tfStatus;
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

    @Override
    public String toString() {
        return "TimeFrame{" + "id=" + id + ", timeframeDate=" + timeframeDate +
                ", timeStart=" + timeStart + ", timeEnd=" + timeEnd +
                ", tfStatus='" + tfStatus + '\'' + ", doctor= don't show" + '}';
    }
}
