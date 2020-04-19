package com.example.kobenhavn.dal.local.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Event implements Serializable {
    private String id;
    private String name;
    private String imagepath;
    private String subtitle;
    private String description;
    private int participants;
    private String playgroundName;
    private Details details;

    public Event(String id, String name, String imagepath, String subtitle, String description, int participants, String playgroundName, Details details) {
        this.id = id;
        this.name = name;
        this.imagepath = imagepath;
        this.subtitle = subtitle;
        this.description = description;
        this.participants = participants;
        this.playgroundName = playgroundName;
        this.details = details;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imagepath='" + imagepath + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", description='" + description + '\'' +
                ", participants=" + participants +
                ", playgroundName='" + playgroundName + '\'' +
                ", details=" + details +
                '}';
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
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

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imagepath, subtitle, description, participants, playgroundName, details);
    }
}
