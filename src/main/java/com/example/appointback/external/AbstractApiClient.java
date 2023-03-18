package com.example.appointback.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@Component
public class AbstractApiClient {

    @Value("${abstractapi.endpoint}")
    private String endpointPrefix;
    @Value("${abstractapi.key}")
    private String key;
    @Autowired
    private RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractApiClient.class);

    public Holiday getHolidays() {
        URI url = UriComponentsBuilder.fromHttpUrl(endpointPrefix)
                .queryParam("api_key", key)
                .queryParam("country", "pl")
                .queryParam("year", 2023)
                .queryParam("month", 3)
                .queryParam("day", 4)
                .build().encode().toUri();
        return null;
    }
}
