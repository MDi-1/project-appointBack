package com.example.appointback;

import com.example.appointback.controller.DoctorController;
import com.example.appointback.controller.DoctorRepository;
import com.example.appointback.controller.MedServiceController;
import com.example.appointback.entity.DoctorDto;
import com.example.appointback.entity.MedicalServiceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.appointback.entity.CalendarHolder.Position.Specialist;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class MedServiceIntegrationTests {

    @Autowired
    private MedServiceController msController;
    @Autowired
    private DoctorController doctorController;
    @Autowired
    private DoctorRepository docRepository;

    @Test
    public void testMsCreate() {
        // given
        DoctorDto doctor = doctorController.createDoctor(new DoctorDto(null, "Abc", "Xyz", Specialist, false));
        List<Long> docList = new ArrayList<>(Collections.singletonList(doctor.getId()));
        msController.createMedService(new MedicalServiceDto(null, "MS", "des", 200, docList));
        // when
        MedicalServiceDto result = msController.getMedServices()
                .stream().findFirst().orElseThrow(IllegalArgumentException::new);
        // then
        assertEquals("des", result.getDescription());
    }

    @Test
    public void testMsUpdate() {
        // given
        DoctorDto doctor = doctorController.createDoctor(new DoctorDto(null, "Abc", "Xyz", Specialist, false));
        List<Long> docList = new ArrayList<>(Collections.singletonList(doctor.getId()));
        MedicalServiceDto msDtoInput = new MedicalServiceDto(null, "MS1", "d", 180, new ArrayList<>());
        MedicalServiceDto creationResponse = msController.createMedService(msDtoInput);
        MedicalServiceDto dtoUpdate = new MedicalServiceDto(creationResponse.getId(), "MS1", "desc", 180, docList);
        MedicalServiceDto updatingResponse = msController.updateMedService(dtoUpdate);
        // when
        MedicalServiceDto result = msController.getMedService(creationResponse.getId());
        System.out.println(" ]]] printing medical services [[[: ");
        msController.getMedServices().forEach(System.out::println);
        // then
        assertAll (() -> assertEquals("desc", result.getDescription()), () -> assertNotNull(updatingResponse));
    }
}
