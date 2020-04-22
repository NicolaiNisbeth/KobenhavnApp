package com.example.kobenhavn.dal.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.kobenhavn.dal.local.model.Subscriptions;

@Dao
public interface SubscriptionsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(Subscriptions subscriptions);

    @Query("SELECT * FROM subscriptions_table WHERE username = :username")
    LiveData<Subscriptions> getSubscriptionsLive(String username);

}
