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
        MedicalServiceDto ms = msController.createMedService(new MedicalServiceDto("MS", 200));
        DoctorDto docIn = new DoctorDto("Abc", "Xyz", Collections.singletonList(ms.getId()));
        DoctorDto doctor = doctorController.createDoctor(docIn);
        // when
        MedicalServiceDto result = msController.getMedServices().stream()
                .findFirst().orElseThrow(IllegalArgumentException::new);
        // then
        assertEquals("MS", result.getServiceName());
    }

    @Test
    public void testMsUpdate() {
        // given
        DoctorDto doctor = doctorController.createDoctor(new DoctorDto("Abc", "Xyz", Specialist, false));
        List<Long> docList = new ArrayList<>(Collections.singletonList(doctor.getId()));
        MedicalServiceDto msDtoInput = new MedicalServiceDto(null, "MS1", "d", 180, null, new ArrayList<>());
        MedicalServiceDto creationResponse = msController.createMedService(msDtoInput);
        MedicalServiceDto dtoUpdate = new MedicalServiceDto(creationResponse.getId(),"MS1","desc",180, null, docList);
        MedicalServiceDto updatingResponse = msController.updateMedService(dtoUpdate);
        // when
        MedicalServiceDto result = msController.getMedService(creationResponse.getId());
        // then
        assertAll (() -> assertEquals("desc", result.getDescription()), () -> assertNotNull(updatingResponse));
    }
}
