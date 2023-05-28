package com.example.appointback;

import com.example.appointback.external.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.Comparator;

import static com.example.appointback.controller.CoreConfiguration.getStartingDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
public class HolidaysIntegrationTests {

    @Mock
    private ClientStub clientStub; // (i) field name is pretty much meaningful here (camelCase use here),
    // Spring knows here what to be autowired in HolidayController as holidayController because
    // class ClientStub extends HolidayClient. Whenever here and in HolidayClient we would use field name let's say
    // "client" then we get IllegalStateException: Failed to load ApplicationContext. Spring has got 2 same autowire
    // candidates and doesn't know which to choose.
    @Autowired
    private HolidayController controller;

    @Autowired
    private HolidayRepository repository;

    @Test
    public void testRunHolidaysCheck() {
        // given
        LocalDate dateForTesting = getStartingDate().plusDays(27L);
        repository.save(new HolidayDao(null, "very special holiday", getStartingDate().plusDays(17L)));
        HolidayDto mockedDto = new HolidayDto("abc", dateForTesting.toString(), "National");
        HolidayDate argumentMatcherToInsert = new HolidayDate(dateForTesting);
        System.out.println(
                " ]] argument matcher: [[\n" + argumentMatcherToInsert + "\n" + " ]] mockedDto: [[\n" + mockedDto);
        when(clientStub.makeHolidayApiRequest(argThat(argumentMatcherToInsert))).thenReturn(mockedDto);
        // when
        boolean functionRan = controller.runHolidaysCheck(clientStub);
        HolidayDao dao = controller.getHolidays().stream()
                .max(Comparator.comparingLong(HolidayDao::getId)).orElse(null);
        // then
        assertAll(
                () -> assertTrue(functionRan),
                () -> assertEquals("marker", dao.getName())
        );
    }
}

@Component("clientStub")
class ClientStub extends HolidayClient {

    public ClientStub(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public HolidayDto makeHolidayApiRequest(LocalDate requestedDate) {
        return null;
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

    @Override
    public String toString() {
        return "HolidayDate{" + "date=" + date + '}';
    }
}
