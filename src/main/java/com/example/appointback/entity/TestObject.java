package com.example.appointback.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * TestObject is an entity to try out variety of functionalities in this
 * project, since "Project Appoint" was in the past for the author the very
 * first application with this level of relationship complexity to learn
 * skills in backend and web app development.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "TEST_OBJECTS")
public class TestObject {

    public enum RankLabel { FIRST, SECOND, THIRD, FOURTH }

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "TRUE_OR_FALSE")
    private boolean trueOrFalse;

    @Enumerated(EnumType.STRING)
    private RankLabel rankLabel;
}
