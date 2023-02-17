package com.example.appointback.controller;

import com.example.appointback.entity.SchedulerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/scheduler")
@RequiredArgsConstructor
public class SchedulerController {

    private final SchedulerMapper mapper;
    private final SchedulerRepository repository;

    @GetMapping("/{schedulerId}")
    public SchedulerDto getScheduler(@PathVariable Long schedulerId) throws IllegalArgumentException {
        return mapper.mapToSchedulerDto(repository.findById(schedulerId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getOne/{name}")
    public SchedulerDto getSchedulerByName(@PathVariable String name) {
        return mapper.mapToSchedulerDto(repository.findByName(name));
    }

    @GetMapping("/getAll")
    public List<SchedulerDto> getSchedulers() {
        return mapper.mapToSchedulerDtoList(repository.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public SchedulerDto createScheduler(@RequestBody SchedulerDto dto) {
        return mapper.mapToSchedulerNewDto(repository.save(mapper.mapToScheduler(dto)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public SchedulerDto updateScheduler(@RequestBody SchedulerDto dto) {
        return mapper.mapToSchedulerDto(repository.save(mapper.mapToScheduler(dto)));
    }

    @DeleteMapping("/{schedulerId}")
    public void deleteScheduler(@PathVariable Long schedulerId) {
        repository.deleteById(schedulerId);
    }
}
