package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TimeFrameDto {
    
    private long id;
    private String timeFrameDate;
    private String timeStart;
    private String timeEnd;
    private int doctorId;
}
