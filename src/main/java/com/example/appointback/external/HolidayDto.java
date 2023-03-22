package com.example.appointback.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayDto {

    @JsonProperty
    private String name;

    @JsonProperty
    private String date;

    @Override
    public String toString() {
        return "HolidayDto{" + "name='" + name + '\'' + ", date=" + date + '}';
    }
}
