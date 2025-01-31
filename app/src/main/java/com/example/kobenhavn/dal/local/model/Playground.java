package com.example.kobenhavn.dal.local.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Immutable POJO
 */
@Entity(tableName = "playground_table")
public class Playground implements Serializable, Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Playground createFromParcel(Parcel in){return new Playground(in);}
        public Playground[] newArray(int size){return new Playground[size];}
    };

    @ColumnInfo(name = "id")
    private String id;

    @PrimaryKey
    @NonNull
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

    @ColumnInfo(name = "events")
    private ArrayList<Event> events;

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

    public ArrayList<Event> getEvents() {
        return events;
    }

    @Ignore
    public Playground(@NonNull String id, @NotNull String name, String imagepath, boolean toiletPossibilities, boolean hasSoccerField, String streetName, int streetNumber, String commune, int zipCode, ArrayList<Event> events) {
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
        this.events = events;
    }

    public Playground(@NonNull String id, @NotNull String name, String imagepath, boolean toiletPossibilities, boolean hasSoccerField, String streetName, int streetNumber, String commune, int zipCode, boolean syncPending, ArrayList<Event> events) {
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
        this.events = events;
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
        this.events = in.readArrayList(Event.class.getClassLoader());
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
                ", events=" + events +
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
//        dest.writeList(events);
    }
}
