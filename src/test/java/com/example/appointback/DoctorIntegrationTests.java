package com.example.appointback;

import com.example.appointback.controller.DoctorController;

import com.example.appointback.controller.DoctorRepository;
import com.example.appointback.controller.TestObjectRepository;
import com.example.appointback.entity.Doctor;
import com.example.appointback.entity.DoctorDto;
import com.example.appointback.entity.TestObject;
import com.example.appointback.entity.TimeFrame;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class DoctorIntegrationTests {

    @Autowired
    private DoctorController controller;

    @Autowired
    private DoctorRepository repository;

    @Autowired
    private TestObjectRepository testRepository;

    @Test
    public void doctorControllerTest() {
        // given
        Doctor doctor = new Doctor("docName", "docLastname", "Board");
        LocalDate date = LocalDate.of(2023, 4, 15);
        LocalTime timeStart = LocalTime.of(8, 0);
        LocalTime timeEnd = LocalTime.of(15, 0);
        TimeFrame timeFrame = new TimeFrame(date, timeStart, timeEnd, "Present", doctor);



        DoctorDto docDto = new DoctorDto(null, "docName", "docLastname", "Board");

        controller.createDoctor(docDto);
        // when
        DoctorDto dto = controller.getDoctors().stream().findFirst().orElseThrow(IllegalArgumentException::new);
        // then
        assertEquals(1L, dto.getId());
    }

}
