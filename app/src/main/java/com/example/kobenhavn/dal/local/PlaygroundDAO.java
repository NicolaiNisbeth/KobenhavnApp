package com.example.kobenhavn.dal.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.kobenhavn.dal.local.model.Playground;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PlaygroundDAO  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(Playground playground);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllPlaygrounds(List<Playground> playgrounds);

    @Query("SELECT * FROM playground_table")
    Flowable<List<Playground>> getPlaygrounds();
}
