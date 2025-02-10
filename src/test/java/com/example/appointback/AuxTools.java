package com.example.appointback;

import com.example.appointback.controller.*;
import org.springframework.beans.factory.annotation.Autowired;

public class AuxTools {

    @Autowired
    private DoctorController doctorController;
    @Autowired
    private PatientController patientController;
    @Autowired
    private AppointmentController appController;
    @Autowired
    private TimeFrameController timeFrameController;
    @Autowired
    private MedServiceController medServiceController;

    public void displayDbState() {
        doctorController.getDoctors().forEach(System.out::println);
        patientController.getPatients().forEach(System.out::println);
        appController.getAllAppointments().forEach(System.out::println);
        timeFrameController.getTimeFrames().forEach(System.out::println);
        medServiceController.getMedServices().forEach(System.out::println);
    }
}
