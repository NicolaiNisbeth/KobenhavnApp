package com.example.kobenhavn.dal.local.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PlaygroundModel implements Parcelable {
    private String title, address, description;
    private ArrayList assignedPedagogues;
    private String imagePath;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PlaygroundModel createFromParcel(Parcel in) {
            return new PlaygroundModel(in);
        }
        public PlaygroundModel[] newArray(int size) {
            return new PlaygroundModel[size];
        }
    };

    private PlaygroundModel(Parcel in){
        this.title = in.readString();
        this.address = in.readString();
        this.description = in.readString();
        this.imagePath = in.readString();
        this.assignedPedagogues =  in.readArrayList(String.class.getClassLoader());
    }

    public PlaygroundModel(String title, String address, String description, ArrayList<String> assignedPedagogues, String imagePath) {
        this.title = title;
        this.address = address;
        this.description = description;
        this.assignedPedagogues = assignedPedagogues;
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList getAssignedPedagogues() {
        return assignedPedagogues;
    }

    public void setAssignedPedagogues(ArrayList<String> assignedPedagogues) {
        this.assignedPedagogues = assignedPedagogues;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.address);
        dest.writeString(this.description);
        dest.writeString(this.description);
        dest.writeString(this.imagePath);
        dest.writeList(this.assignedPedagogues);
    }
}
