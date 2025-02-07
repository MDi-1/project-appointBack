package com.example.appointback;

import com.example.appointback.controller.*;
import com.example.appointback.entity.*;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.appointback.entity.CalendarHolder.Position.*;
import static com.example.appointback.entity.TimeFrame.TfStatus.*;
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
    private MedServiceController msController;
    DoctorDto doctorOut;

    @BeforeEach
    public void prepareDoctor() {
        MedicalServiceDto ms = msController.createMedService(new MedicalServiceDto("Surgeon", 250));
        DoctorDto doctorIn = new DoctorDto("DocName", "DocLastname", Collections.singletonList(ms.getId()));
        doctorOut = doctorController.createDoctor(doctorIn);
        TimeFrameDto timeFrameDto = new TimeFrameDto(null, "2023-06-03", "08:00", "16:00", Present, doctorOut.getId());
        tfController.createTimeFrame(timeFrameDto);
    }


    @Test
    public void testTfCreate() {
        // given
        TimeFrameDto tfDto2 = new TimeFrameDto(null, "2023-06-04", "08:00", "16:00", Present, doctorOut.getId());
        tfController.createTimeFrame(tfDto2);
        List<Long> timeframes = tfController.getTimeFrames().stream()
                .map(TimeFrameDto::getId).collect(Collectors.toList());
        DoctorDto doctor = doctorController.getDoctors().get(0);
        doctor.setTimeFrameIds(timeframes);
        doctorController.updateDoctor(doctor);
        // when
        List<TimeFrameDto> list = tfController.getTimeFramesByDoctor(doctorOut.getId());
        // then
        assertEquals(2, list.size());
    }

    @Test
    public void testGetTfById() {
        // given
        Long timeFrameId = tfController.getTimeFrames().get(0).getId();
        // when
        TimeFrameDto timeFrameResponse = tfController.getTimeFrame(timeFrameId);
        // then
        assertEquals("2023-06-03", timeFrameResponse.getTimeFrameDate());
    }

    @Test
    public void testTfUpdate() {
        // given
        TimeFrameDto tf = tfController.getTimeFrames().get(0);
        DoctorDto doctor = doctorController.getDoctors().get(0);
        DoctorDto dOut2 = doctorController.updateDoctor(doctor);
        // when
        TimeFrameDto testInput = new TimeFrameDto(tf.getId(), "2023-06-05", "6:00", "9:00", Present, dOut2.getId());
        TimeFrameDto result = tfController.updateTimeFrame(testInput);
        // then
        assertEquals("2023-06-05", result.getTimeFrameDate());
    }

    @Test
    public void testAutoCreation() {
        // given
        String currentDate = LocalDate.now().plusWeeks(1L).toString();
        TimeFrameDto tfDto = new TimeFrameDto(null, currentDate, "08:00", "16:00", Present, doctorOut.getId());
        tfController.createTimeFrame(tfDto);
        // when
        tfController.autoCreateTimeFrames(LocalDate.now());
        long count = tfController.getTimeFramesByDoctor(doctorOut.getId()).size();
        // then
        assertEquals(31, count);
    }

    @Test
    public void testAppointmentDropout() {
        // given
        Long msId = msController.getMedServices().get(0).getId();
        Long patId = patientController.createPatient(new PatientDto(null, "Aaa", "Bbb")).getId();
        TimeFrameDto tf1 = tfController.createTimeFrame(new TimeFrameDto(
                        null, LocalDate.of(2023, 6, 6).toString(), "-", "-", Present, doctorOut.getId()));
        TimeFrameDto tf2 = tfController.createTimeFrame(new TimeFrameDto(
                        null, LocalDate.of(2023, 6, 7).toString(), "09:00", "12:00", Present, doctorOut.getId()));
        // when
        AppointmentDto a1out = appController.createAppointment(new AppointmentDto(
                null, LocalDateTime.of(2023, 6, 7, 10, 0).toString(), 160, msId, doctorOut.getId(), patId));
        List<Long> tfList = new ArrayList<>(Arrays.asList(tf1.getId(), tf2.getId()));
        List<Long> appList = new ArrayList<>(Collections.singletonList(a1out.getId()));
        DoctorDto doctor = doctorController.getDoctor(doctorOut.getId());
        doctor.setTimeFrameIds(tfList);
        doctor.setAppointmentIds(appList);
        doctorController.updateDoctor(doctor);
        TimeFrameDto tf2mod = tfController.updateTimeFrame(new TimeFrameDto(
                tf2.getId(), LocalDate.of(2023, 6, 7).toString(), "off", "off", Present, doctorOut.getId()));
        // then
        assertEquals(Day_Off, tfController.getTimeFrame(tf2mod.getId()).getTfStatus());
    }

    @Test // does not work when appointment is in another day than tf; f. getAppsOutsideTf(tf.getId())
    public void testAppsOutsideTf() {// should work independently, from tfs.
        // ...after a little bit of thinking - no, actually it's correct; we can check particular day if all
        // appointments are framed correctly inside tf. Completely orphaned appointments should be checked by some
        // other function.

        // given
        Long msId = msController.getMedServices().get(0).getId();
        PatientDto pat = patientController.createPatient(new PatientDto(null, "pat", "Pat"));
        TimeFrameDto tf = tfController.createTimeFrame(new TimeFrameDto(
                null, LocalDate.of(2023, 9, 15).toString(), "09:00", "12:00", Present, doctorOut.getId()));
        LocalDateTime[] dateTime = {
                LocalDateTime.of(2023, 9, 15,  1, 0), LocalDateTime.of(2023, 9, 15, 1, 0),
                LocalDateTime.of(2023, 9, 15, 11, 0), LocalDateTime.of(2023, 9, 15, 12, 0),
                LocalDateTime.of(2023, 9, 15, 16, 0), LocalDateTime.of(2023, 9, 15, 17, 0)
        };
        List<Long> msList = Collections.singletonList(msId);
        List<Long> tfList = Collections.singletonList(tf.getId());
        List<Long> appList = IntStream.range(0, 4)
                .mapToObj(i -> appController.createAppointment(new AppointmentDto(
                        null, dateTime[i].toString(), 160, msId, doctorOut.getId(), pat.getId())).getId())
                .collect(Collectors.toList());
        // when
        doctorController.updateDoctor(
                new DoctorDto(doctorOut.getId(), "DocName", "DocLastname" , Board, false, tfList, appList, msList));
        List<AppointmentDto> appsOutsideTf = tfController.getAppsOutsideTf(tf.getId());
        // then
        assertEquals(2, appsOutsideTf.size());
    }
}
