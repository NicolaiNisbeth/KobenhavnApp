package com.example.kobenhavn.dal.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.DELETE;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long add(User user);

    @Update
    void update(User user);

    @Query("UPDATE user_table SET subscribed_playgrounds = :subscribed WHERE username = :username")
    void update(String username, List<Playground> subscribed);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_table WHERE username = :username")
    Single<User> getUser(String username);

}
