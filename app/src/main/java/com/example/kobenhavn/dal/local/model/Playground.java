package com.example.kobenhavn.dal.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Immutable POJO that represents a playground
 */
@Entity(tableName = "playground_table")
public class Playground implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "imagePath")
    private String imagepath;

    @ColumnInfo(name = "toiletPossibilities")
    private boolean toiletPossibilities;

    @ColumnInfo(name = "hasSoccerField")
    private boolean hasSoccerField;

    @ColumnInfo(name = "streetName")
    private String streetName;

    @ColumnInfo(name = "streetNumber")
    private int streetNumber;

    @ColumnInfo(name = "commune")
    private String commune;

    @ColumnInfo(name = "zipCode")
    private int zipCode;

    @ColumnInfo(name = "sync_pending")
    private boolean syncPending;

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagepath() {
        return imagepath;
    }

    public boolean isToiletPossibilities() {
        return toiletPossibilities;
    }

    public boolean isHasSoccerField() {
        return hasSoccerField;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getCommune() {
        return commune;
    }

    public int getZipCode() {
        return zipCode;
    }

    public boolean isSyncPending() {
        return syncPending;
    }

    @Ignore
    public Playground(@NonNull String id, String name, String imagepath, boolean toiletPossibilities, boolean hasSoccerField, String streetName, int streetNumber, String commune, int zipCode) {
        this.id = id;
        this.name = name;
        this.imagepath = imagepath;
        this.toiletPossibilities = toiletPossibilities;
        this.hasSoccerField = hasSoccerField;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.commune = commune;
        this.zipCode = zipCode;
        this.syncPending = true;
    }


    public Playground(@NonNull String id, String name, String imagepath, boolean toiletPossibilities, boolean hasSoccerField, String streetName, int streetNumber, String commune, int zipCode, boolean syncPending) {
        this.id = id;
        this.name = name;
        this.imagepath = imagepath;
        this.toiletPossibilities = toiletPossibilities;
        this.hasSoccerField = hasSoccerField;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.commune = commune;
        this.zipCode = zipCode;
        this.syncPending = syncPending;
    }

    @Override
    public String toString() {
        return "Playground{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imagepath='" + imagepath + '\'' +
                ", toiletPossibilities=" + toiletPossibilities +
                ", hasSoccerField=" + hasSoccerField +
                ", streetName='" + streetName + '\'' +
                ", streetNumber=" + streetNumber +
                ", commune='" + commune + '\'' +
                ", zipCode=" + zipCode +
                ", syncPending=" + syncPending +
                '}';
    }
}
