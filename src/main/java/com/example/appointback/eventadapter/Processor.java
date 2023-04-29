package com.example.appointback.eventadapter;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarRequest;
import com.google.api.services.calendar.model.Event;
import java.io.IOException;
// this class along with the package to be removed in first release
public class Processor { // an attempt to develop adapter pattern between Appointment entity and Event google object

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
