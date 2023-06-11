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
    private TimeFrame.TfStatus tfStatus;
    private Long ownersId;

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setTfStatus(TimeFrame.TfStatus tfStatus) {
        this.tfStatus = tfStatus;
    }

    @Override
    public String toString() {
        return "TimeFrameDto{" + "id=" + id + ", timeFrameDate='" + timeFrameDate + '\'' +
                ", timeStart='" + timeStart + '\'' + ", timeEnd='" + timeEnd + '\'' + ", status='" + tfStatus + '\'' +
                ", ownersId=" + ownersId + '}';
    }
}
