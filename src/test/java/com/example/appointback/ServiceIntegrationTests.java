package com.example.appointback;

import com.example.appointback.controller.AppointmentRepository;
import com.example.appointback.controller.MaintenanceController;
import com.example.appointback.entity.Appointment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class ServiceIntegrationTests {

    @Autowired
    private MaintenanceController controller;
    @Autowired
    private AppointmentRepository repository;

    @Test
    public void test() {
        // given
        controller.sampleDataFeed();
        // when
        List<Appointment> list = repository.findByPrice(150);
        // then
        assertEquals(3, list.size());
    }
}
