package com.example.kobenhavn.dal.local.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EventConverter {

    @TypeConverter
    public static List<Event> fromStringToList(String value){
        Type listType = new TypeToken<ArrayList<Event>>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromListToString(List<Event> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
