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

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public String toString() {
        return "TimeFrameDto{" + "id=" + id + ", timeFrameDate='" + timeFrameDate + '\'' +
                ", timeStart='" + timeStart + '\'' + ", timeEnd='" + timeEnd + '\'' + ", doctorId=" + doctorId + '}';
    }
}
