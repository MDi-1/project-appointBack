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
import java.util.stream.Collectors;

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
        DoctorDto dOut = doctorController.createDoctor(new DoctorDto(null, "", "", "Specialist"));
        TimeFrameDto tfDto = new TimeFrameDto(null, "2023-03-03", "08:00", "16:00", "Present", dOut.getId());
        TimeFrameDto tfOut = tfController.createTimeFrame(tfDto);
        // when
        List<Long> tfList = new ArrayList<>(Collections.singletonList(tfOut.getId()));
        DoctorDto dDto = new DoctorDto(dOut.getId(), "Doc","McDoctough","Specialist", tfList, null, null);
        DoctorDto dOut2 = doctorController.updateDoctor(dDto);
        List<TimeFrameDto> resultList = tfController.getTimeFramesByDoctor(dOut2.getId());
        // then
        assertEquals(1, resultList.size());
    }

    @Test
    public void testTfUpdate() {
        // given
        DoctorDto dOut = doctorController.createDoctor(new DoctorDto(null, "", "", "Specialist"));
        TimeFrameDto tfDto = new TimeFrameDto(null, "2023-03-03", "08:00", "16:00", "Present", dOut.getId());
        TimeFrameDto tf = tfController.createTimeFrame(tfDto);
        // when
        List<Long> tfList = new ArrayList<>(Collections.singletonList(tf.getId()));
        DoctorDto dDto = new DoctorDto(dOut.getId(), "Doc","McDoctough","Specialist", tfList, null, null);
        DoctorDto dOut2 = doctorController.updateDoctor(dDto);
        TimeFrameDto testInput = new TimeFrameDto(tf.getId(), "2023-03-04", "6:00", "9:00", "Present", dOut2.getId());
        TimeFrameDto result = tfController.updateTimeFrame(testInput);
        // then
        assertEquals("2023-03-04", result.getTimeFrameDate());
    }

    @Test
    public void testTfDelete() {
        // given
        DoctorDto d = doctorController.createDoctor(new DoctorDto(null, "Abc", "Xyz", "Specialist"));
        TimeFrameDto tfDto = new TimeFrameDto(null, "2023-03-03", "08:00", "16:00", "Present", d.getId());
        TimeFrameDto tfOut = tfController.createTimeFrame(tfDto);
        System.out.println(" >>>> " + doctorController.getDoctor(d.getId()));
                System.out.println(" >>>>>>>>>>>>> tf : " + tfController.getTimeFrames());
        // when
                List<Long> tfList = new ArrayList<>(Arrays.asList(tfOut.getId()));
                DoctorDto dUpdatedDto = new DoctorDto(d.getId(), "FFF","LLL","Board", tfList, null, null);
                DoctorDto dResult = doctorController.updateDoctor(dUpdatedDto);
                System.out.println(" >>>>>>>>>>>>> doc: " + dResult);
                DoctorDto refreshed = doctorController.getDoctors().stream().findAny().orElse(null);
                System.out.println(" >>>>>> refreshed doc:" + refreshed + "\n" +
                        " >>>>>> got tf from repo before: " + tfRepository.findAll() + "\n"
                );
        tfController.deleteTimeFrame(2L);
        System.out.println(" >>>>>> refreshed doc after del:" + refreshed + "\n" +
        " >>>>>> got tf from repo after: " + "\n" +
        " >>>>>> got tf from ctr : " + tfController.getTimeFrame(2L) + "\n" );
        // then
        assertEquals(0, tfController.getTimeFrames().size());
        assertEquals("Abc", doctorController.getDoctor(d.getId()).getName());
    }

    @Test
    public void testAutoCreation() {
        // given
        DoctorDto doctor = doctorController.createDoctor(new DoctorDto(null, "Abc", "Xyz", "Specialist"));
        String currentDate = LocalDate.now().plusWeeks(1L).toString();
        TimeFrameDto tfDto = new TimeFrameDto(null, currentDate, "08:00", "16:00", "Present", doctor.getId());
        tfController.createTimeFrame(tfDto);
        // when
        tfController.autoCreateTimeFrames();
        long count = tfRepository.findTimeFrameByDoc(doctor.getId()).size();
        // then
        assertEquals(30, count);
    }

    @Test
    public void testAppointmentDropout() {
        // given
        DoctorDto doctor = doctorController.createDoctor(new DoctorDto(null, "Abc", "Xyz", "Specialist"));
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
        doctorController.updateDoctor(new DoctorDto(doctor.getId(), "Abc","Xyz","Board", tfList, appList, null));
        TimeFrameDto tf2mod = tfController.updateTimeFrame(new TimeFrameDto(
                tf2.getId(), LocalDate.of(2023, 3, 7).toString(), "off", "off", "aaa", doctor.getId()));

        System.out.println(" >>>> appointment: " + a1out + "\n");
        DoctorDto dto = doctorController.getDoctor(doctor.getId());
        System.out.println(" >>>> doctor dto: " + dto + "\n");
        Doctor d = doctorRepository.findById(doctor.getId()).orElse(null);
        System.out.println(" >>>> doctor: " + d + "\n");
        // then
        assertEquals("Day_Off", tfController.getTimeFrame(tf2mod.getId()).getStatus());
    }
}
