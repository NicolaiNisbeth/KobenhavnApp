package com.example.kobenhavn.dal.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.kobenhavn.dal.local.model.Event;

import java.util.List;



@Dao
public interface EventDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(Event event);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Event> events);

    @Query("SELECT * FROM event_table WHERE username = :username")
    LiveData<List<Event>> getEventsLive(String username);

    @Delete
    void deleteEvent(Event event);
}
