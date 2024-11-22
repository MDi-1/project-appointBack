package com.example.appointback.entity;

import com.example.appointback.entityfactory.CalHolderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmployeeDto implements CalHolderDto {

    private Long id;
    private String name;
    private String lastName;
    private CalendarHolder.Position position;
    private boolean goCalendarSync;
    private List<Long> appointmentIds;
    private List<Long> timeFrameIds;

    public EmployeeDto(Long id,String name,String lastName, CalendarHolder.Position position, boolean goCalendarSync) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.position = position;
        this.goCalendarSync = goCalendarSync;
    }
}
