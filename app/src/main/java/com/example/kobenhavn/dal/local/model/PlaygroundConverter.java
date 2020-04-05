package com.example.kobenhavn.dal.local.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlaygroundConverter {

    @TypeConverter
    public static List<Playground> fromStringToList(String value) {
        Type listType = new TypeToken<ArrayList<Playground>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromListToString(List<Playground> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
