package com.example.kobenhavn.dal.local;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.kobenhavn.dal.local.converter.DetailsTimeConverter;
import com.example.kobenhavn.dal.local.converter.EventConverter;
import com.example.kobenhavn.dal.local.converter.PhoneNumbersConverter;
import com.example.kobenhavn.dal.local.converter.PlaygroundConverter;
import com.example.kobenhavn.dal.local.converter.PlaygroundsIDConverter;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.local.model.Playground;

@androidx.room.Database(entities = {Playground.class, User.class}, version = 2, exportSchema = false)
@TypeConverters({PlaygroundConverter.class, EventConverter.class, DetailsTimeConverter.class, PlaygroundsIDConverter.class, PhoneNumbersConverter.class})
public abstract class Database extends RoomDatabase {
    private static Database instance;
    private static String DB_NAME = "offlinedb";

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), Database.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract PlaygroundDAO playgroundDAO();

    public abstract UserDAO userDAO();
}
