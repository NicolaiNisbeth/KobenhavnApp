package com.example.kobenhavn.dal.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.Objects;

/**
 * Immutable POJO
 */
@Entity(tableName = "subscriptions_table")
public class Subscription implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "subscriptions")
    private Playground playground;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Playground getPlayground() {
        return playground;
    }

    @Ignore
    public Subscription(String username, Playground playground) {
        this.username = username;
        this.playground = playground;
    }

    public Subscription(long id, String username, Playground playground) {
        this.id = id;
        this.username = username;
        this.playground = playground;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", playground=" + playground +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return username.equals(that.username) &&
                Objects.equals(playground.getId(), that.playground.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, playground);
    }
}
