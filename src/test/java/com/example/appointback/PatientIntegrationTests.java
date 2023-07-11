package com.example.appointback;

import com.example.appointback.controller.*;
import com.example.appointback.entity.AppointmentDto;
import com.example.appointback.entity.DoctorDto;
import com.example.appointback.entity.MedicalServiceDto;
import com.example.appointback.entity.PatientDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.example.appointback.entity.CalendarHolder.Position.Specialist;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class PatientIntegrationTests {

    @Autowired
    private AppointmentController appointmentController;
    @Autowired
    private PatientController patientController;
    @Autowired
    private DoctorController doctorController;
    @Autowired
    private MedServiceController msController;

    @Test
    public void testPatientCreation() {
        // given
        PatientDto patDto = new PatientDto(null, "abc", "xyz");
        // when
        PatientDto creationOutput = patientController.createPatient(patDto);
        // then
        assertEquals("abc", creationOutput.getFirstName());
    }

    @Test
    public void testPatientRemoval() {
        // given
        MedicalServiceDto ms = msController.createMedService(new MedicalServiceDto("Laryngologist", 200));
        PatientDto p = patientController.createPatient(new PatientDto(null, "abc", "xyz"));
        DoctorDto d = doctorController.createDoctor(new DoctorDto(
                null, "doc", "Doc", Specialist, false, null, null, Collections.singletonList(ms.getId())));
        AppointmentDto appOut = appointmentController.createAppointment(new AppointmentDto(
                null, "2023-03-03T09:00", ms.getPrice(), ms.getId(), d.getId(), p.getId()));
        // when
        PatientDto updatedDto = new PatientDto(p.getId(), "pat", "Pat", Collections.singletonList(appOut.getId()));
        PatientDto result = patientController.updatePatient(updatedDto);
        patientController.deletePatient(p.getId());
        // then
        assertAll(
                () -> assertEquals(0, patientController.getPatients().size()),
                () -> assertEquals(0, appointmentController.getAllAppointments().size()),
                () -> assertEquals(1, doctorController.getDoctors().size())
        );
    }

    @Test
    public void testPatientRetrieval() {
        // given
        patientController.createPatient(new PatientDto(null, "a", "A"));
        patientController.createPatient(new PatientDto(null, "b", "B"));
        patientController.createPatient(new PatientDto(null, "c", "C"));
        // when
        List<PatientDto> list = patientController.getPatients();
        Long midId = list.stream().filter(e -> e.getFirstName().equals("b")).findAny().orElse(null).getId();
        Long lastId = list.stream().map(PatientDto::getId).sorted(Collections.reverseOrder()).findFirst().orElse(null);
        // then
        assertAll(() -> assertEquals(3, list.size()), () -> assertTrue(midId < lastId));
    }

    @Test
    public void testGetPatientById() {
        // given
        PatientDto patient = patientController.createPatient(new PatientDto(null, "a", "A"));
        // when
        PatientDto dtoReceived = patientController.getPatient(patient.getId());
        // then
        assertEquals("a", dtoReceived.getFirstName());
    }

    @Test
    public void testPatientUpdate() {
        // given
        MedicalServiceDto ms = msController.createMedService(new MedicalServiceDto("Laryngologist", 200));
        PatientDto pat = patientController.createPatient(new PatientDto(null, "abc", "xyz"));
        DoctorDto doc = doctorController.createDoctor(new DoctorDto(
                null, "doc", "Doc", Specialist, false, null, null, Collections.singletonList(ms.getId())));
        AppointmentDto aIn = new AppointmentDto(null, "2023-03-03T09:00", 200, ms.getId(), doc.getId(), pat.getId());
        AppointmentDto aOut = appointmentController.createAppointment(aIn);
        // when
        PatientDto updatedDto = new PatientDto(pat.getId(), "pat", "Pat", Collections.singletonList(aOut.getId()));
        PatientDto result = patientController.updatePatient(updatedDto);
        // then
        assertEquals("pat", result.getFirstName());
    }
}
