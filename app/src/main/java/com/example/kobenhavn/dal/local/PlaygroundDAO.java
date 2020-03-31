package com.example.kobenhavn.dal.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kobenhavn.dal.local.model.Playground;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PlaygroundDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(Playground playground);

    @Update
    void update(Playground playground);

    @Delete
    void delete(Playground playground);

    @Query("SELECT * FROM playground")
    Flowable<List<Playground>> getPlaygrounds();

}
