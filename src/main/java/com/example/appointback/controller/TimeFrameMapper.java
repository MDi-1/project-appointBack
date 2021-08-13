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
                LocalDateTime.parse(dto.getMonStart()), dto.getMonDuration(),
                LocalDateTime.parse(dto.getTueStart()), dto.getTueDuration(),
                LocalDateTime.parse(dto.getWedStart()), dto.getWedDuration(),
                LocalDateTime.parse(dto.getThuStart()), dto.getThuDuration(),
                LocalDateTime.parse(dto.getFriStart()), dto.getFriDuration(),
                doctorRepository.findById(dto.getDoctorId()).orElseThrow(IllegalArgumentException::new));
    }

    public TimeFrameDto mapToTimeFrameDto(final TimeFrame time) {
        return new TimeFrameDto(time.getId(),
                time.getMonStart().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")), time.getMonDuration(),
                time.getTueStart().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")), time.getTueDuration(),
                time.getWedStart().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")), time.getWedDuration(),
                time.getThuStart().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")), time.getThuDuration(),
                time.getFriStart().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")), time.getFriDuration(),
                time.getDoctor().getId());
    }

    public List<TimeFrameDto> mapToTimeFrameDtoList(final List<TimeFrame> timeFrameList) {
        return timeFrameList.stream().map(this::mapToTimeFrameDto).collect(Collectors.toList());
    }
}
