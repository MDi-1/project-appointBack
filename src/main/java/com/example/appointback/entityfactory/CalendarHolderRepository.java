package com.example.appointback.entityfactory;

import com.example.appointback.entity.CalendarHolder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CalendarHolderRepository extends CrudRepository<CalendarHolder, Long> {

    List<CalendarHolder> findAll();
}
