package com.example.appointback.external;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface HolidayRepository extends CrudRepository<HolidayDao, Long> {

    List<HolidayDao> findAll();
}
