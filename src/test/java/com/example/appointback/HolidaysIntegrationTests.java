package com.example.appointback;

import com.example.appointback.external.HolidayController;
import com.example.appointback.external.HolidayDao;
import com.example.appointback.external.HolidayDto;
import com.example.appointback.external.HolidayRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;

import static com.example.appointback.controller.CoreConfiguration.getStartingDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
public class HolidaysIntegrationTests {

    @Mock
    @Autowired
    private HolidayController controller;
    @Autowired
    private HolidayRepository repository;

    @Test
    public void testRunHolidaysCheck() {
        // given
        repository.save(new HolidayDao(null, "very special holiday", getStartingDate().plusDays(17L)));
        HolidayDto mockedDto = new HolidayDto("abc", "1");

        when(controller.makeHolidayApiRequest(argThat(new HolidayDate(getStartingDate().plusDays(27L)))))
                .thenReturn(mockedDto);
        // when
        boolean functionRan = controller.runHolidaysCheck();
        HolidayDao dao = controller.getHolidays().stream().sorted(Collections.reverseOrder()).findFirst().orElse(null);
        String name = dao.getName();
        // then
        System.out.println(" ]] all holiday repo print: [[\n" + repository.findAll());
        assertAll(
                () -> assertTrue(functionRan)
                //() -> assertEquals("marker", name)
        );
    }
}

class HolidayDate implements ArgumentMatcher<LocalDate> {

    private final LocalDate date;

    public HolidayDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean matches(LocalDate date) {
        return date != null && date.equals(getStartingDate().plusDays(27L));
        // (i) before Intellij suggestion there was: if(date != null && date.equals(getStartingDate().plusDays(27L))) {
        // return true; } else { return false; }
    }
}
