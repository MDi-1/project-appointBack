package com.example.appointback;

import com.example.appointback.controller.*;
import com.example.appointback.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.appointback.entity.CalendarHolder.Position.Specialist;
import static com.example.appointback.entity.TimeFrame.TfStatus.Present;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class AppointmentIntegrationTests {

    @Autowired
    private AppointmentController appController;
    @Autowired
    private DoctorController doctorController;
    @Autowired
    private MedServiceController medServiceController;
    @Autowired
    private PatientController patientController;
    @Autowired
    private TimeFrameController tfController;
    private Long a1Id;
    private Long dId;
    private Long pId;
    private Long msId;

    @BeforeEach
    public void prepareDB() {
        msId = medServiceController.createMedService(new MedicalServiceDto("Physician", 200)).getId();
        dId = doctorController.createDoctor(new DoctorDto(
                null, "Doc", "Abc", Specialist, false, null, null, Collections.singletonList(msId))).getId();
        pId = patientController.createPatient(new PatientDto(null, "Pat", "Xyz", null)).getId();
        Long aId = appController.createAppointment(
                new AppointmentDto(null, "2023-03-03T09:00", 200, msId, dId, pId)).getId();
        a1Id = appController.createAppointment(new AppointmentDto(null,"2023-03-04T10:00",200,msId,dId,pId)).getId();
        Long tfId = tfController.createTimeFrame(
                new TimeFrameDto(null, "2023-03-03", "08:00", "16:00", Present, dId)).getId();
        doctorController.updateDoctor(new DoctorDto(
                dId, "Doc","Abc", Specialist, false, Collections.singletonList(tfId), Arrays.asList(aId, a1Id), null));
        patientController.updatePatient(new PatientDto(pId, "Pat", "Xyz", Arrays.asList(aId, a1Id)));
    }

    @Test
    public void testGetAppointments() {
        // given, when
        List<AppointmentDto> list = appController.getAllAppointments();
        // then
        assertEquals(2, list.size());
    }

    @Test
    public void testGetAppointment() {
        // given, when
        AppointmentDto appointmentDto = appController.getAppointment(a1Id);
        // then
        assertEquals("10:00", appointmentDto.getStartDateTime().substring(11, 16));
    }

    @Test
    public void testGetAppointmentsForDoc() {
        // given, when
        List<AppointmentDto> list = appController.getAppsForDoctor(dId);
        // then
        assertEquals(2, list.size());
    }

    @Test
    public void testGetAppsForPatient() {
        // given, when
        List<AppointmentDto> list = appController.getAppsForPatient(pId);
        // then
        assertEquals(2, list.size());
    }

    @Test
    public void testUpdateAppointment() {
        // given
        AppointmentDto appCreationResponse = appController.updateAppointment(
                new AppointmentDto(a1Id, "2023-03-04T10:00", 50, msId, dId, pId));
        // when
        AppointmentDto appointmentDto = appController.getAppointment(appCreationResponse.getId());
        // then
        assertEquals(50, appointmentDto.getPrice());
    }

    @Test
    public void testDeleteAppointment() {
        // given
        List<Long> tfIdList = tfController.getTimeFramesByDoctor(dId).stream()
                .map(TimeFrameDto::getId).collect(Collectors.toList());
        List<Long> idAppList = appController.getAllAppointments().stream()
                .map(AppointmentDto::getId).collect(Collectors.toList());
        // when
        idAppList.remove(a1Id);
        doctorController.updateDoctor(new DoctorDto(dId, "Doc", "Abc", Specialist, false, tfIdList, idAppList, null));
        patientController.updatePatient(new PatientDto(pId, "Pat", "Xyz", idAppList));
        appController.deleteAppointment(a1Id);
        // then
        assertEquals(1, appController.getAllAppointments().size());
    }
}
