package com.example.appointback;

import com.example.appointback.controller.AppointmentRepository;
import com.example.appointback.controller.DoctorRepository;
import com.example.appointback.controller.PatientRepository;
import com.example.appointback.entity.Appointment;
import com.example.appointback.entity.Doctor;
import com.example.appointback.entity.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AppointmentTestSuite {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    public void testSaveToDB() {
        // given
        Doctor doctor1 = new Doctor("first1", "last1", "position1");
        Doctor doctor2 = new Doctor("first2", "last2", "position2");
        Doctor doctor3 = new Doctor("first3", "last3", "position3");

        // when
        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);
        doctorRepository.save(doctor3);

        // then
        List<Doctor> doctors = doctorRepository.findAll();
        assertEquals(3, doctors.size());
        // cleanup
        doctorRepository.deleteAll();
    }

    @Test
    public void testSaveRelations() {
        // given
        Doctor doctor1 = new Doctor("first1", "last1", "position1");
        Doctor doctor2 = new Doctor("first2", "last2", "position2");
        Patient patient = new Patient("name", "surname");
        Appointment appt1 = new Appointment(LocalDateTime.of(2021, 12, 30, 22, 55), 30, doctor1, patient);
        Appointment appt2 = new Appointment(LocalDateTime.of(2022, 1, 2, 13, 45), 30, doctor2, patient);
        // when
        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);
        patientRepository.save(patient);
        appointmentRepository.save(appt1);
        appointmentRepository.save(appt2);
        doctor1.getAppointments().add(appt1);
        doctor2.getAppointments().add(appt2);
        patient.getAppointments().add(appt1);
        patient.getAppointments().add(appt2);
        // then
        List<Doctor> doctors = doctorRepository.findAll();
        assertEquals(2, doctors.size());
        // cleanup
        doctorRepository.deleteAll();
    }
}
