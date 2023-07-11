package com.example.appointback;

import com.example.appointback.controller.*;
import com.example.appointback.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static com.example.appointback.controller.CoreConfiguration.getPresentDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
public class ServiceIntegrationTests {

    @Autowired
    private MaintenanceController maintenanceController;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private MedServiceRepository medServiceRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private TimeFrameRepository tfRepository;

    @Test
    public void testSampleDataFeed() {
        // given
        maintenanceController.setTfExcessList(new ArrayList<>());
        maintenanceController.setAppExcessList(new ArrayList<>());
        maintenanceController.sampleDataFeed();
        // when
        List<Appointment> list = appointmentRepository.findByPrice(160);
        // then
        assertTrue(3 < list.size());
    }

    @Test
    public void testDbCheck() {
        // given
        maintenanceController.setTfExcessList(new ArrayList<>());
        maintenanceController.setAppExcessList(new ArrayList<>());
        MedicalService ms = medServiceRepository.save(new MedicalService("ms1", 200));
        Patient patient = patientRepository.save(new Patient("p", "P"));
        Doctor doctor = doctorRepository.save(new Doctor("x", "X", CalendarHolder.Position.Specialist, false));
        doctor.setMedicalServices(Collections.singletonList(ms));
        int initialListIterations = 6;
        IntStream.range(0, initialListIterations)
                .mapToObj(i -> new TimeFrame(null, getPresentDate().plusDays(i),
                        LocalTime.of(8, 0), LocalTime.of(16, 0), TimeFrame.TfStatus.Present, doctor))
                .forEach(tf -> tfRepository.save(tf));
        IntStream.range(0, initialListIterations)
                .mapToObj(i -> {
                    LocalDateTime dateTime = LocalDateTime.of(getPresentDate().plusDays(i), LocalTime.of(8 + i, 0));
                    return new Appointment(null, dateTime, ms.getPrice(), ms, doctor, patient);
                })
                .forEach(a -> appointmentRepository.save(a));
        // when
        int testListIterations = 3;
        IntStream.range(0, testListIterations)
                .mapToObj(i -> {
                    LocalDateTime dateTime = LocalDateTime.of(getPresentDate().plusDays(i), LocalTime.of(8 + i, 0));
                    return new Appointment(null, dateTime, 111, ms, doctor, patient);
                })
                .forEach(a -> appointmentRepository.save(a));
        // then
        assertEquals(3, maintenanceController.dDbCheck());
    }
}
