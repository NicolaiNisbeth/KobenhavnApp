package com.example.kobenhavn.dal.local;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kobenhavn.dal.local.model.Playground;

@androidx.room.Database(entities = {Playground.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    private static Database instance;
    private static String DB_NAME = "offlinedb";

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), Database.class, DB_NAME)
                    .build();
        }
        return instance;
    }

    public abstract PlaygroundDAO playgroundDAO();
}
