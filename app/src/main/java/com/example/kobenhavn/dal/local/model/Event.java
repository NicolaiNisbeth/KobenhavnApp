package com.example.kobenhavn.dal.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * Immutable POJO
 */
@Entity(tableName = "event_table")
public class Event implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "imagepath")
    private String imagepath;

    @ColumnInfo(name = "subtitle")
    private String subtitle;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "participants")
    private int participants;

    @ColumnInfo(name = "playground_name")
    private String playgroundName;

    @ColumnInfo(name = "details")
    private Details details;

    @ColumnInfo(name = "sync_pending")
    private boolean syncPending;

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getUsername() {
        return username;
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

    public Event(@NotNull String id, String username, String name, String imagepath, String subtitle, String description, int participants, String playgroundName, Details details, boolean syncPending) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.imagepath = imagepath;
        this.subtitle = subtitle;
        this.description = description;
        this.participants = participants;
        this.playgroundName = playgroundName;
        this.details = details;
        this.syncPending = syncPending;
    }

    @Ignore
    public Event(@NotNull String id, String username, String name, String imagepath, String subtitle, String description, int participants, String playgroundName, Details details) {
        this.id = id;
        this.username = username;
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
                ", username='" + username + '\'' +
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
