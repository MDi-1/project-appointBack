package com.example.appointback.controller;

import com.example.appointback.entity.TimeFrame;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TimeFrameRepository extends CrudRepository<TimeFrame, Long> {

    List<TimeFrame> findAll();
}
