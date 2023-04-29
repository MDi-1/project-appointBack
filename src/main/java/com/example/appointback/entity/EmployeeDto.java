package com.example.appointback.entity;

import com.example.appointback.entityfactory.FactoryDtoOutput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmployeeDto implements FactoryDtoOutput {

    private Long id;
    private String name;
    private String lastName;
    private String position;
    private boolean goCalendarSync;
    private List<Long> appointmentIds;
    private List<Long> timeFrameIds;

    public EmployeeDto(Long id, String name, String lastName, String position, boolean goCalendarSync) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.position = position;
        this.goCalendarSync = goCalendarSync;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" + "id=" + id + ", name='" + name + '\'' + ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' + ", goCalendarSync=" + goCalendarSync +
                ", timeFrameIds=" + timeFrameIds + ", appointmentIds=" + appointmentIds + '}';
    }
}
