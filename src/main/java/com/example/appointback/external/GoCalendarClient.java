package com.example.appointback.external;

import com.example.appointback.entity.Appointment;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoCalendarClient {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CALENDAR_ID =
            "2706375a9773699d531196c38eb990dcb1758a077c560523a080da81d475bd2f@group.calendar.google.com";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = GoCalendarClient.class.getResourceAsStream("/credentials.json");
        if (in == null) throw new FileNotFoundException("Resource not found: " + "/credentials.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static Calendar getCalendarService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName("proj-appoint 0").build();
    }

    public static Event postEvent(Appointment appointment) {
        String patientString = appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName();
        Event event = new Event()
                .setId("proapp" + appointment.getId().toString())
                .setSummary(patientString + " - appointment")
                .setDescription("appointment with " + patientString + " valued " + appointment.getPrice());
        DateTime startDateTime = new DateTime(appointment.getStartDateTime() + ":00+02:00");
        EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("Europe/Warsaw");
        event.setStart(start);
        DateTime endDateTime = new DateTime(appointment.getStartDateTime().plusHours(1L) + ":00+02:00");
        EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("Europe/Warsaw");
        event.setEnd(end);
        try {
            return getCalendarService().events().insert(CALENDAR_ID, event).execute();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteEvent(Long id) {
        try {
            getCalendarService().events().delete(CALENDAR_ID, "proapp" + id).execute();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}