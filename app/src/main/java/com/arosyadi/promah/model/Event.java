package com.arosyadi.promah.model;


import java.util.HashMap;
import java.util.Map;

public class Event{
    private String id;
    private String unit;
    private String name;
    private String dateStart;
    private String dateEnd;
    private String eventName;
    private String eventLoc;
    private String eventSpeaker;
    private String eventDesc;

    public Event(String id, String unit, String name, String dateStart, String dateEnd, String eventName, String eventLocation, String eventSpeaker, String eventDesc) {
        this.id = id;
        this.unit = unit;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.eventName = eventName;
        this.eventLoc = eventLocation;
        this.eventSpeaker = eventSpeaker;
        this.eventDesc = eventDesc;
    }

    public Event(String unit, String name, String dateStart, String dateEnd, String eventName, String eventLocation, String eventSpeaker, String eventDesc) {
        this.unit = unit;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
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

    public Map<String, Object> toMap(){
        HashMap<String, Object> results = new HashMap<>();

        results.put("unit", this.unit);
        results.put("name", this.unit);
        results.put("dateStart", this.unit);
        results.put("dateEnd", this.unit);
        results.put("eventName", this.unit);
        results.put("eventLoc", this.unit);
        results.put("eventSpeaker", this.unit);
        results.put("eventDesc", this.unit);

        return results;
    }


}
