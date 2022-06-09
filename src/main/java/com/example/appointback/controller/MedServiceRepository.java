package com.example.appointback.controller;

import com.example.appointback.entity.MedicalService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedServiceRepository extends CrudRepository<MedicalService, Short> {

    List<MedicalService> findAll();
}
