package com.example.appointback;

import com.example.appointback.controller.*;
import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.Doctor;
import com.example.appointback.entity.Patient;
import com.example.appointback.entity.TimeFrame;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static com.example.appointback.controller.CoreConfiguration.getStartingDate;
import static com.example.appointback.entity.TimeFrame.TfStatus.Day_Off;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class ServiceIntegrationTests {

    @Autowired
    private MaintenanceController maintenanceController;
    @Autowired
    private AppointmentController appointmentController;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private TimeFrameRepository tfRepository;

    @Test
    public void test() {
        // given
        maintenanceController.sampleDataFeed();
        // when
        List<Appointment> list = appointmentRepository.findByPrice(150);
        // then
        assertEquals(3, list.size());
    }

    @Test
    public void testDbCheck() {
        // given
        maintenanceController.sampleDataFeed();
        List<Doctor> doctorList = doctorRepository.findAll();
        Doctor doc1 = doctorList.get(0);
        Doctor doc2 = doctorList.get(1);
        Patient pat = patientRepository.findAll().get(0);
        List<Appointment> appointmentList = Arrays.asList(
                new Appointment(LocalDateTime.of(getStartingDate().plusDays(0L), LocalTime.of(10, 0)), 150, doc1, pat),
                new Appointment(LocalDateTime.of(getStartingDate().plusDays(2L), LocalTime.of(12, 0)), 150, doc1, pat),
                new Appointment(LocalDateTime.of(getStartingDate().plusDays(3L), LocalTime.of(14, 0)), 160, doc1, pat),
                new Appointment(LocalDateTime.of(getStartingDate().plusDays(3L), LocalTime.of(8 , 0)), 200, doc2, pat)
        );
        // when
        appointmentList.forEach(appointmentController::clearWeekendCollision);
        appointmentRepository.saveAll(appointmentList);
        LocalDate someDate = getStartingDate().plusDays(1L);
        tfRepository.save(new TimeFrame(null, someDate, LocalTime.of(8, 0), LocalTime.of(15, 0), Day_Off, doc1));
        // then
        assertEquals(4, maintenanceController.dDbCheck());
    }
}
