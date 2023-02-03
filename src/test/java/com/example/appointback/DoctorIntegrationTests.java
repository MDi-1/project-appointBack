package com.example.appointback;

import com.example.appointback.controller.DoctorController;

import com.example.appointback.entity.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class DoctorIntegrationTests {

    @Autowired
    private DoctorController controller;

    @Test
    public void doctorControllerTest() {
        // given
        Doctor doctor = new Doctor("docName", "docLastname", "Board");
        LocalDate date = LocalDate.of(2023, 4, 15);
        LocalTime timeStart = LocalTime.of(8, 0);
        LocalTime timeEnd = LocalTime.of(15, 0);
        TimeFrame timeFrame =
                new TimeFrame ( date, timeStart, timeEnd, "Present", doctor );



        DoctorDto docDto =new DoctorDto(null,"docName","docLastname","Board");
        controller.createDoctor(
                      new DoctorDto(null, "docName", "docLastname", "Board"));
        // when
        DoctorDto dto = controller.getDoctors().stream().findFirst()
                                  .orElseThrow(IllegalArgumentException::new);
        // then
        assertEquals(1L, dto.getId());
    }

}
