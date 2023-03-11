package com.example.appointback.controller;

import com.example.appointback.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/timeFrame")
@RequiredArgsConstructor
public class TimeFrameController {

    private final TimeFrameMapper mapper;
    private final AppointmentMapper aMapper;
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
    public List<TimeFrameDto> getTimeFramesByDoctor(@PathVariable Long docId) {
        return mapper.mapToTimeFrameDtoList(repository.findTimeFrameByDoc(docId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TimeFrameDto createTimeFrame(@RequestBody TimeFrameDto dto) {
        if (dto.getTimeStart().equals("-") && dto.getTimeEnd().equals("-")) {
            dto.setTimeStart("08:00");
            dto.setTimeEnd("16:00");
        }
        if (dto.getTimeStart().equals("off") && dto.getTimeEnd().equals("off")) {
            dto.setTimeStart("00:00");
            dto.setTimeEnd("00:00");
            dto.setStatus("Day_Off");
        }
        TimeFrame tf = mapper.mapToTimeFrame(dto);
        checkForAppsOutsideTf(tf);
        return mapper.mapToTimeFrameDto(repository.save(tf));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TimeFrameDto updateTimeFrame(@RequestBody TimeFrameDto dto) {
        if (dto.getTimeStart().equals("-") && dto.getTimeEnd().equals("-")) {
            dto.setTimeStart("08:00");
            dto.setTimeEnd("16:00");
        }
        if (dto.getTimeStart().equals("off") && dto.getTimeEnd().equals("off")) {
            dto.setTimeStart("00:00");
            dto.setTimeEnd("00:00");
            dto.setStatus("Day_Off");
        }
        TimeFrame tf = mapper.mapToTimeFrame(dto);
        List<Appointment> aList = checkForAppsOutsideTf(tf);
        if (aList.size() > 0) {
            System.out.println("---- Project Appoint application ---- appointments are outside doctor's timeframe");
        }
        return mapper.mapToTimeFrameDto(repository.save(tf));
    }

    @DeleteMapping("/{timeFrameId}")
    public void deleteTimeFrame(@PathVariable Long timeFrameId) {
        System.out.println(" >>>> deleteTimeFrame - repo got tfId= " + timeFrameId);
        repository.deleteById(timeFrameId);
    }

    @PostMapping("/autoCreateTfList")
    public boolean autoCreateTimeFrames(LocalDate today) {
        List<Doctor> doctorList = docRepository.findAll();
        for(Doctor doc : doctorList) {
            List<TimeFrame> tfList = repository.findTimeFrameByDoc(doc.getId());
            List<TimeFrame> newTfList = new ArrayList<>();
            for(int n = 0; n < 30; n++) {
                TimeFrame sampleTf = new TimeFrame(
                        today.plusDays(n), LocalTime.of(8, 0), LocalTime.of(16, 0), "Present", doc);
                boolean found = false;
                for (TimeFrame singleTf : tfList) {
                    if (singleTf.getTimeframeDate().equals(sampleTf.getTimeframeDate())) {
                        found = true;
                        break;
                    }
                }
                if (!found) newTfList.add(sampleTf);
            }
            tfList.addAll(newTfList);
            repository.saveAll(tfList);
        }
        return true;
    }

    @GetMapping("getAppsOutsideTf/{timeFrameId}")
    public List<AppointmentDto> getAppsOutsideTf(@PathVariable Long timeFrameId) {
        return aMapper.mapToAppointmentDtoList(checkForAppsOutsideTf(repository.findById(timeFrameId)
                .orElseThrow(IllegalArgumentException::new)));
    } // we will need complete DB check - all TFs against Appointments

    public List<Appointment> checkForAppsOutsideTf(TimeFrame tf) {
        List<Appointment> appOutsideList = new ArrayList<>();

        if (tf.getDoctor().getAppointments() == null || tf.getDoctor().getAppointments().size() == 0) {
            System.out.println(" Doctor: " + tf.getDoctor().getName() + " has no appointments");
            return Collections.emptyList();
        }
        for (Appointment item : tf.getDoctor().getAppointments()) {
            LocalDate aDate = LocalDate.from(item.getStartDateTime());
            LocalTime aTime = LocalTime.from(item.getStartDateTime());
            if (tf.getTimeframeDate().equals(aDate)) {
                if (aTime.isBefore(tf.getTimeStart()) ||aTime.isAfter(tf.getTimeEnd()) ||aTime.equals(tf.getTimeEnd()))
                {
                    appOutsideList.add(item);
                }
            }
        }
        return appOutsideList;
    }
}
