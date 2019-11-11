package com.arosyadi.promah.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Event{
    private String unit;
    private String name;
    private String eventName;
    private String dateStart;
    private String dateEnd;
    private String eventLocation;

    public Event(String unit, String name, String eventName, String dateStart, String dateEnd, String eventLocation) {
        this.unit = unit;
        this.name = name;
        this.eventName = eventName;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.eventLocation = eventLocation;
    }

    public Event(){}




    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }


}
