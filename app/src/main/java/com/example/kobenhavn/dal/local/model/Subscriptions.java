package com.example.kobenhavn.dal.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "subscriptions_table")
public class Subscriptions implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    private String userID;

    @ColumnInfo(name = "subscriptions")
    private List<Playground> subscriptions = new ArrayList<>();

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<Playground> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Playground> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Subscriptions(String userID, List<Playground> subscriptions) {
        this.userID = userID;
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return "Subscriptions{" +
                "userID='" + userID + '\'' +
                ", subscriptions=" + subscriptions +
                '}';
    }
}
