package com.example.appointback;

import com.example.appointback.controller.DoctorController;

import com.example.appointback.controller.TestObjectRepository;
import com.example.appointback.entity.DoctorDto;
import com.example.appointback.entity.TestObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
public class DoctorIntegrationTests {

    @Autowired
    private DoctorController controller;

    @Autowired
    private TestObjectRepository testRepository;


    /*
    @Test
    public void testDoctorDto() {
        // given
        Long id = 180L;
        // when
        DoctorDto dto = controller.getDoctor(id);
        // then
        assertEquals(180L, dto.getId());
    }

    @Test
    public void testDoctorAppList() {
        // given
        Long id = 180L;
        // when
        DoctorDto dto = controller.getDoctor(id);
        // then
        assertTrue(3 < dto.getAppointmentIds().size());
    }

     */

    @Test
    public void testCreateTestObject() {
        // given
        TestObject to = new TestObject(1L, "done in test class");
        testRepository.save(to);
        // when
        TestObject found = testRepository.findById(1L).orElseThrow(IllegalArgumentException::new);
        // then
        assertEquals(1L, found.getId());
    }
}
