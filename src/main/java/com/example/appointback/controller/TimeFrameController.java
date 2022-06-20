package com.example.appointback.controller;

import com.example.appointback.entity.Doctor;
import com.example.appointback.entity.TimeFrame;
import com.example.appointback.entity.TimeFrameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    public List<TimeFrameDto> autoCreateTimeFrames() {
        LocalDate today = LocalDate.now();

        List<Doctor> doctorList = docRepository.findAll();
        for(Doctor doc : doctorList) {
            List<TimeFrame> tfList = doc.getTimeFrames();
            TimeFrame current = null;
            for(TimeFrame singleTF : tfList) {
                if(today.equals(singleTF.getTimeframeDate())) {
                    current = singleTF;
                }
            }
            int todaysIndex = tfList.indexOf(current);
            for (int n = 1; n < 32; n++) {
                // TimeFrame singleTF = tfList.get(i);
                TimeFrame next = tfList.get(todaysIndex);


                assert current != null;
                if(today.plusDays(n).equals(next)) {
                    //create tf for that day.
                }
            }
        }
        return null;
    }
}
