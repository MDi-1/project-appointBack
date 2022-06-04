package com.example.appointback.controller;

import com.example.appointback.entity.TimeFrame;
import com.example.appointback.entity.TimeFrameDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeFrameMapper {

    private DoctorRepository doctorRepository;

    public TimeFrame mapToTimeFrame(final TimeFrameDto timeFrameDto) {
        return new TimeFrame(timeFrameDto.getId(),
                LocalDate.parse(timeFrameDto.getTimeFrameDate()),
                LocalTime.parse(timeFrameDto.getTimeStart()),
                LocalTime.parse(timeFrameDto.getTimeEnd()),
                doctorRepository.findById(timeFrameDto.getDoctorId()).orElseThrow(IllegalArgumentException::new));
    }

    public TimeFrameDto mapToTimeFrameDto(final TimeFrame timeFrame) {
        return new TimeFrameDto(timeFrame.getId(),
                timeFrame.getTimeframeDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                timeFrame.getTimeStart().format(DateTimeFormatter.ofPattern("hh:mm")),
                timeFrame.getTimeEnd().format(DateTimeFormatter.ofPattern("hh:mm")),
                timeFrame.getDoctor().getId());
    }

    public List<TimeFrameDto> mapToTimeFrameDtoList(final List<TimeFrame> timeFrameList) {
        return timeFrameList.stream().map(this::mapToTimeFrameDto).collect(Collectors.toList());
    }
}
