package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.Scheduler;
import com.example.appointback.entity.SchedulerDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SchedulerMapper {

    public Scheduler mapToScheduler(final SchedulerDto schedulerDto) {
        return new Scheduler(schedulerDto.getName());
    }

    public SchedulerDto mapToSchedulerNewDto(final Scheduler scheduler) {
        return new SchedulerDto(scheduler.getId(), scheduler.getName());
    }

    public SchedulerDto mapToSchedulerDto(final Scheduler scheduler) {
        List<Long> listOfAppIds;
        if(scheduler.getAppointments() != null) {
            listOfAppIds = scheduler.getAppointments().stream().map(Appointment::getId).collect(Collectors.toList());
        } else listOfAppIds = new ArrayList<>();
        return new SchedulerDto(scheduler.getId(), scheduler.getName(), listOfAppIds);
    }

    public List<SchedulerDto> mapToSchedulerDtoList(final List<Scheduler> schedulerList) {
        return schedulerList.stream().map(this::mapToSchedulerDto).collect(Collectors.toList());
    }
}
