package com.example.appointback.controller;

import com.example.appointback.entity.CalendarHolder;
import com.example.appointback.entity.TimeFrame;
import com.example.appointback.entity.TimeFrameDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeFrameMapper {

    private DoctorRepository doctorRepository;
    private EmployeeRepository employeeRepository;

    public TimeFrame mapToTimeFrame(final TimeFrameDto timeFrameDto) {
        CalendarHolder calendarHolder = doctorRepository.findById(timeFrameDto.getOwnersId()).orElse(null);
        if (calendarHolder == null) {
            calendarHolder = employeeRepository.findById(timeFrameDto.getOwnersId()).orElseThrow(IllegalArgumentException::new);
        }




        return new TimeFrame(
                timeFrameDto.getId(),
                LocalDate.parse(timeFrameDto.getTimeFrameDate()),
                LocalTime.parse(timeFrameDto.getTimeStart(), DateTimeFormatter.ofPattern("H:mm")),
                LocalTime.parse(timeFrameDto.getTimeEnd(), DateTimeFormatter.ofPattern("H:mm")),
                timeFrameDto.getTfStatus(),
                calendarHolder
        );
    }

    public TimeFrameDto mapToTimeFrameDto(final TimeFrame timeFrame) {
        return new TimeFrameDto(
                timeFrame.getId(),
                timeFrame.getTimeframeDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                timeFrame.getTimeStart().format(DateTimeFormatter.ofPattern("HH:mm")),
                timeFrame.getTimeEnd().format(DateTimeFormatter.ofPattern("HH:mm")),
                timeFrame.getTfStatus(),
                timeFrame.getDoctor().getId()
        );
    }

    public List<TimeFrameDto> mapToTimeFrameDtoList(final List<TimeFrame> timeFrameList) {
        return timeFrameList.stream().map(this::mapToTimeFrameDto).collect(Collectors.toList());
    }
}
