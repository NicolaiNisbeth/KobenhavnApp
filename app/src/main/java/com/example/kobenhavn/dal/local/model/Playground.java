package com.example.kobenhavn.dal.local.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

/**
 * Immutable POJO that represents a playground
 */
@Entity(tableName = "playground_table")
public class Playground implements Serializable, Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Playground createFromParcel(Parcel in){return new Playground(in);}
        public Playground[] newArray(int size){return new Playground[size];}
    };

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

    private Playground(Parcel in){
        this.id = in.readString();
        this.name = in.readString();
        this.imagepath = in.readString();
        //this.toiletPossibilities = in.readBoolean();
        //this.hasSoccerField = in.readBoolean();
        this.streetName = in.readString();
        this.streetNumber = in.readInt();
        this.commune = in.readString();
        this.zipCode = in.readInt();
        //this.syncPending = in.readBoolean();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playground that = (Playground) o;
        return toiletPossibilities == that.toiletPossibilities &&
                hasSoccerField == that.hasSoccerField &&
                streetNumber == that.streetNumber &&
                zipCode == that.zipCode &&
                syncPending == that.syncPending &&
                id.equals(that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(imagepath, that.imagepath) &&
                Objects.equals(streetName, that.streetName) &&
                Objects.equals(commune, that.commune);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imagepath, toiletPossibilities, hasSoccerField, streetName, streetNumber, commune, zipCode, syncPending);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(imagepath);
        //dest.writeBoolean(toiletPossibilities);
        //dest.writeBoolean(hasSoccerField);
        dest.writeString(streetName);
        dest.writeInt(streetNumber);
        dest.writeString(commune);
        dest.writeInt(zipCode);
        //dest.writeBoolean(syncPending);
    }
}
