package com.example.appointback.controller;

import com.example.appointback.entity.TimeFrame;
import com.example.appointback.entity.TimeFrameDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeFrameMapper {

    private DoctorRepository doctorRepository;

    public TimeFrame mapToTimeFrame(final TimeFrameDto dto) {
        return new TimeFrame(dto.getId(),

                doctorRepository.findById(dto.getDoctorId()).orElseThrow(IllegalArgumentException::new));
    }

    public TimeFrameDto mapToTimeFrameDto(final TimeFrame time) {
        return new TimeFrameDto(time.getId(),

                time.getDoctor().getId());
    }

    public List<TimeFrameDto> mapToTimeFrameDtoList(final List<TimeFrame> timeFrameList) {
        return timeFrameList.stream().map(this::mapToTimeFrameDto).collect(Collectors.toList());
    }
}
