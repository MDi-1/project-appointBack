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

    @PostMapping("/autoCreateTfList")
    public List<TimeFrameDto> autoCreateTimeFrames() {
        //at the end of this f. preparation newly created objects has to be written to DB
        LocalDate today = LocalDate.now();
        List<Doctor> doctorList = docRepository.findAll();
        for(Doctor doc : doctorList) {
            List<TimeFrame> tfList = doc.getTimeFrames();
            TimeFrame current = null;
            boolean found = false;
            for(TimeFrame singleTF : tfList) {
                if(today.equals(singleTF.getTimeframeDate())) {
                    current = singleTF;
                    found = true;
                }
            }
            if(!found) {
                current = new TimeFrame(today, LocalTime.of(8, 0), LocalTime.of(16, 0), doc);
                System.out.println("---- sout ---- create TF for today: " + today);
                tfList.add(current);//index of this item is likely to be wrong
            }
            int todaysIndex = tfList.indexOf(current);
            for (int n = 0; n < 31; n++) {
                LocalDate date2check = today.plusDays(n);
                LocalDate dateOfExaminedTf = tfList.get(todaysIndex + n).getTimeframeDate(); // Optional here
                if(date2check.equals(dateOfExaminedTf)) {
                    System.out.println("---- sout ---- TF for date " + date2check + " does exist.");
                }
                else System.out.println("---- sout ---- simulate - create TF for day: " + date2check);
                //create TF for that day.
            }
        } return Collections.emptyList();
    }
}
