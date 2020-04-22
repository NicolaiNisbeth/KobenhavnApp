package com.example.kobenhavn.dal.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import java.util.ArrayList;
import io.reactivex.Single;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(User user);

    @Query("UPDATE user_table set firstname = :firstName, email = :email, phone_number = :phoneNumber, password = :password")
    void updateUserFields(String firstName, String email, String phoneNumber, String password);


    @Query("SELECT * FROM user_table WHERE username = :username")
    LiveData<User> getUserLive(String username);
}
