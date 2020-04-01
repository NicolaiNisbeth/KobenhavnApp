package com.example.kobenhavn.dal.local.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.HashSet;
import java.util.Set;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private Long id;

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

    public Long getId() {
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

    public User(long id, String firstname, String lastname, String username, String password, String email, String imagepath, String phonenumber, boolean syncPending) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.imagepath = imagepath;
        this.phonenumber = phonenumber;
        this.syncPending = syncPending;
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
                '}';
    }
}
