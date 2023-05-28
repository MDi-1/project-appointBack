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
@Entity(name = "PATIENTS")
public class Patient {

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id; // changed from int to Long; previously I thought there is separate id numbering for each entity
                    // but Hibernate by default assigns id numbers from one "pool" for all entities! this "pool"
                    // is hibernate_sequence table which has just one column "next_val" with one row.
    @NotNull
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @Column(name = "LAST_NAME")
    private String lastName;

    @OneToMany(targetEntity = Appointment.class, mappedBy = "patient",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    public Patient(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
// During bootcamp we have been learnt to use Long type for ID of entities but for some entities I decided to try
// other types like Integer and int.
//track of the lines properly.Using the same argument for indentation in the tabs vs spaces debate

