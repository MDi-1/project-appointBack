package com.example.appointback.external;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component("holidayClient")
@RequiredArgsConstructor
public class HolidayClient {

    @Value("${abstractapi.endpoint}")
    private String endpointPrefix;
    @Value("${abstractapi.key}")
    private String key;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(HolidayController.class);

    public HolidayDto makeHolidayApiRequest(LocalDate requestedDate) {
        HolidayDto dto;
        URI url = UriComponentsBuilder.fromHttpUrl("https://holidays.abstractapi.com/v1/")
                .queryParam("api_key", key)
                .queryParam("country", "pl")
                .queryParam("year" , requestedDate.getYear())
                .queryParam("month", requestedDate.getMonthValue())
                .queryParam("day"  , requestedDate.getDayOfMonth())
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
        if (dto != null && dto.getType().equals("National")) return dto;
        else return null;
    }
}
