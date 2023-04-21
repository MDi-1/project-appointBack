package com.example.appointback.external;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class GoogleCalendarService {

    private static final String APP_NAME = "MyCalendarApp";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;
    private static final String CREDENTIALS_FILE_PATH = "/path/to/your/credentials.json";
    private static final String CLIENT_SECRET_FILE_PATH = "/path/to/your/client_secret.json";
    private static final String REDIRECT_URI = GoogleOAuthConstants.OOB_REDIRECT_URI;
    private static final String USER_ID = "user123@gmail.com";
    private static final String CALENDAR_ID = "primary";

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    private static Credential authorize() throws IOException {
        InputStream in = GoogleCalendarService.class.getResourceAsStream("/credentials.json");
        if (in == null) throw new FileNotFoundException("Resource not found: " + "/credentials.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, Collections.singleton(CalendarScopes.CALENDAR))
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize(USER_ID);
    }

    private static Calendar getCalendarService() throws IOException {
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, authorize()).setApplicationName(APP_NAME).build();
    }

    public static void addEvent(Event event) throws IOException {
        getCalendarService().events().insert(CALENDAR_ID, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }

    public static void unusedFunction(String... args) throws IOException {
        Event event = new Event()
                .setSummary("Test Event")
                .setDescription("This is a test event");
        DateTime startDateTime = new DateTime("2023-04-01T10:00:00+02:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Warsaw");
        event.setStart(start);
        DateTime endDateTime = new DateTime("2023-04-01T12:00:00+02:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Europe/Warsaw");
        event.setEnd(end);
        addEvent(event);
    }
} // remove this class when project appoint is done. fixme