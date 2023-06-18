package com.example.appointback.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayDto {

    @JsonProperty
    private String name;

    @JsonProperty
    private String date;

    @JsonProperty
    private String type;

    @Override
    public String toString() {
        return "HolidayDto{" + "name='" + name + '\'' + ", date=" + date + '}';
    }
}
