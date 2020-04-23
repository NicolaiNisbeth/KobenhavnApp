package com.example.kobenhavn.dal.local;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.kobenhavn.dal.local.converter.DetailsConverter;
import com.example.kobenhavn.dal.local.converter.PlaygroundConverter;
import com.example.kobenhavn.dal.local.converter.TimeConverter;
import com.example.kobenhavn.dal.local.converter.EventConverter;
import com.example.kobenhavn.dal.local.converter.PlaygroundListConverter;
import com.example.kobenhavn.dal.local.converter.StringListConverter;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Subscription;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.local.model.Playground;

@androidx.room.Database(entities = {Playground.class, User.class, Subscription.class, Event.class}, version = 1, exportSchema = false)
@TypeConverters({PlaygroundListConverter.class, EventConverter.class, StringListConverter.class, TimeConverter.class, DetailsConverter.class, PlaygroundConverter.class})
public abstract class Database extends RoomDatabase {
    private static Database instance;
    private static String DB_NAME = "offlinedb";
    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public abstract PlaygroundDAO playgroundDAO();
    public abstract UserDAO userDAO();
    public abstract SubscriptionsDAO subscriptionsDAO();
    public abstract EventDAO eventDAO();
}
