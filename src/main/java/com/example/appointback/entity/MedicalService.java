package com.example.appointback.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
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

    @ManyToMany(targetEntity = com.example.appointback.entity.Doctor.class,
            cascade = { CascadeType.PERSIST, CascadeType.DETACH,
                                     CascadeType.MERGE, CascadeType.REFRESH },
            fetch = FetchType.LAZY,
            mappedBy = "medicalServices")
    private List<Doctor> doctors;

    public MedicalService(String serviceName, String description) {
        this.serviceName = serviceName;
        this.description = description;
    }
}
