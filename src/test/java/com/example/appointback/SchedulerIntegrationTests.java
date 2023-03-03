package com.example.appointback;

import com.example.appointback.controller.AppointmentController;
import com.example.appointback.controller.SchedulerController;
import com.example.appointback.entity.AppointmentDto;
import com.example.appointback.entity.SchedulerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class SchedulerIntegrationTests {

    @Autowired
    private SchedulerController scController;
    @Autowired
    private AppointmentController appointmentController;

    @Test
    public void testSchedulerCreate() {
        // given
        scController.createScheduler(new SchedulerDto(null, "sc"));
        // when
        String result = scController.getSchedulers()
                .stream().findAny().orElseThrow(IllegalArgumentException::new).getName();
        // then
        assertEquals("sc", result);
    }

    @Test
    public void testSchedulerUpdate() {
        // given
        SchedulerDto scheduler = scController.createScheduler(new SchedulerDto(null, "sc"));
        AppointmentDto appDto = new AppointmentDto(null, LocalDateTime.now().toString(), 100, scheduler.getId(), 1L);
        AppointmentDto appOut = appointmentController.createAppointment(appDto);
        List<Long> aList = new ArrayList<>(Arrays.asList(appOut.getId()));
        SchedulerDto scUpdated = scController.updateScheduler(new SchedulerDto(scheduler.getId(), "sc", aList));
        // when
        String result = scController.getSchedulers()
                .stream().findAny().orElseThrow(IllegalArgumentException::new).getName();
        // then
        assertEquals("sc", result);
    }
}
