package com.example.appointback.controller;

import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.AppointmentDto;
import com.example.appointback.entity.TimeFrame;
import com.example.appointback.entity.TimeFrameDto;
import com.example.appointback.misc.DuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/service")
@RequiredArgsConstructor
public class MaintenanceController {

    private final AppointmentRepository appRepository;
    private final TimeFrameRepository tfRepository;
    private final AppointmentMapper appMapper;
    private final TimeFrameMapper tfMapper;

    @GetMapping("/dDbCheck") // abbreviation: "duplicate database check"
    public void dDbCheck() throws DuplicateException {
        try  {
            countDup();
        } catch (Exception e) {
            if (countDup() > 0) {
                throw new DuplicateException();
            }
        }
    }

    private int countDup() {
        Set<Appointment> appCheckSet = new HashSet<>();
        List<AppointmentDto> appExcessList = new ArrayList<>();
        for (Appointment appointment : appRepository.findAll()) {
            if (!appCheckSet.add(appointment)) appExcessList.add(appMapper.mapToAppointmentDto(appointment));
        }
        Set<TimeFrame> tfCheckSet = new HashSet<>();
        List<TimeFrameDto> tfExcessList = new ArrayList<>();
        for (TimeFrame timeFrame : tfRepository.findAll()) {
            if (!tfCheckSet.add(timeFrame)) tfExcessList.add(tfMapper.mapToTimeFrameDto(timeFrame));
        }
        for (AppointmentDto appItem : appExcessList) System.out.println(appItem);
        for (TimeFrameDto tfItem : tfExcessList) System.out.println(tfItem);
        int excessItems = appExcessList.size() + tfExcessList.size();
        System.out.println(">> returned number of excess items: " + excessItems);
        return excessItems;
    }

}
