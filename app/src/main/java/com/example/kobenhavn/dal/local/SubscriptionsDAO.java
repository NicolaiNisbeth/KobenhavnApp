package com.example.kobenhavn.dal.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Subscription;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface SubscriptionsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(Subscription subscription);

    @Delete
    void removeSubscription(Subscription toBeDeleted);

    @Query("SELECT * FROM subscriptions_table WHERE username = :username")
    LiveData<List<Subscription>> getSubscriptionsLive(String username);

}
