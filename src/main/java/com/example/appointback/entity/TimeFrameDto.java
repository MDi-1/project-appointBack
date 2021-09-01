package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TimeFrameDto {
    
    private Long id;
    private String monStart;
    private Long monDuration;
    private String tueStart;
    private Long tueDuration;
    private String wedStart;
    private Long wedDuration;
    private String thuStart;
    private Long thuDuration;
    private String friStart;
    private Long friDuration;
    private Long doctorId;
}
