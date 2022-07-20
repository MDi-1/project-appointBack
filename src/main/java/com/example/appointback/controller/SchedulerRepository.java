package com.example.appointback.controller;

import com.example.appointback.entity.Scheduler;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SchedulerRepository extends CrudRepository<Scheduler, Integer> {

    List<Scheduler> findAll();
}
