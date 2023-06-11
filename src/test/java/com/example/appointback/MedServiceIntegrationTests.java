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
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        msController.createMedService(new MedicalServiceDto(null, "MS", "des", docList));
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
        MedicalServiceDto m1 = msController.createMedService(new MedicalServiceDto(null, "s", "S", new ArrayList<>()));
        MedicalServiceDto m2 = msController.updateMedService(new MedicalServiceDto(m1.getId(), "s2", "desc", docList));
        // when
        MedicalServiceDto result = msController.getMedService(m2.getId());
        // then
        assertEquals("desc", result.getDescription());
    }
}
