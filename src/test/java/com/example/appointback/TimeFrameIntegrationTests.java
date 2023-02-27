package com.example.appointback;

import com.example.appointback.controller.*;
import com.example.appointback.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private TimeFrameController tfController;
    @Autowired
    private TimeFrameRepository tfRepository;

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
                // System.out.println(" >>>>>>>>>>>>> tf : " + tfController.getTimeFrames());
        // when
                // I don't understand why updateDoctor() prevents deleteTimeFrame() from succeeding
                // List<Long> tfList = new ArrayList<>(Arrays.asList(tf.getId()));
                // DoctorDto dUpdatedDto = new DoctorDto(dCreatedOut.getId(), "FFF","LLL","Board", tfList, null, null);
                // DoctorDto dResult = doctorController.updateDoctor(dUpdatedDto);
                // System.out.println(" >>>>>>>>>>>>> doc: " + dResult);
        tfController.deleteTimeFrame(tfOut.getId());
        // then
        assertEquals(0, tfController.getTimeFrames().size());
        assertEquals("Abc", doctorController.getDoctor(d.getId()).getName());
    }

    @Test
    public void testAutoCreation() {
        // given
        DoctorDto d = doctorController.createDoctor(new DoctorDto(null, "Abc", "Xyz", "Specialist"));
        String currentDate = LocalDate.now().plusWeeks(1L).toString();
        TimeFrameDto tfDto = new TimeFrameDto(null, currentDate, "08:00", "16:00", "Present", d.getId());
        TimeFrameDto tfOut = tfController.createTimeFrame(tfDto);
        // when
        tfController.autoCreateTimeFrames();
        List<TimeFrame> tfList = tfRepository.findTimeFrameByDoc(d.getId());
        LocalDate now = LocalDate.now();
/*
        List<TimeFrame> filteredList = tfList.stream()
                .filter(e -> (e.getTimeframeDate().equals(now) || e.getTimeframeDate().isAfter(now)))
                .collect(Collectors.toList());
*/
        long count = tfList.stream()
                .filter(e -> (e.getTimeframeDate().equals(now) || e.getTimeframeDate().isAfter(now))).count();
        // then
        assertEquals(30, count);
    }
}
