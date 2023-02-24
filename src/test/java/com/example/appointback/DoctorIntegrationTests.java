package com.example.appointback;

import com.example.appointback.controller.*;
import com.example.appointback.entity.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class DoctorIntegrationTests {

    @Autowired
    private DoctorController doctorController;
    @Autowired
    private TimeFrameController tfController;
    @Autowired
    private AppointmentController appController;
    @Autowired
    private MedServiceController msController;
    @Autowired
    private PatientController patientController;
    @Autowired
    private SchedulerController schedulerController;

    @Test
    public void testFindNoOne() {
        // given, when
        int listSize = doctorController.getDoctors().size();
        // then
        assertEquals(0, listSize);
    }

    @Test
    public void testDoctorCreationUnrelated() {
        // given
        DoctorDto dto = new DoctorDto(null, "Doctress", "Doctoree", "Board");
        // when
        DoctorDto output = doctorController.createDoctor(dto);
        // then
        assertEquals("Doctress", output.getName());
    }

    @Test
    public void testDoctorCreationRelated() {
        // given
        DoctorDto dto = new DoctorDto(null, "Doc", "McDoctough", "Specialist", null, null, null);
        // when
        DoctorDto output = doctorController.createDoctor(dto);
        // then
        assertEquals("Doc", output.getName());
    }

    @Test
    public void testDoctorUpdate() {
        // given
        DoctorDto dOut = doctorController.createDoctor(new DoctorDto(null, "", "", "Specialist"));
        PatientDto pOut = patientController.createPatient(new PatientDto(null, "Pat", "Phatient", null));
        AppointmentDto appDto = new AppointmentDto(null, "2023-03-03T09:00", 160, dOut.getId(), pOut.getId());
        AppointmentDto aOut = appController.createAppointment(appDto);
        TimeFrameDto tfDto = new TimeFrameDto(null, "2023-03-03", "08:00", "16:00", "Present", dOut.getId());
        TimeFrameDto tfOut = tfController.createTimeFrame(tfDto);
        MedicalServiceDto msDto = new MedicalServiceDto(
                null,"Laryngologist", null, new ArrayList<>(Collections.singletonList(dOut.getId())));
        MedicalServiceDto msOut = msController.createMedService(msDto);
        // when
        List<Long> appList = new ArrayList<>(Collections.singletonList(aOut.getId()));
        List<Long> tfList = new ArrayList<>(Collections.singletonList(tfOut.getId()));
        List<Long> msList = new ArrayList<>(Collections.singletonList(msOut.getId()));
        DoctorDto dDto = new DoctorDto(null, "Doc","McDoctough","Specialist", tfList, appList, msList);
        DoctorDto docResult = doctorController.updateDoctor(dDto);
        // then
        assertEquals("McDoctough", docResult.getLastName());
    }

    @Test
    public void testListRetrieval() {
        // given, when
        doctorController.createDoctor(new DoctorDto());
        // then
        assertThrows(ConstraintViolationException.class,
                () -> doctorController.getDoctors().stream().findFirst().orElseThrow(IllegalArgumentException::new),
                "anticipated exception wasn't thrown");
    }

    @Test
    public void testRemoveDoctor() {
        // given
        DoctorDto doctorDto = new DoctorDto(null, "x", "X", "Manager");
        PatientDto patientDto = new PatientDto(null, "p", "P");
        SchedulerDto schedulerDto = new SchedulerDto(null, "Default_Scheduler");
        // when
        Long doctorId = doctorController.createDoctor(doctorDto).getId();
        Long patientId = patientController.createPatient(patientDto).getId();
        Long schedulerId = schedulerController.createScheduler(schedulerDto).getId();
        AppointmentDto appDto = new AppointmentDto(null, "2023-03-03T09:00", 160, doctorId, patientId);
        Long appId = appController.createAppointment(appDto).getId();
        DoctorDto updatedDoctor = new DoctorDto(doctorId,"x","X","Manager",null,Collections.singletonList(appId),null);
        doctorController.updateDoctor(updatedDoctor);
        Long foundDoctorId = doctorController.getDoctors().stream().findAny().orElse(null).getId();
        String txtOut = doctorController.deleteDoctor(foundDoctorId);
        System.out.println(txtOut);
        // then
        assertEquals(0, doctorController.getDoctors().size());
    }
}
