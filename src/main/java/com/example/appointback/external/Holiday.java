package com.example.appointback.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Holiday {

    @JsonProperty
    private String name;

    @JsonProperty
    private LocalDate date;
}
