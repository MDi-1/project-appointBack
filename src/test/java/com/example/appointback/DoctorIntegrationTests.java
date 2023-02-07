package com.example.appointback;

import com.example.appointback.controller.*;

import com.example.appointback.entity.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    // tmp

    @Autowired
    private PatientController pc;
    @Autowired
    private SchedulerController sc;
    @Autowired
    private TestObjectController tc;

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
        DoctorDto dto = new DoctorDto(
                2L, "Doc", "McDoctough", "Specialist", null, null, null);
        // when
        DoctorDto output = doctorController.createDoctor(dto);
        // then
        assertEquals(2L, output.getId());
    }

    // public void testTimeFrameCreation() {}

    // public void testAppointmentCreation() {}

    // public void testMedicalServiceCreation() {}

    // public void testDoctorsRetrieval() {}

    @Test
    public void testDoctorUpdate() {
        // given
        DoctorDto docInit = new DoctorDto(3L, "", "", "Specialist");
        TimeFrameDto tfDto =new TimeFrameDto(
                4L, "2023-03-03", "08:00", "16:00", "Present", 4L);
        AppointmentDto appDto = new AppointmentDto(
                7L, "2023-03-03T09:00", 160, 4L, 5L);
        MedicalServiceDto msDto = new MedicalServiceDto(
                8L,"Laryngologist", null, new ArrayList<>(Arrays.asList(4L)));
        List<Long> tfList = new ArrayList<>(Arrays.asList(tfDto.getId()));
        List<Long> appList = new ArrayList<>(Arrays.asList(appDto.getId()));
        List<Long> msList = new ArrayList<>(Arrays.asList(msDto.getId()));
        DoctorDto dDto = new DoctorDto(
                4L, "Doc","McDoctough","Specialist", tfList, appList, msList);
        PatientDto pDto = new PatientDto(5L, "Pat", "Phatient", appList);
        // when
        DoctorDto dOut = doctorController.createDoctor(docInit);
        System.out.println(" ]] doctor  written to db [[ " + dOut);
        PatientDto patOut = pc.createPatient(pDto);
        System.out.println(" ]] patient written to db [[ " + patOut);
        TimeFrameDto tfOut = tfController.createTimeFrame(tfDto);
        AppointmentDto aOut = appController.createAppointment(appDto);
        System.out.println(" ]] appoint written to db [[ " + aOut);
        MedicalServiceDto msOut = msController.createMedService(msDto);
        System.out.println(" ]] a list of apps: [[\n" +
                appController.getAllAppointments());
        DoctorDto docResult = doctorController.updateDoctor(dDto);

        System.out.println(" ]] all objects: [[ \n" +
                tfController.getTimeFrames() + "\n" +
                appController.getAllAppointments() + "\n" +
                msController.getMedServices() + "\n" + pc.getPatients() +
                "\n" + sc.getSchedulers()  + "\n" + tc.getTestObjects());
        // then
        assertEquals(4L, docResult.getId());
    }

    @Test
    public void doctorControllerTest() {
        // given
        Doctor doctor = new Doctor("docName", "docLastname", "Board");
        DoctorDto docDto =new DoctorDto(null,"docName","docLastname","Board");
        doctorController.createDoctor(
                      new DoctorDto(null, "docName", "docLastname", "Board"));
        // when
        DoctorDto dto = doctorController.getDoctors().stream().findFirst()
                                  .orElseThrow(IllegalArgumentException::new);
        // then
        assertEquals(1L, dto.getId());
    }

    @Test
    public void testDoctorDeletion() {}
}
