package com.example.appointback.controller;

import com.example.appointback.entity.CalendarHolder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CHRepository extends CrudRepository<CalendarHolder, Long> {

    List<CalendarHolder> findAll();
}
