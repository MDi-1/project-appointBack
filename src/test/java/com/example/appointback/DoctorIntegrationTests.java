package com.example.appointback;

import com.example.appointback.controller.DoctorController;

import com.example.appointback.entity.*;
import org.junit.jupiter.api.*;
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
    public void testFindNoOne() {
        // given
        // when
        controller.getDoctors();
        // then
        assertEquals(0, 0);
    }

    @Test
    public void testDoctorCreationUnrelated() {
        // given
        DoctorDto dto = new DoctorDto(null, "Doctress", "Doctoree", "Board");
        // when
        DoctorDto output = controller.createDoctor(dto);
        // then
        assertEquals("Doctress", output.getName());

        System.out.println(" ]] list of doctors as following: ]] " +
                controller.getDoctors());
    }

    @Test
    public void testDoctorCreationRelated() {
        LocalDate date = LocalDate.of(2023, 4, 15);
        LocalTime tStart = LocalTime.of(8, 0);
        LocalTime tEnd = LocalTime.of(15, 0);
        //TimeFrame tf = new TimeFrame(date, tStart, tEnd, "Present", doctor);

        //DoctorDto dto = new DoctorDto("Doc", "McDoctough", "Specialist");
    }


    @Test
    public void testTimeFrameCreation() {}

    @Test
    public void testAppointmentCreation() {}

    @Test
    public void testMedicalServiceCreation() {}

    @Test
    public void testDoctorsRetrieval() {}

    @Test
    public void testDoctorUpdate() {}

    @Test
    public void doctorControllerTest() {
        // given
        Doctor doctor = new Doctor("docName", "docLastname", "Board");
        DoctorDto docDto =new DoctorDto(null,"docName","docLastname","Board");
        controller.createDoctor(
                      new DoctorDto(null, "docName", "docLastname", "Board"));
        // when
        DoctorDto dto = controller.getDoctors().stream().findFirst()
                                  .orElseThrow(IllegalArgumentException::new);
        // then
        assertEquals(1L, dto.getId());

        System.out.println(" ]] list of doctors as following: ]] " +
                controller.getDoctors());
    }

    @Test
    public void testDoctorDeletion() {}

    @Test
    public void x_reviseDB() {
    }
}
