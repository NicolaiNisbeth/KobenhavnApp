package com.example.kobenhavn.dal.local.model;

import java.util.Date;

public class Event {
    private String id;
    private String name;
    private String imagepath;
    private Date date;
    private String subtitle;
    private String time;
    private String description;
    private int participants;
    private String playgroundName;

    public Event(String id, String name, String imagepath, Date date, String subtitle, String time, String description, int participants, String playgroundName) {
        this.id = id;
        this.name = name;
        this.imagepath = imagepath;
        this.date = date;
        this.subtitle = subtitle;
        this.time = time;
        this.description = description;
        this.participants = participants;
        this.playgroundName = playgroundName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public String getPlaygroundName() {
        return playgroundName;
    }

    public void setPlaygroundName(String playgroundName) {
        this.playgroundName = playgroundName;
    }
}
