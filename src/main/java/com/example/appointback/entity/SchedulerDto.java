package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SchedulerDto {

    private int id;
    private String name;
    private List<Long> appointmentsIds;

    public SchedulerDto(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
