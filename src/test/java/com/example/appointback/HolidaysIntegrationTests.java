package com.example.appointback;

import com.example.appointback.external.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Comparator;

import static com.example.appointback.controller.CoreConfiguration.getPresentDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
public class HolidaysIntegrationTests {

    @Mock
    private HolidayClientStub holidayClientStub; // (i) field name is pretty much meaningful here (camelCase use here),
    // Spring knows here what to be autowired in HolidayController as holidayController because
    // class HolidayClientStub extends HolidayClient. Whenever here and in HolidayClient we would use field name let's
    // say "client" then we get IllegalStateException:Failed to load ApplicationContext. Spring has got 2 same autowire
    // candidates and doesn't know which to choose.
    @Autowired
    private HolidayController controller;

    @Autowired
    private HolidayRepository repository;

    @Test
    public void testRunHolidaysCheck() {
        // given
        LocalDate dateForTesting = getPresentDate().plusDays(27L);
        repository.save(new HolidayDao(null, "very special holiday", getPresentDate().plusDays(17L)));
        HolidayDto mockedDto = new HolidayDto("abc", dateForTesting.toString(), "National");
        HolidayDateMatcher argumentMatcherToInsert = new HolidayDateMatcher(dateForTesting);
        when(holidayClientStub.makeHolidayApiRequest(argThat(argumentMatcherToInsert))).thenReturn(mockedDto);
        // when
        boolean functionRan = controller.runHolidaysCheck(holidayClientStub); //try to understand this again -todo
        HolidayDao dao = controller.getHolidays().stream()
                .max(Comparator.comparingLong(HolidayDao::getId)).orElse(null);
        // then
        assertAll( () -> assertTrue(functionRan), () -> assertEquals("marker", dao.getName()));
    }
}