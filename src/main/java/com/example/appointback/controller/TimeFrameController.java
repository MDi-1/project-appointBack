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

    @GetMapping("/{timeFrameId}")
    public TimeFrameDto getTimeFrame(@PathVariable Long timeFrameId) {
        return null; // fixme
    }

    @GetMapping("/getAll")
    public List<TimeFrameDto> getTimeFrames() {
        return null; // fixme
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TimeFrameDto createTimeFrame(@RequestBody TimeFrameDto dto) {
        return null; // fixme
    }

    @PutMapping("/{timeFrameId}")
    public TimeFrameDto updateTimeFrame(@PathVariable Long timeFrameId, @RequestBody TimeFrameDto dto) {
        return null; // fixme
    }

    @DeleteMapping("/{timeFrameId}")
    public void deleteTimeFrame(@PathVariable Long timeFrameId) {
    }

}
