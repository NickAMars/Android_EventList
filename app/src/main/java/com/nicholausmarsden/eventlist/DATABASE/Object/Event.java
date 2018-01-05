package com.nicholausmarsden.eventlist.DATABASE.Object;

/**
 * Created by nicho on 12/27/2017.
 */

public class Event {
    private int id;
    private String eventName;
    private String eventDesc;
    private int eventFlag; // rather a 0 or 1
    private String eventTime;


    public int getId() {return id;}
    public String getEventName() {return eventName;}
    public String getEventDesc() {return eventDesc;}
    public int getEventFlag() {return eventFlag;}
    public String getEventTime() {return eventTime;}

    public void setId(int id) {this.id = id;}
    public void setEventName(String eventName) {this.eventName = eventName;}
    public void setEventDesc(String eventDesc) {this.eventDesc = eventDesc;}
    public void setEventFlag(int eventFlag) {this.eventFlag = eventFlag;}
    public void setEventTime(String eventTime) {this.eventTime = eventTime;}
}
