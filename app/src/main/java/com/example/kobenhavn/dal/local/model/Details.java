package com.example.kobenhavn.dal.local.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Details implements Parcelable {
    private Date date;
    private Date startTime;
    private Date endTime;

    public Details(Date date, Date startTime, Date endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private Details(Parcel in){
        this.date = new Date(in.readLong());
        this.startTime = new Date(in.readLong());
        this.endTime = new Date(in.readLong());
    }

    @Override
    public String toString() {
        return "Details{" +
                "date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Details createFromParcel(Parcel in){return new Details(in);}
        public Details[] newArray(int size){return new Details[size];}
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date.getTime());
        dest.writeLong(startTime.getTime());
        dest.writeLong(endTime.getTime());
    }



}
