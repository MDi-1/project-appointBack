package com.example.appointback.controller;

import com.example.appointback.entity.TimeFrameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/timeFrame")
@RequiredArgsConstructor
public class TimeFrameController {

    private final TimeFrameMapper mapper;
    private final TimeFrameRepository repository;

    @GetMapping("/{timeFrameId}")
    public TimeFrameDto getTimeFrame(@PathVariable Long timeFrameId) {
        return mapper.mapToTimeFrameDto(repository.findById(timeFrameId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getAll")
    public List<TimeFrameDto> getTimeFrames() {
        return mapper.mapToTimeFrameDtoList(repository.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TimeFrameDto createTimeFrame(@RequestBody TimeFrameDto dto) {
        return mapper.mapToTimeFrameDto(repository.save(mapper.mapToTimeFrame(dto)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TimeFrameDto updateTimeFrame(@RequestBody TimeFrameDto dto) {
        return mapper.mapToTimeFrameDto(repository.save(mapper.mapToTimeFrame(dto)));
    }

    @DeleteMapping("/{timeFrameId}")
    public void deleteTimeFrame(@PathVariable Long timeFrameId) {
        repository.deleteById(timeFrameId);
    }
}
