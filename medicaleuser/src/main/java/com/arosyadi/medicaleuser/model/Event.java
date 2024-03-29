package com.arosyadi.medicaleuser.model;

public class Event{
    private String id;
    private String unit;
    private String name;
    private String dateStart;
    private String dateEnd;
    private String monthStart;
    private String monthEnd;
    private String eventName;
    private String eventLoc;
    private String eventSpeaker;
    private String eventDesc;

    public Event(String id, String unit, String name, String dateStart, String dateEnd, String monthStart, String monthEnd, String eventName, String eventLocation, String eventSpeaker, String eventDesc) {
        this.id = id;
        this.unit = unit;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.monthStart = monthStart;
        this.monthEnd = monthEnd;
        this.eventName = eventName;
        this.eventLoc = eventLocation;
        this.eventSpeaker = eventSpeaker;
        this.eventDesc = eventDesc;
    }

    public Event(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

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

    public String getEventLoc() {
        return eventLoc;
    }

    public void setEventLoc(String eventLoc) {
        this.eventLoc = eventLoc;
    }


}