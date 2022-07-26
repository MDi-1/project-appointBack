package com.example.appointback.controller;

import com.example.appointback.entity.TimeFrame;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TimeFrameRepository extends CrudRepository<TimeFrame, Long> {

    List<TimeFrame> findAll();

    @Query
    List<TimeFrame> findTimeFrameByDoc(@Param("ID")int docId);
}
