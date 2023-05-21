package com.example.appointback;

import com.example.appointback.external.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
    private HolidayClient client_old; // 2 B deleted? fixme
    @Mock
    private HolidayClientStub client;
    @Autowired
    private HolidayController controller;
    @Autowired
    private HolidayRepository repository;

    @Test
    public void testRunHolidaysCheck() {
        // given
        LocalDate dateForTesting = getStartingDate().plusDays(27L);
        repository.save(new HolidayDao(null, "very special holiday", getStartingDate().plusDays(17L)));
        HolidayDto mockedDto = new HolidayDto("abc", dateForTesting.toString());
        HolidayDate argumentMatcherToInsert = new HolidayDate(dateForTesting);
        System.out.println(
                " ]] argument matcher: [[\n" + argumentMatcherToInsert + "\n" + " ]] mockedDto: [[\n" + mockedDto);
        // trzeba będzie zrobić jak w kodilla-course klasa: WeatherForecastTestSuite. f. runHolidaysCheck() będzie
        // musiała przyjmować arg w postaci interface i to będzie obiekt klasy, w konstruktorze której wykonujemy
        // API request, bądź stubujemy API request
        // fixme

        when(client.makeHolidayApiRequest(argThat(argumentMatcherToInsert))).thenReturn(mockedDto);
        // when
        boolean functionRan = controller.runHolidaysCheck(client);
        HolidayDao dao = controller.getHolidays().stream().min(Collections.reverseOrder()).orElse(null);
        String name = dao.getName();
        // then
        System.out.println(" ]] all holiday repo print: [[\n" + repository.findAll());
        assertAll(
                () -> assertTrue(functionRan)
                //() -> assertEquals("marker", name)
        );
    }
}

@Component
class HolidayClientStub extends HolidayClient {

    public HolidayClientStub(RestTemplate restTemplate) {
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
