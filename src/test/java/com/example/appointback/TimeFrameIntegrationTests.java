package com.example.appointback;

import com.example.appointback.controller.*;
import com.example.appointback.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class TimeFrameIntegrationTests {

    @Autowired
    private DoctorController doctorController;
    @Autowired
    private PatientController patientController;
    @Autowired
    private AppointmentController appController;
    @Autowired
    private TimeFrameController tfController;
    @Autowired
    private TimeFrameRepository tfRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void testTfCreate() {
        // given
        DoctorDto dOut = doctorController.createDoctor(new DoctorDto(null, "", "", "Specialist", false));
        TimeFrameDto tfDto = new TimeFrameDto(null, "2023-03-03", "08:00", "16:00", "Present", dOut.getId());
        TimeFrameDto tfOut = tfController.createTimeFrame(tfDto);
        // when
        List<Long> tfList = new ArrayList<>(Collections.singletonList(tfOut.getId()));
        DoctorDto dDto = new DoctorDto(dOut.getId(), "Doc","McDoctough","Specialist", false, tfList, null, null);
        DoctorDto dOut2 = doctorController.updateDoctor(dDto);
        List<TimeFrameDto> resultList = tfController.getTimeFramesByDoctor(dOut2.getId());
        // then
        assertEquals(1, resultList.size());
    }

    @Test
    public void testGetTfById() {
        // given
        DoctorDto dOut = doctorController.createDoctor(new DoctorDto(null, "", "", "Specialist", false));
        TimeFrameDto tfDto = new TimeFrameDto(null, "2023-03-03", "08:00", "16:00", "Present", dOut.getId());
        TimeFrameDto tf = tfController.createTimeFrame(tfDto);
        List<Long> list = new ArrayList<>(Collections.singletonList(tf.getId()));
        doctorController.updateDoctor(new DoctorDto(dOut.getId(), "Abc","Xyz","Specialist", false, list, null, null));
        // when
        TimeFrameDto dtoRetrieved = tfController.getTimeFrame(tf.getId());
        // then
        assertEquals("2023-03-03", dtoRetrieved.getTimeFrameDate());
    }

    @Test
    public void testTfUpdate() {
        // given
        DoctorDto dOut = doctorController.createDoctor(new DoctorDto(null, "", "", "Specialist", false));
        TimeFrameDto tfDto = new TimeFrameDto(null, "2023-03-03", "08:00", "16:00", "Present", dOut.getId());
        TimeFrameDto tf = tfController.createTimeFrame(tfDto);
        // when
        List<Long> tfList = new ArrayList<>(Collections.singletonList(tf.getId()));
        DoctorDto dDto = new DoctorDto(dOut.getId(), "Doc","McDoctough","Specialist", false, tfList, null, null);
        DoctorDto dOut2 = doctorController.updateDoctor(dDto);
        TimeFrameDto testInput = new TimeFrameDto(tf.getId(), "2023-03-04", "6:00", "9:00", "Present", dOut2.getId());
        TimeFrameDto result = tfController.updateTimeFrame(testInput);
        // then
        assertEquals("2023-03-04", result.getTimeFrameDate());
    }

    @Test
    public void testAutoCreation() {
        // given
        DoctorDto doctor = doctorController.createDoctor(new DoctorDto(null, "Abc", "Xyz", "Specialist", false));
        String currentDate = LocalDate.now().plusWeeks(1L).toString();
        TimeFrameDto tfDto = new TimeFrameDto(null, currentDate, "08:00", "16:00", "Present", doctor.getId());
        tfController.createTimeFrame(tfDto);
        // when
        tfController.autoCreateTimeFrames(LocalDate.now());
        long count = tfRepository.findTimeFrameByDoc(doctor.getId()).size();
        // then
        assertEquals(30, count);
    }

    @Test
    public void testAppointmentDropout() {
        // given
        DoctorDto doctor = doctorController.createDoctor(new DoctorDto(null, "Abc", "Xyz", "Specialist", false));
        PatientDto pat = patientController.createPatient(new PatientDto(null, "Aaa", "Bbb"));
        TimeFrameDto tf1 = tfController.createTimeFrame(new TimeFrameDto(
                        null, LocalDate.of(2023, 3, 6).toString(), "-", "-", "Present", doctor.getId()));
        TimeFrameDto tf2 = tfController.createTimeFrame(new TimeFrameDto(
                        null, LocalDate.of(2023, 3, 7).toString(), "09:00", "12:00", "Present", doctor.getId()));
        // when
        AppointmentDto a1out = appController.createAppointment(new AppointmentDto(
                null, LocalDateTime.of(2023, 3, 7, 10, 0).toString(), 160, doctor.getId(), pat.getId()));
        List<Long> tfList = new ArrayList<>(Arrays.asList(tf1.getId(), tf2.getId()));
        List<Long> appList = new ArrayList<>(Collections.singletonList(a1out.getId()));
        doctorController.updateDoctor(new DoctorDto(doctor.getId(),"Abc","Xyz","Board",false,tfList,appList,null));
        TimeFrameDto tf2mod = tfController.updateTimeFrame(new TimeFrameDto(
                tf2.getId(), LocalDate.of(2023, 3, 7).toString(), "off", "off", "aaa", doctor.getId()));
        // then
        assertEquals("Day_Off", tfController.getTimeFrame(tf2mod.getId()).getStatus());
    }

    @Test
    public void testAppsOutsideTf() {
        // given
        DoctorDto doctor = doctorController.createDoctor(new DoctorDto(null, "doc", "Doc", "Specialist", false));
        PatientDto pat = patientController.createPatient(new PatientDto(null, "pat", "Pat"));
        TimeFrameDto tf = tfController.createTimeFrame(new TimeFrameDto(
                null, LocalDate.of(2023, 9, 15).toString(), "09:00", "12:00", "Present", doctor.getId()));
        AppointmentDto a1 = appController.createAppointment(new AppointmentDto(
                null, LocalDateTime.of(2023, 9, 15,  8, 0).toString(), 160, doctor.getId(), pat.getId()));
        AppointmentDto a2 = appController.createAppointment(new AppointmentDto(
                null, LocalDateTime.of(2023, 9, 15, 10, 0).toString(), 160, doctor.getId(), pat.getId()));
        AppointmentDto a3 = appController.createAppointment(new AppointmentDto(
                null, LocalDateTime.of(2023, 9, 15, 11, 0).toString(), 160, doctor.getId(), pat.getId()));
        AppointmentDto a4 = appController.createAppointment(new AppointmentDto(
                null, LocalDateTime.of(2023, 9, 15, 12, 0).toString(), 160, doctor.getId(), pat.getId()));
        // when
        List<Long> tfList = new ArrayList<>(Collections.singletonList(tf.getId()));
        List<Long> appList = new ArrayList<>(Arrays.asList(a1.getId(), a2.getId(), a3.getId(), a4.getId()));
        doctorController.updateDoctor(new DoctorDto(doctor.getId(),"doc","Doc","Board", false, tfList, appList, null));
        List<AppointmentDto> appsOutsideTf = tfController.getAppsOutsideTf(tf.getId());
        // then
        assertEquals(2, appsOutsideTf.size());
    }
}
