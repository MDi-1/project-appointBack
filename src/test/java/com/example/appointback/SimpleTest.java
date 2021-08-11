package com.example.appointback;

import com.example.appointback.controller.DoctorRepository;
import com.example.appointback.entity.Doctor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SimpleTest {

    @Autowired
    private DoctorRepository repository;

    @Test
    public void simpleTest() {
        // given
        Doctor doctor1 = new Doctor("first1", "last1", "position1");
        Doctor doctor2 = new Doctor("first2", "last2", "position2");
        Doctor doctor3 = new Doctor("first3", "last3", "position3");
        // when
        repository.save(doctor1);
        repository.save(doctor2);
        repository.save(doctor3);
        // then
        List<Doctor> doctors = repository.findAll();
        assertEquals(3, doctors.size());
        //cleanup
        repository.deleteAll();
    }
}
