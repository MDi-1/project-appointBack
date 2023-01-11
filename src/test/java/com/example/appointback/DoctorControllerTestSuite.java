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
public class DoctorControllerTestSuite {

    @Autowired
    private DoctorController controller;

    @Autowired
    private TestObjectRepository testRepository;

    @Test
    public void testDoctorDto() {
        // given
        int id = 180;
        // when
        DoctorDto dto = controller.getDoctor(id);
        // then
        assertEquals(180, dto.getId());
    }

    @Test
    public void testDoctorAppList() {
        // given
        int id = 180;
        // when
        DoctorDto dto = controller.getDoctor(id);
        // then
        assertTrue(3 < dto.getAppointmentIds().size());
    }

    @Test
    public void testCreateTestObject() {
        // given
        TestObject to = new TestObject(5, "done during test");
        testRepository.save(to);
        // when
        TestObject found = testRepository.findByName("done during test").orElseThrow(IllegalArgumentException::new);
        // then
        assertEquals("done during test", found.getName());
    }
}
