package com.example.kobenhavn.dal.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.http.DELETE;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(User user);

    @Query("UPDATE user_table set firstname = :firstName, email = :email, phone_number = :phoneNumber, password = :password")
    void updateUserFields(String firstName, String email, String phoneNumber, String password);


    // TODO: how do we keep this when user is replaced
    @Query("UPDATE user_table SET subscribed_playgrounds = :subscribed WHERE username = :username")
    void updateSubscribedPlaygrounds(String username, List<Playground> subscribed);

    @Query("UPDATE user_table SET events = :enrolledEvents WHERE username = :username")
    void UpdateEnrolledEvents(String username, ArrayList<Event> enrolledEvents);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_table WHERE username = :username")
    LiveData<User> getUserLive(String username);

    @Query("SELECT * FROM user_table WHERE username = :username")
    Single<User> getUser(String username);

}
