package com.example.appointback;

import com.example.appointback.external.HolidayClient;
import com.example.appointback.external.HolidayDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;

@Component("clientStub")
class HolidayClientStub extends HolidayClient {

    public HolidayClientStub(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public HolidayDto makeHolidayApiRequest(LocalDate requestedDate) {
        return null;
    }
}
