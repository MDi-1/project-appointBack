package com.example.appointback;

import com.example.appointback.controller.AppointmentController;
import com.example.appointback.controller.DoctorController;
import com.example.appointback.controller.PatientController;
import com.example.appointback.controller.TimeFrameController;
import com.example.appointback.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.coyote.http11.Constants.a;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class AppointmentIntegrationTests {

    @Autowired
    private AppointmentController appController;
    @Autowired
    private DoctorController doctorController;
    @Autowired
    private PatientController patientController;
    @Autowired
    private TimeFrameController tfController;
    private Long aId;
    private Long dId;
    private Long pId;

    @BeforeEach
    public void prepareDB() {
        DoctorDto dOut = doctorController.createDoctor(new DoctorDto(null, "Doc", "Abc", "Specialist", false));
        dId = dOut.getId();
        PatientDto pOut = patientController.createPatient(new PatientDto(null, "Pat", "Xyz", null));
        pId = pOut.getId();
        AppointmentDto a1 = appController.createAppointment(new AppointmentDto(null,"2023-03-03T09:00",160,dId,pId));
        AppointmentDto a2 = appController.createAppointment(new AppointmentDto(null,"2023-03-04T10:00",200,dId,pId));
        TimeFrameDto tfDto = new TimeFrameDto(null, "2023-03-03", "08:00", "16:00", "Present", dOut.getId());
        TimeFrameDto tfOut = tfController.createTimeFrame(tfDto);
        aId = a2.getId();
        List<Long> appList = new ArrayList<>(Arrays.asList(a1.getId(), aId));
        List<Long> tfList = new ArrayList<>(Collections.singletonList(tfOut.getId()));
        doctorController.updateDoctor(new DoctorDto(dId, "Doc", "Abc", "Specialist", false, tfList, appList, null));
        patientController.updatePatient(new PatientDto(pId, "Pat", "Xyz", appList));
    }

    @Test
    public void testGetAppointments() {
        // given, when
        List<AppointmentDto> list = appController.getAllAppointments();
        // then
        assertEquals(2, list.size());
    }

    @Test
    public void testGetAppointment() {
        // given, when
        AppointmentDto a = appController.getAppointment(aId);
        // then
        assertEquals("2023-03-04,10:00", a.getStartDateTime());
    }

    @Test
    public void testGetAppointmentsForDoc() {
        // given, when
        List<AppointmentDto> list = appController.getAppsForDoctor(dId);
        // then
        assertEquals(2, list.size());
    }

    @Test
    public void testGetAppsForPatient() {
        // given, when
        List<AppointmentDto> list = appController.getAppsForPatient(pId);
        // then
        assertEquals(2, list.size());
    }

    @Test
    public void testUpdateAppointment() {
        // given
        AppointmentDto a = appController.updateAppointment(new AppointmentDto(aId, "2023-03-04T10:00", 50, dId, pId));
        // when
        AppointmentDto appointmentDto = appController.getAppointment(a.getId());
        // then
        assertEquals(50, appointmentDto.getPrice());
    }

    @Test
    public void testDeleteAppointment() {
        // given
        List<Long> tfIdList = tfController.getTimeFramesByDoctor(dId).stream()
                .map(TimeFrameDto::getId).collect(Collectors.toList());
        List<Long> idAppList = appController.getAllAppointments().stream()
                .map(AppointmentDto::getId).collect(Collectors.toList());
        // when
        idAppList.remove(aId);
        doctorController.updateDoctor(new DoctorDto(dId,"Doc","Abc","Specialist", false, tfIdList, idAppList, null));
        patientController.updatePatient(new PatientDto(pId, "Pat", "Xyz", idAppList));
        appController.deleteAppointment(aId);
        // then
        assertEquals(1, appController.getAllAppointments().size());
    }
}
