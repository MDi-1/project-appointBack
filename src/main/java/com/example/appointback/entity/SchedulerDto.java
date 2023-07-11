package com.example.appointback.entity;

import com.example.appointback.entityfactory.FactoryDtoOutput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SchedulerDto implements FactoryDtoOutput {

    private Long id;
    private String name;
    private List<Long> appointmentIds;

    public SchedulerDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
