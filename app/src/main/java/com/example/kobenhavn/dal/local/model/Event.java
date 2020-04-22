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
    private boolean syncPending;

    // constructor for when syncing is successful
    public Event(String id, String name, String imagepath, String subtitle, String description, int participants, String playgroundName, Details details, boolean syncPending) {
        this.id = id;
        this.name = name;
        this.imagepath = imagepath;
        this.subtitle = subtitle;
        this.description = description;
        this.participants = participants;
        this.playgroundName = playgroundName;
        this.details = details;
        this.syncPending = syncPending;
    }

    // constructor
    public Event(String id, String name, String imagepath, String subtitle, String description, int participants, String playgroundName, Details details) {
        this.id = id;
        this.name = name;
        this.imagepath = imagepath;
        this.subtitle = subtitle;
        this.description = description;
        this.participants = participants;
        this.playgroundName = playgroundName;
        this.details = details;
        this.syncPending = true;
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
                ", syncPending=" + syncPending +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagepath() {
        return imagepath;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }

    public int getParticipants() {
        return participants;
    }

    public String getPlaygroundName() {
        return playgroundName;
    }

    public Details getDetails() {
        return details;
    }

    public boolean isSyncPending() {
        return syncPending;
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
