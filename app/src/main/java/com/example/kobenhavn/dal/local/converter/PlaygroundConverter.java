package com.example.kobenhavn.dal.local.converter;

import androidx.room.TypeConverter;

import com.example.kobenhavn.dal.local.model.Details;
import com.example.kobenhavn.dal.local.model.Playground;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlaygroundConverter {

    @TypeConverter
    public static Playground fromStringToPlayground(String value) {
        return new Gson().fromJson(value, Playground.class);
    }

    @TypeConverter
    public static String fromPlaygroundToString(Playground playground) {
        Gson gson = new Gson();
        return gson.toJson(playground);
    }


}
