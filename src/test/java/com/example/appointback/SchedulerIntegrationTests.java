package com.example.appointback;

import com.example.appointback.controller.AppointmentController;
import com.example.appointback.controller.DoctorController;
import com.example.appointback.controller.PatientController;
import com.example.appointback.controller.SchedulerController;
import com.example.appointback.entity.AppointmentDto;
import com.example.appointback.entity.PatientDto;
import com.example.appointback.entity.SchedulerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class SchedulerIntegrationTests {

    @Autowired
    private SchedulerController scController;
    @Autowired
    private DoctorController doctorController;
    @Autowired
    private PatientController patientController;
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
        String dateTime = LocalDateTime.now().toString();
        SchedulerDto scheduler = scController.createScheduler(new SchedulerDto(null, "sc"));
        PatientDto patDto = patientController.createPatient(new PatientDto(null, "abc", "xyz"));
        AppointmentDto appDto = new AppointmentDto(null, dateTime, 100, scheduler.getId(), patDto.getId());
        AppointmentDto appOut = appointmentController.createAppointment(appDto);
        List<Long> aList = new ArrayList<>(Collections.singletonList(appOut.getId()));
        // when
        scController.updateScheduler(new SchedulerDto(scheduler.getId(), "sc", aList));
        String result = scController.getSchedulers()
                .stream().findAny().orElseThrow(IllegalArgumentException::new).getName();
        // then
        assertEquals("sc", result);
    }
}
