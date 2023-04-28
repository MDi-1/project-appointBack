package com.example.appointback.eventadapter;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarRequest;
import com.google.api.services.calendar.model.Event;

import java.io.IOException;

public class Processor {

    public static final String INS = "INS";
    public static final String DEL = "DEL";

    public CalendarRequest performGoAction(Calendar c, String action, String calendarId, Event evt) throws IOException {
        c.events().insert(calendarId, evt);

        switch (action) {
            case INS:
            case DEL:
                return null;
        }
        return null;
    }

    public CalendarRequest performGoAction(Calendar c, String action, String calendarId, String id) throws IOException {
        c.events().delete(calendarId, id);

        switch (action) {
            case INS:
            case DEL:
                return null;
        }
        return null;
    }


}
