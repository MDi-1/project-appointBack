package com.example.appointback;

import org.mockito.ArgumentMatcher;
import java.time.LocalDate;
import static com.example.appointback.controller.CoreConfiguration.getPresentDate;

class HolidayDateMatcher implements ArgumentMatcher<LocalDate> {

    private final LocalDate date;

    public HolidayDateMatcher(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean matches(LocalDate date) {
        return date != null && date.equals(getPresentDate().plusDays(27L));
        // (i) before Intellij suggestion there was: if(date != null && date.equals(getStartingDate().plusDays(27L))) {
        // return true; } else { return false; }
    }

    @Override
    public String toString() {
        return "HolidayDate{" + "date=" + date + '}';
    }
}
