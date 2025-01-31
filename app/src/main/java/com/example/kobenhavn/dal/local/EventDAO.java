package com.example.kobenhavn.dal.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.kobenhavn.dal.local.model.Event;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface EventDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(Event event);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Event> events);

    @Delete
    void deleteEvent(Event event);

    @Query("SELECT * FROM event_table WHERE username = :username")
    LiveData<List<Event>> getEventsLive(String username);

    @Query("DELETE FROM event_table")
    Completable clearAll();
}
