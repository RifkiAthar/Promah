package com.arosyadi.promah.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Event{
    private String unit;
    private String name;
    private String dateStart;
    private String dateEnd;
    private String monthStart;
    private String monthEnd;
    private String eventName;
    private String eventLocation;
    private String eventSpeaker;

    public Event(String unit, String name, String dateStart, String dateEnd, String monthStart, String monthEnd, String eventName, String eventLocation, String eventSpeaker) {
        this.unit = unit;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.monthStart = monthStart;
        this.monthEnd = monthEnd;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventSpeaker = eventSpeaker;
    }

    public String getEventSpeaker() {
        return eventSpeaker;
    }

    public void setEventSpeaker(String eventSpeaker) {
        this.eventSpeaker = eventSpeaker;
    }

    public String getMonthStart() {
        return monthStart;
    }

    public void setMonthStart(String monthStart) {
        this.monthStart = monthStart;
    }

    public String getMonthEnd() {
        return monthEnd;
    }

    public void setMonthEnd(String monthEnd) {
        this.monthEnd = monthEnd;
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
