package com.example.kobenhavn.dal.local.model;

public class EventModel {
    private String date, subtitle, title, time, description, interested;

    public EventModel(String date, String subtitle, String title, String time, String description, String interested) {
        this.date = date;
        this.subtitle = subtitle;
        this.title = title;
        this.time = time;
        this.description = description;
        this.interested = interested;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInterested() {
        return interested;
    }

    public void setInterested(String interested) {
        this.interested = interested;
    }
}
