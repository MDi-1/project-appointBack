package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TimeFrameDto {
    
    private Long id;
    private String timeFrameDate;
    private String timeStart;
    private String timeEnd;
    private String status;
    private Long doctorId;

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TimeFrameDto{" + "id=" + id + ", timeFrameDate='" + timeFrameDate + '\'' +
                ", timeStart='" + timeStart + '\'' + ", timeEnd='" + timeEnd + '\'' + ", status='" + status + '\'' +
                ", doctorId=" + doctorId + '}';
    }
}
