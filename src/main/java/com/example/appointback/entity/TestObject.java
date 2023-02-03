package com.example.appointback.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * xyz
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "TEST_OBJECTS")
public class TestObject {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String name;
}
