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
} // Pomysł na zastosowanie wzorca Factory: Ten wzorzec można spróbować zastosować tylko na obiektach typu DTO dla
// Doctor, Scheduler i jakiś jeszcze (który dziedziczy po CalendarHolder; trzeba wymyślić jakiś). Trzeba będzie zrobić
// dla nich jeden Controller, który w switch-u będzie tworzył różne obiekty dziedziczące po CalendarHolder
