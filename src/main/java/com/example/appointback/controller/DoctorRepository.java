package com.example.appointback.controller;

import com.example.appointback.entity.CalendarHolder;
import com.example.appointback.entity.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<CalendarHolder, Long> {

    List<CalendarHolder> findAll();
}
