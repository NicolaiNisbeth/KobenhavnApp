package com.example.kobenhavn.dal.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kobenhavn.dal.local.model.User;

import io.reactivex.Single;
import retrofit2.http.DELETE;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long add(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_table WHERE username = :username")
    Single<User> getUser(String username);

}
