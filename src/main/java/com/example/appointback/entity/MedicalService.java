package com.example.appointback.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
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
                        // precision of BigDecimal is irrelevant here since this project is just for educational
    @Column(name = "PRICE") // purpose no one in this imaginary clinic would price services more precisely than tens of
    private int price; // currency units, so 'int' type is more than good enough.

    @ManyToMany(mappedBy = "medicalServices")
    private List<Doctor> doctors;

    public MedicalService(String serviceName, String description, int price) {
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
    }
}
