package com.brz.system;

import java.util.List;

/**
 * Created by macro on 16/4/11.
 */
public class DownloadTime {

    private String type;
    private List<TimeSlot> timeslot;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TimeSlot> getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(List<TimeSlot> timeslot) {
        this.timeslot = timeslot;
    }
}
