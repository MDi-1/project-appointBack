package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SERVICES")
public class MedicalService {

    @Id
    @GeneratedValue
    @Column(name = "S_ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String serviceName;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY,
            mappedBy = "medicalServices")
    private List<Doctor> doctors = new ArrayList<>();

    public MedicalService(String serviceName, String description) {
        this.serviceName = serviceName;
        this.description = description;
    }
}
