package com.example.appointback.entity;

import com.example.appointback.entityfactory.FactoryDtoOutput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class DoctorDto implements FactoryDtoOutput {

    private Long id;
    private String name;
    private String lastName;
    private CalendarHolder.Position position;
    private boolean goCalendarSync;
    private List<Long> timeFrameIds;
    private List<Long> appointmentIds;
    private List<Long> medServiceIds;

    public DoctorDto(String name, String lastName, CalendarHolder.Position position, boolean goCalendarSync) {
        this.name = name;
        this.lastName = lastName;
        this.position = position;
        this.goCalendarSync = goCalendarSync;
    }

    public DoctorDto(String name, String lastName, List<Long> medServiceIds) {
        this.name = name;
        this.lastName = lastName;
        this.position = Arrays.stream(CalendarHolder.Position.values()).findFirst().orElse(null);
        this.goCalendarSync = false;
        this.medServiceIds = medServiceIds;
    }

    public void setTimeFrameIds(List<Long> timeFrameIds) {
        this.timeFrameIds = timeFrameIds;
    }

    public void setAppointmentIds(List<Long> appointmentIds) {
        this.appointmentIds = appointmentIds;
    }
}
