package com.example.appointback.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AbstractApiClient {

    @Value("${https://holidays.abstractapi.com/v1/}")
    private String endpointPrefix;
    @Autowired
    private RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractApiClient.class);

    public void getHolidays() {
        return;
    }
}
