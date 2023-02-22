package com.example.appointback;

import com.example.appointback.controller.DoctorController;
import com.example.appointback.controller.TimeFrameController;
import com.example.appointback.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class TimeFrameIntegrationTests {

    @Autowired
    private DoctorController doctorController;
    @Autowired
    private TimeFrameController tfController;

    @Test
    public void testTfCreate() {
        // given
        DoctorDto dOut = doctorController.createDoctor(new DoctorDto(null, "", "", "Specialist"));
        TimeFrameDto tfDto = new TimeFrameDto(null, "2023-03-03", "08:00", "16:00", "Present", dOut.getId());
        TimeFrameDto tfOut = tfController.createTimeFrame(tfDto);
        // when
        List<Long> tfList = new ArrayList<>(Collections.singletonList(tfOut.getId()));
        DoctorDto dDto = new DoctorDto(null, "Doc","McDoctough","Specialist", tfList, null, null);
        DoctorDto docResult = doctorController.updateDoctor(dDto);
        TimeFrameDto result = tfController.getTimeFramesByDoctor(
                dOut.getTimeFrameIds().stream().findFirst().orElseThrow(IllegalArgumentException::new));
        // then
        assertEquals("McDoctough", docResult.getLastName());
    }

    @Test
    public void testTfUpdate() {}

    @Test
    public void testTfDelete() {}
    }
