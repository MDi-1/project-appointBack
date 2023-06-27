package com.example.appointback.entity;

import com.example.appointback.entityfactory.FactoryDtoOutput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DoctorDto implements FactoryDtoOutput {

    private Long id;
    private String name;
    private String lastName;
    private CalendarHolder.Position position;
    private boolean goCalendarSync;
    private List<Long> timeFrameIds;
    private List<Long> appointmentIds;
    private List<Long> medServiceIds;

    public DoctorDto(Long id, String name, String lastName, CalendarHolder.Position position, boolean goCalendarSync) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.position = position;
        this.goCalendarSync = goCalendarSync;
    }
}
