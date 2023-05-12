package com.example.appointback;

import com.example.appointback.external.HolidayController;
import com.example.appointback.external.HolidayDao;
import com.example.appointback.external.HolidayRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class HolidaysIntegrationTests {

    @Autowired
    private HolidayController controller;

    HolidayRepository repository;

    @Test
    public void test() {
        // given
        LocalDate holidayDate = LocalDate.now().plusDays(17L);
        HolidayDao dao = new HolidayDao(null, "very special holiday", LocalDate.now().plusDays(17L));

        // when

        // then
        assertEquals(0,0);
    }
}
