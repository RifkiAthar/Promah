package com.arosyadi.promah.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    private String unit;
    private String name;
    private String eventName;
    private String dateStart;
    private String dateEnd;
    private String eventLocation;

    protected Event(Parcel in) {
        unit = in.readString();
        name = in.readString();
        eventName = in.readString();
        dateStart = in.readString();
        dateEnd = in.readString();
        eventLocation = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(unit);
        parcel.writeString(name);
        parcel.writeString(eventName);
        parcel.writeString(dateStart);
        parcel.writeString(dateEnd);
        parcel.writeString(eventLocation);
    }
}
