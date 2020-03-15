package com.example.kobenhavn.ui.legepladser;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class LegepladsModel implements Parcelable {
    private String titel, adresse, beskrivelse;
    private ArrayList kontaktPersoner;
    private String img_url;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public LegepladsModel createFromParcel(Parcel in) {
            return new LegepladsModel(in);
        }

        public LegepladsModel[] newArray(int size) {
            return new LegepladsModel[size];
        }
    };

    private LegepladsModel(Parcel in){
        this.titel = in.readString();
        this.adresse = in.readString();
        this.beskrivelse = in.readString();
        this.img_url = in.readString();
        this.kontaktPersoner =  in.readArrayList(String.class.getClassLoader());
    }

    public LegepladsModel(String titel, String adresse, String beskrivelse, ArrayList<String> kontaktPersoner, String img_url) {
        this.titel = titel;
        this.adresse = adresse;
        this.beskrivelse = beskrivelse;
        this.kontaktPersoner = kontaktPersoner;
        this.img_url = img_url;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public ArrayList getKontaktPersoner() {
        return kontaktPersoner;
    }

    public void setKontaktPersoner(ArrayList<String> kontaktPersoner) {
        this.kontaktPersoner = kontaktPersoner;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.titel);
        dest.writeString(this.adresse);
        dest.writeString(this.beskrivelse);
        dest.writeString(this.beskrivelse);
        dest.writeString(this.img_url);
        dest.writeList(this.kontaktPersoner);
    }
}
