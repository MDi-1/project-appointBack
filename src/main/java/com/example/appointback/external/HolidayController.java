package com.example.appointback.external;

import com.example.appointback.controller.CoreConfiguration;
import com.example.appointback.controller.TimeFrameRepository;
import com.example.appointback.entity.TimeFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/holiday")
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayRepository repository;
    private final TimeFrameRepository tfRepository;
    private final HolidayClient holidayClient;

    @PostMapping("/runApi")
    public boolean runHolidaysCheckWrapper() {
        return runHolidaysCheck(holidayClient);
    }

    // writes to db holidays and marker objects for about a month onward.
    public boolean runHolidaysCheck(HolidayClient client) {
        List<HolidayDao> list = getHolidays();
        LocalDate date = list.stream().filter(e -> e.getName().equals("marker"))
                .map(HolidayDao::getDate).min(Collections.reverseOrder()).orElse(findLastHolidayDate(list));
        if (date.isAfter(CoreConfiguration.getStartingDate().plusDays(29L))) return false;
        for (int i = 0; i < 30; i ++) {
            HolidayDto dto = client.makeHolidayApiRequest(date.plusDays(i));
            if (dto != null) repository.save(new HolidayDao(null, dto.getName(), date.plusDays(i)));
        }
        int n = 0;
        LocalDate endDay;
        do {
            n ++;
            if (n > 20) return true; // trzeba sprawdzić w debuggerze czy ta f. w ogóle działa
            endDay = date.plusDays(30 + n);
        }
        while (client.makeHolidayApiRequest(endDay) != null);
        repository.save(new HolidayDao(null, "marker", endDay));
        return true;
    }

    private LocalDate findLastHolidayDate(List<HolidayDao> list) {
        System.out.println("---- Project Appoint app; .orElse() call performed #2 - findLastHolidayDate()");
        return list.stream().map(HolidayDao::getDate).min(Collections.reverseOrder()).orElse(getPresentDate());
    }

    private LocalDate getPresentDate() {
        System.out.println("---- Project Appoint app; .orElse() call performed #3 - getPresentDate()");
        return CoreConfiguration.getStartingDate();
    }
    // (i) purpose of 2 prints above is to see if .orElse argument is checked in a stream, even if stream finds
    // desired item. It turns out that these two consecutive calls (findLastHolidayDate() and getPresentDate() )
    // are made. Therefore it's better to use 'if / else' statement in order to make calls #2 and #3 unreachable after
    // first search is successful.

    @PutMapping("/applyToTfs")
    public void applyHolidaysToTfs() { // alters TimeFrame table according to Holidays table.
        List<TimeFrame> tfList = tfRepository.findAll();
        List<HolidayDao> holidayList = getHolidays()
                .stream().filter(e -> !e.getName().equals("marker")).collect(Collectors.toList());
        List<TimeFrame> tfOutputList = tfList.stream()
                .filter(tfElement -> holidayList.stream()
                        .anyMatch(holidayElement -> holidayElement.getDate().equals(tfElement.getTimeframeDate())))
                .collect(Collectors.toList());
        tfOutputList.forEach(timeFrame -> {
            timeFrame.setStatus("Holiday");
            timeFrame.setTimeStart(LocalTime.of(0, 0));
            timeFrame.setTimeEnd(LocalTime.of(0, 0));
            tfRepository.save(timeFrame);
        });
    }

    @GetMapping("/getAll")
    public List<HolidayDao> getHolidays() {
        return repository.findAll();
    }
}
