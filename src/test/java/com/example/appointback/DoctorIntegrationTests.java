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

import static com.example.appointback.entity.CalendarHolder.Position.*;
import static com.example.appointback.entity.TimeFrame.TfStatus.Present;
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
        DoctorDto dto = new DoctorDto(null, "Doctress", "Doctoree", Board, false);
        // when
        DoctorDto output = doctorController.createDoctor(dto);
        // then
        assertEquals("Doctress", output.getName());
    }

    @Test
    public void testDoctorCreationAllArgConstructor() {
        // given
        DoctorDto dto = new DoctorDto(null, "Doc", "McDoctough", Specialist, true, null, null, null);
        // when
        DoctorDto response = doctorController.createDoctor(dto);
        DoctorDto doctorFound = doctorController.getDoctor(response.getId());
        // then
        assertEquals("Doc", doctorFound.getName());
    }

    @Test
    public void testDoctorUpdate() {
        // given
        DoctorDto dOut = doctorController.createDoctor(new DoctorDto(null, "", "", Specialist, false));
        PatientDto pOut = patientController.createPatient(new PatientDto(null, "Pat", "Phatient", null));
        AppointmentDto appDto = new AppointmentDto(null, "2023-03-03T09:00", 160, dOut.getId(), pOut.getId());
        AppointmentDto aOut = appController.createAppointment(appDto);
        TimeFrameDto tfDto = new TimeFrameDto(null, "2023-03-03", "08:00", "16:00", Present, dOut.getId());
        TimeFrameDto tfOut = tfController.createTimeFrame(tfDto);
        MedicalServiceDto msDto = new MedicalServiceDto(
                null,"Laryngologist", null, new ArrayList<>(Collections.singletonList(dOut.getId())));
        MedicalServiceDto msOut = msController.createMedService(msDto);
        // when
        List<Long> appList = new ArrayList<>(Collections.singletonList(aOut.getId()));
        List<Long> tfList = new ArrayList<>(Collections.singletonList(tfOut.getId()));
        List<Long> msList = new ArrayList<>(Collections.singletonList(msOut.getId()));
        DoctorDto dDto = new DoctorDto(null, "Doc", "McDoctough", Specialist, false, tfList, appList, msList);
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
                "anticipated exception wasn't thrown"
        );
    }

    @Test
    public void testRemoveDoctor() {
        // given
        DoctorDto doctor1 = doctorController.createDoctor(new DoctorDto(null, "x", "X", Manager, false));
        DoctorDto dto2input = new DoctorDto(null, "y", "Y", Specialist, false, null, new ArrayList<>(), null);
        DoctorDto doctor2 = doctorController.createDoctor(dto2input);
        PatientDto patient = patientController.createPatient(new PatientDto(null, "pat", "Pat"));
        schedulerController.createScheduler(new SchedulerDto(null, "Default_Scheduler"));
        // when
        AppointmentDto appDto = new AppointmentDto(null, "2023-03-03T09:00", 160, doctor1.getId(), patient.getId());
        AppointmentDto appointmentCreationResponse = appController.createAppointment(appDto);
        List<Long> appointmentIdList = Collections.singletonList(appointmentCreationResponse.getId());
        DoctorDto updDoc = new DoctorDto(doctor1.getId(), "Abc", "Xyz", Manager, false, null, appointmentIdList, null);
        DoctorDto docUpdateResponse = doctorController.updateDoctor(updDoc);
        String deletionResponse1 = doctorController.deleteDoctor(docUpdateResponse.getId());
        String deletionResponse2 = doctorController.deleteDoctor(doctor2.getId());
        // then
        assertAll (
                () -> assertEquals(0, doctorController.getDoctors().size()),
                () -> assertNull(deletionResponse1),
                () -> assertNull(deletionResponse2)
        );
    }
}
