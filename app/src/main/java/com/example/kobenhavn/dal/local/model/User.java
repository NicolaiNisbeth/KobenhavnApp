package com.example.kobenhavn.dal.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */

@Entity(tableName = "user_table")
public class User implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "firstname")
    private String firstname;

    @ColumnInfo(name = "lastname")
    private String lastname;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "imagepath")
    private String imagepath;

    @ColumnInfo(name = "phone_number")
    private String phonenumber;

    @ColumnInfo(name = "sync_pending")
    private boolean syncPending;

    @ColumnInfo(name = "subscribed_playgrounds")
    private List<Playground> subscribedPlaygrounds = new ArrayList<>();

    @ColumnInfo(name = "events")
    private ArrayList<Event> events = new ArrayList<>();

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public List<Playground> getSubscribedPlaygrounds() {
        return subscribedPlaygrounds;
    }

    public void setSubscribedPlaygrounds(List<Playground> subscribedPlaygrounds) {
        this.subscribedPlaygrounds = subscribedPlaygrounds;
    }



    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getImagepath() {
        return imagepath;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public boolean isSyncPending() {
        return syncPending;
    }


    //private Set<Event> events = new HashSet<>();

    //private Set<String> playgroundIDs = new HashSet<>();


    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setSyncPending(boolean syncPending) {
        this.syncPending = syncPending;
    }

    @Ignore
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Ignore
    public User(String firstname, String lastname, String username, String password, String email, String imagepath, String phonenumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.imagepath = imagepath;
        this.phonenumber = phonenumber;
        this.syncPending = true;
    }


    public User(@NonNull String id, String firstname, String lastname, String username, String password, String email, String imagepath, String phonenumber, boolean syncPending, List<Playground> subscribedPlaygrounds, ArrayList<Event> events) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.imagepath = imagepath;
        this.phonenumber = phonenumber;
        this.syncPending = syncPending;
        this.subscribedPlaygrounds = subscribedPlaygrounds;
        this.events = events;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", imagepath='" + imagepath + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", syncPending=" + syncPending +
                ", subscribedPlaygrounds=" + subscribedPlaygrounds +
                '}';
    }
}
