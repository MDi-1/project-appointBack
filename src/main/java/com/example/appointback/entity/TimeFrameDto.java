package com.example.appointback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeFrameDto that = (TimeFrameDto) o;
        return Objects.equals(timeFrameDate, that.timeFrameDate) && Objects.equals(ownersId, that.ownersId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeFrameDate, ownersId);
    }

    @Override
    public String toString() {
        return "TimeFrameDto(" + "id=" + id + ", timeFrameDate='" + timeFrameDate + '\'' +
                ", timeStart='" + timeStart + '\'' + ", timeEnd='" + timeEnd + '\'' +
                ", tfStatus='" + tfStatus + '\'' + ", ownersId=" + ownersId + ")";
    }
}
