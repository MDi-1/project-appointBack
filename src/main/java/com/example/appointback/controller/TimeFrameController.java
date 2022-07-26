package com.example.appointback.controller;

import com.example.appointback.entity.Doctor;
import com.example.appointback.entity.TimeFrame;
import com.example.appointback.entity.TimeFrameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/timeFrame")
@RequiredArgsConstructor
public class TimeFrameController {

    private final TimeFrameMapper mapper;
    private final TimeFrameRepository repository;
    private final DoctorRepository docRepository;

    @GetMapping("/getOne/{timeFrameId}")
    public TimeFrameDto getTimeFrame(@PathVariable Long timeFrameId) {
        return mapper.mapToTimeFrameDto(repository.findById(timeFrameId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/getAll")
    public List<TimeFrameDto> getTimeFrames() {
        return mapper.mapToTimeFrameDtoList(repository.findAll());
    }

    @GetMapping("/byDoc/{docId}")
    public List<TimeFrameDto> getTimeFramesByDoctor(@PathVariable int docId) {
        List <TimeFrameDto> list = mapper.mapToTimeFrameDtoList(repository.findTimeFrameByDoc(docId));
        for (TimeFrameDto element: list) {
            System.out.println(element.getTimeFrameDate());
        }
        return list;
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

    @PostMapping("/autoCreateTfList")
    public List<TimeFrameDto> autoCreateTimeFrames() {
        //at the end of this f. preparation newly created objects has to be written to DB
        LocalDate today = LocalDate.now();
        List<Doctor> doctorList = docRepository.findAll();
        for(Doctor doc : doctorList) {
            List<TimeFrame> tfList = doc.getTimeFrames();
            for(int n = 0; n < 30; n++) {
            }
        } return Collections.emptyList();
    }
}
