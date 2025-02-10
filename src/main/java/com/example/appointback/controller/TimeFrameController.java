package com.example.appointback.controller;

import com.example.appointback.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/timeFrame")
@RequiredArgsConstructor
public class TimeFrameController {

    private final TimeFrameMapper mapper;
    private final AppointmentMapper aMapper;
    private final TimeFrameRepository repository;
    private final DoctorRepository docRepository;
    private final AppointmentRepository appRepository;

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
            dto.setTimeStart(CoreConfiguration.DEFAULT_STARTING_TIME.toString());
            dto.setTimeEnd(CoreConfiguration.DEFAULT_ENDING_TIME.toString());
        }
        if (dto.getTimeStart().equals("off") && dto.getTimeEnd().equals("off")) {
            dto.setTimeStart("00:00");
            dto.setTimeEnd("00:00");
            dto.setTfStatus(TimeFrame.TfStatus.Day_Off);
        }
        TimeFrame tf = mapper.mapToTimeFrame(dto);
        checkForAppsOutsideTf(tf);
        return mapper.mapToTimeFrameDto(repository.save(tf));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TimeFrameDto updateTimeFrame(@RequestBody TimeFrameDto dto) {
        if (dto.getTimeStart().equals("-") && dto.getTimeEnd().equals("-")) {
            dto.setTimeStart(CoreConfiguration.DEFAULT_STARTING_TIME.toString());
            dto.setTimeEnd(CoreConfiguration.DEFAULT_ENDING_TIME.toString());
        }
        if (dto.getTimeStart().equals("off") && dto.getTimeEnd().equals("off")) {
            dto.setTimeStart("00:00");
            dto.setTimeEnd("00:00");
            dto.setTfStatus(TimeFrame.TfStatus.Day_Off);
        }
        TimeFrame tf = mapper.mapToTimeFrame(dto);
        List<Appointment> aList = checkForAppsOutsideTf(tf);
        if (!aList.isEmpty()) {
            System.out.println("---- Project Appoint application ---- appointments are outside doctor's timeframe");
        }
        return mapper.mapToTimeFrameDto(repository.save(tf));
    }

    @DeleteMapping("/{timeFrameId}")
    public void deleteTimeFrame(@PathVariable Long timeFrameId) {
        repository.deleteById(timeFrameId);
    }

    @PostMapping("/autoCreateTfList")
    public void autoCreateTimeFrames(LocalDate today) {
        if (today == null) today = LocalDate.now();
        for (Doctor iteratedDoctor : docRepository.findAll()) {
            List<TimeFrame> tfList = repository.findTimeFrameByDoc(iteratedDoctor.getId());
            List<TimeFrame> newList = new ArrayList<>();
            for (int n = 0; n < 30; n++) {
                boolean found = false;
                for (TimeFrame singleTf : tfList) {
                    if (singleTf.getTimeframeDate().equals(today.plusDays(n))) {
                        found = true;
                        break;
                    }
                }
                if (!found) { // wyrzucić "if" bo found zawsze = false, jeśli wykonanie dojdzie do tego miejsca; fixme
                    newList.add(new TimeFrame(
                            today.plusDays(n),
                            CoreConfiguration.DEFAULT_STARTING_TIME,
                            CoreConfiguration.DEFAULT_ENDING_TIME,
                            TimeFrame.TfStatus.Present,
                            iteratedDoctor
                    ));
                }
            }
            tfList.addAll(newList);
            repository.saveAll(tfList);
        }
    }

    @GetMapping("getAppsOutsideTf/{timeFrameId}")
    public List<AppointmentDto> getAppsOutsideTf(@PathVariable Long timeFrameId) {
        return aMapper.mapToAppointmentDtoList(checkForAppsOutsideTf(repository.findById(timeFrameId)
                .orElseThrow(IllegalArgumentException::new)));
    }

    public List<Appointment> checkForAppsOutsideTf(TimeFrame tf) {
        List<Appointment> appOutsideList = new ArrayList<>();
        List<Appointment> inputList = tf.getDoctor().getAppointments();
        if (inputList == null || inputList.isEmpty()) {
            System.out.println(" Doctor: " + tf.getDoctor().getName() + " has no appointments");
            return appOutsideList;
        }
        for (Appointment item : inputList) {
            LocalDate date = LocalDate.from(item.getStartDateTime());
            LocalTime time = LocalTime.from(item.getStartDateTime());
            LocalTime tStart = tf.getTimeStart(), tEnd = tf.getTimeEnd();
            if (tf.getTimeframeDate().equals(date) && (time.isBefore(tStart)||time.isAfter(tEnd)||time.equals(tEnd))) {
                appOutsideList.add(item);
            }
        }
        return appOutsideList;
    }

    public List<Appointment> searchForOrphanedApps() {
        List<Appointment> resultList = appRepository.findAll();
        List<Appointment> aListToSubtract = new ArrayList<>();
        repository.findAll().forEach(tf -> aListToSubtract.addAll(checkForAppsOutsideTf(tf)));
        resultList.removeAll(aListToSubtract);
        return resultList;
    }

    @GetMapping("getOrphanedApps/{timeFrameId}")
    public List<AppointmentDto> getOrphanedApps() {
        return aMapper.mapToAppointmentDtoList(searchForOrphanedApps());
    }
}
