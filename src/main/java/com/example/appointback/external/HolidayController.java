package com.example.appointback.external;

import com.example.appointback.controller.TimeFrameRepository;
import com.example.appointback.entity.TimeFrame;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/holiday")
@RequiredArgsConstructor
public class HolidayController {

    @Value("${abstractapi.endpoint}")
    private String endpointPrefix;
    @Value("${abstractapi.key}")
    private String key;
    private final RestTemplate restTemplate;
    private final HolidayRepository repository;
    private final TimeFrameRepository tfRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(HolidayController.class);

    @PostMapping
    public void runHolidaysCheck() {
        List<HolidayDao> list = getHolidays();
        LocalDate date = list.stream().filter(e -> e.getName().equals("marker"))
                .map(HolidayDao::getDate).min(Collections.reverseOrder()).orElse(findLastHolidayDate(list));
        if (date.isAfter(LocalDate.now().plusDays(29L))) return;
        for (int i = 0; i < 30; i ++) {
            HolidayDto dto = makeHolidayApiRequest(date.plusDays(i));
            if (dto != null) repository.save(new HolidayDao(null, dto.getName(), date.plusDays(i)));
        }
        int n = 0;
        LocalDate endDay;
        do {
            n ++;
            if (n > 20) return;
            endDay = date.plusDays(30 + n);
        }
        while (makeHolidayApiRequest(endDay) != null);
        repository.save(new HolidayDao(null, "marker", endDay));
    }

    private LocalDate findLastHolidayDate(List<HolidayDao> list) {
        System.out.println("---- Project Appoint app; .orElse() call performed #2 - findLastHolidayDate()");
        return list.stream().map(HolidayDao::getDate).min(Collections.reverseOrder()).orElse(getPresentDate());
    }

    private LocalDate getPresentDate() {
        System.out.println("---- Project Appoint app; .orElse() call performed #3 - getPresentDate()");
        return LocalDate.now();
    }
    // (i) purpose of 2 prints above is to see if .orElse argument is checked in a stream, even if stream finds
    // desired item. It turns out that these two consecutive calls (findLastHolidayDate() and getPresentDate() )
    // are made. Therefore it's better to use 'if / else' statement in order to make calls #2 and #3 unreachable after
    // first search is successful.

    public HolidayDto makeHolidayApiRequest(LocalDate beginDate) {
        HolidayDto dto;
        URI url = UriComponentsBuilder.fromHttpUrl(endpointPrefix)
                .queryParam("api_key", key)
                .queryParam("country", "pl")
                .queryParam("year" , beginDate.getYear())
                .queryParam("month", beginDate.getMonthValue())
                .queryParam("day"  , beginDate.getDayOfMonth())
                .build().encode().toUri();
        try {
            HolidayDto[] response = restTemplate.getForObject(url, HolidayDto[].class);
            List<HolidayDto> list = Optional.ofNullable(response).map(Arrays::asList).orElse(Collections.emptyList());
            dto = list.stream().findFirst().orElse(null);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            dto = null;
        }
        try {
            Thread.sleep(2000);
            System.out.println("---- Project Appoint app; external API request sent:\n" + url);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return dto;
    }

    @PutMapping("/applyToTfs")
    public void applyHolidaysToTfs() {
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
